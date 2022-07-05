package demo4.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import demo4.model.account;
import demo4.model.account_role;
import demo4.model.category;
import demo4.model.cosinesimilarity;
import demo4.model.document;
import demo4.model.feedback;
import demo4.model.teacher;
import demo4.model.tempcosinesimilarity;
import demo4.service.accountService;
import demo4.service.account_roleService;
import demo4.service.categoryService;
import demo4.service.cosineSimilarityService;
import demo4.service.documentService;
import demo4.service.feedbackService;
import demo4.service.mailService;
import demo4.service.notifyService;
import demo4.service.roleService;
import demo4.service.teacherService;
import demo4.service.tempCosineSimilarityService;

@EnableAsync
@Controller
@RequestMapping(value = "/admin")
@PropertySource("classpath:configservice.properties")
public class adminController {

	
	@Autowired
	private categoryService categoryService;

	@Autowired
	private documentService documentService;
	
	@Autowired
	private cosineSimilarityService cosineSimilarityService;
	
	@Autowired
	private tempCosineSimilarityService tempCosineSimilarityService;
	
	@Value("${config.python}")
	private String python;
	
	@Value("${config.username}")
	private String mailServer;
	
	@Autowired
	private ServletContext app;
	
	@Autowired
	private teacherService teacherService;
	
	@Autowired
	private accountService accountService;
	
	@Autowired
	private account_roleService account_roleService;
	
	@Autowired
	private roleService roleService;
	
	@Autowired
	private notifyService notifyService;
	
	@Autowired
	private feedbackService feedbackService;
	
	@Autowired
	private mailService mailService;
	
	@GetMapping(value = "/category")
	public String category(Model md) {
		md.addAttribute("listCategory", categoryService.getAllCategory());
		return "category";
	}
	

	@GetMapping(value = "/category/create")
	public String createCategory(Model md) {
		md.addAttribute("category", new category());
		return "createcategory";
	}
	
	@PostMapping(value = "/category/create")
	public String confirmCreateCategory(@ModelAttribute(value = "category") category category) {
		categoryService.saveCategory(category);
		return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/category/edit/{id}")
	public String editCategory(@PathVariable(value = "id") String id,Model md) {
		md.addAttribute("category", categoryService.getCategoryById(id));
		return "editcategory";
	}
	
	@PostMapping(value = "/category/edit")
	public String confirmEditCategory(@ModelAttribute(value = "category") category category) {
		categoryService.updateCategory(category);
		return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/category/delete/{id}")
	public String confirmDeleteCategory(@PathVariable(value = "id")String id) {
		categoryService.deleteCategory(id);
		return "redirect:/admin/category";
	}
	

	
	
	private static String readUrl(HttpURLConnection con) throws Exception {
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	
	private void tempCosineSimilarityByDocumentName() throws Exception{
		HashMap<String, HashMap<Integer, String>> json = documentService.createJsonContainIdAndNameDocument();
		//Request server python
		Gson gson = new Gson();
		String url=python+"cosinesimilaritybydocumentname";
		URL object=new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestMethod("POST");
		con.getOutputStream().write(gson.toJson(json).getBytes());
		
		//Read Json 
		JSONObject getJson = new JSONObject(readUrl(con));
		JSONObject getValuesCosineSimilarity = getJson.getJSONObject("cosineSimilarity");
		JSONArray getValuesVectorName = new JSONArray(getJson.getJSONArray("vectorName"));
		String getValueKey = getJson.getString("key");
		int countVectorName = (int) getJson.get("countVectorName");
		Calendar calendar = Calendar.getInstance();
		
		//check cosineSimilarity
		//check cosineSimilarity
		tempcosinesimilarity tempCosineSimilarity = tempCosineSimilarityService.getTempCosineSimilarityByKey(getValueKey);
		
		//update if != null else create
		if(tempCosineSimilarity != null) {
			tempCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			tempCosineSimilarity.setVectorName(getValuesVectorName.toString());
			tempCosineSimilarity.setTime(calendar.getTime());
			tempCosineSimilarity.setCountVectorName(countVectorName);
			tempCosineSimilarityService.updateTempCosineSimilarity(tempCosineSimilarity);
		}else {
			tempcosinesimilarity newTempCosineSimilarity = new tempcosinesimilarity();
			newTempCosineSimilarity.setKey(getValueKey);
			newTempCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			newTempCosineSimilarity.setVectorName(getValuesVectorName.toString());
			newTempCosineSimilarity.setTime(calendar.getTime());
			newTempCosineSimilarity.setCountVectorName(countVectorName);
			tempCosineSimilarityService.saveTempCosineSimilarity(newTempCosineSimilarity);
		}
			
	}
	
	
	
	private void tempCosineSimilarityByDocumentSummary() throws Exception{
		HashMap<String, HashMap<Integer, String>> json = documentService.createJsonContainIdAndSummaryDocument();		
		//Request server python
		Gson gson = new Gson();	
		
		String url=python+"cosinesimilaritybydocumentsummary";
		URL object=new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestMethod("POST");
		con.getOutputStream().write(gson.toJson(json).getBytes());
		
		//Read Json 
		JSONObject getJson = new JSONObject(readUrl(con));
		JSONObject getValuesCosineSimilarity = getJson.getJSONObject("cosineSimilarity");
		JSONArray getValuesVectorName = new JSONArray(getJson.getJSONArray("vectorName"));
		String getValueKey = getJson.getString("key");
		int countVectorName = (int) getJson.get("countVectorName");
		Calendar calendar = Calendar.getInstance();
		
		//check cosineSimilarity
		tempcosinesimilarity tempCosineSimilarity = tempCosineSimilarityService.getTempCosineSimilarityByKey(getValueKey);
		
		//update if != null else create
		if(tempCosineSimilarity != null) {
			tempCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			tempCosineSimilarity.setVectorName(getValuesVectorName.toString());
			tempCosineSimilarity.setTime(calendar.getTime());
			tempCosineSimilarity.setCountVectorName(countVectorName);
			tempCosineSimilarityService.updateTempCosineSimilarity(tempCosineSimilarity);
		}else {
			tempcosinesimilarity newTempCosineSimilarity = new tempcosinesimilarity();
			newTempCosineSimilarity.setKey(getValueKey);
			newTempCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			newTempCosineSimilarity.setVectorName(getValuesVectorName.toString());
			newTempCosineSimilarity.setTime(calendar.getTime());
			newTempCosineSimilarity.setCountVectorName(countVectorName);
			tempCosineSimilarityService.saveTempCosineSimilarity(newTempCosineSimilarity);
		}
			
	}
	
	
	@GetMapping(value = "/cosinesimilarity")
	public String cosineSimilarity(Model md) throws Exception {
	
		List<tempcosinesimilarity> listTempCosineSimilarity = tempCosineSimilarityService.getAllTempCosineSimilarity();
		
		if(!listTempCosineSimilarity.isEmpty()) {
			List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();
			if(listCosineSimilarity.isEmpty()) {
				for (tempcosinesimilarity cosinesimilarity2 : listTempCosineSimilarity) {
					Calendar calendar = Calendar.getInstance();
					cosinesimilarity ncs = new cosinesimilarity(cosinesimilarity2.getKey(),calendar.getTime(),cosinesimilarity2.getVectorName(),cosinesimilarity2.getCosineSimilarity(),cosinesimilarity2.getCountVectorName());
					cosineSimilarityService.saveCosineSimilarity(ncs);
				}
			}
			List<document> listDocumentInNotify = new ArrayList<document>();
			demo4.model.notify notify = notifyService.getNotifyById("notify");
			if(notify.getMessage() != null) {
				String[] arrayIdDocumentToNotify = notify.getMessage().split("-");
				for (String id : arrayIdDocumentToNotify) {
					listDocumentInNotify.add(documentService.getDocumentById(Integer.valueOf(id)));
				}
			}
			md.addAttribute("listDocumentInNotify", listDocumentInNotify);
			md.addAttribute("listCosineSimilarity", listCosineSimilarity);
			md.addAttribute("listTempCosineSimilarity", listTempCosineSimilarity);
			return "cosinesimilarity";
		}else {
			List<document> listDocumentInNotify = new ArrayList<document>();
			demo4.model.notify notify = notifyService.getNotifyById("notify");
			if(notify.getMessage() != null) {
				String[] arrayIdDocumentToNotify = notify.getMessage().split("-");
				for (String id : arrayIdDocumentToNotify) {
					listDocumentInNotify.add(documentService.getDocumentById(Integer.valueOf(id)));
				}
			}
			md.addAttribute("listDocumentInNotify", listDocumentInNotify);
			return "cosinesimilarity";		
		}
	}
	
	@GetMapping(value = "/cosinesimilarity/update")
	public String updateCosineSimilarityByKey(){
		List<tempcosinesimilarity> listTempCosineSimilarity = tempCosineSimilarityService.getAllTempCosineSimilarity();
		Calendar calendar = Calendar.getInstance();
		for (tempcosinesimilarity tempcosinesimilarity : listTempCosineSimilarity) {
			cosinesimilarity updateCosine = cosineSimilarityService.getCosineSimilarityByKey(tempcosinesimilarity.getKey());
			updateCosine.setCosineSimilarity(tempcosinesimilarity.getCosineSimilarity());
			updateCosine.setCountVectorName(tempcosinesimilarity.getCountVectorName());
			updateCosine.setVectorName(tempcosinesimilarity.getVectorName());
			updateCosine.setTime(calendar.getTime());
			cosineSimilarityService.updateCosineSimilarity(updateCosine);
		}
		return "redirect:/admin/cosinesimilarity";		
	}

	
	@GetMapping(value = "/tempcosinesimilarity/create")
	public String tempCosineSimilarity() throws Exception {
		tempCosineSimilarityByDocumentName();
		tempCosineSimilarityByDocumentSummary();
		demo4.model.notify no = notifyService.getNotifyById("notify");
		no.setMessage(null);
		notifyService.updateNotify(no);
		return "redirect:/admin/cosinesimilarity";
	}
	
	
	@GetMapping(value = "/cosinesimilarity/detail/{key}")
	public String detailCosineSimilarity(@PathVariable(value = "key") String key,Model md){
		cosinesimilarity cosine = cosineSimilarityService.getCosineSimilarityByKey(key);
		JSONObject getCosinesimilarity = new JSONObject(cosine.getCosineSimilarity());
		JSONArray getVectorName = new JSONArray(cosine.getVectorName());
		md.addAttribute("key", key);
		md.addAttribute("count", cosine.getCountVectorName());
		md.addAttribute("getCosinesimilarity", getCosinesimilarity);
		md.addAttribute("getVectorName", getVectorName.toString());
		return "detailcosinesimilarity";		
	}
	
	
	@GetMapping(value = "/tempcosinesimilarity/detail/{key}")
	public String detailTempCosineSimilarity(@PathVariable(value = "key") String key,Model md){
		tempcosinesimilarity tempcosine = tempCosineSimilarityService.getTempCosineSimilarityByKey(key);
		JSONObject getCosinesimilarity = new JSONObject(tempcosine.getCosineSimilarity());
		JSONArray getVectorName = new JSONArray(tempcosine.getVectorName());
		md.addAttribute("key", key);
		md.addAttribute("count", tempcosine.getCountVectorName());
		md.addAttribute("getCosinesimilarity", getCosinesimilarity);
		md.addAttribute("getVectorName", getVectorName.toString());
		return "detailcosinesimilarity";		
	}
	
	
	@GetMapping(value = "/createteacher")
	public String createAccountTeacher(Model md) {
		List<teacher> l = teacherService.getAllTeacher();
		md.addAttribute("list", l);
		return "createteacher";
	}
	
	
	
	public  HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		
		list.clear();
		return temp;
	}
	
	
	
	@GetMapping(value = "/evaluatingsystem")
	public String evalutingForSystem(Model md) {
		try {
		Object objCountAccountInSystem = accountService.countAccount();
		List<Object[]> feedbackDocumentOfAccount = feedbackService.feedbackDocumentOfAccount();
		
		//remove id exist in notify (clean list)
		demo4.model.notify no = notifyService.getNotifyById("notify");
		String message = no.getMessage();
		if (message != null) {
			String[] arr_message = message.split("-");
			for (String idInNotify : arr_message) {
				for (int i = 0; i < feedbackDocumentOfAccount.size(); i++) {
					Object[] obj = feedbackDocumentOfAccount.get(i);
					String convertResultObj = String.valueOf(obj[1]) ;
					if(convertResultObj.equalsIgnoreCase(idInNotify)) {
						feedbackDocumentOfAccount.remove(i);
					}
				}

			}
		}
		
		//Hashmap with key is id account and values is string document id feedback of account
		HashMap<String, String> resultFb = new HashMap<String, String>();
		
		
		//put to result with key is Id account and values is String (id document)
		for (Object[] objects : feedbackDocumentOfAccount) {
			if(resultFb.containsKey(String.valueOf(objects[0]))) {
				String oldValues = resultFb.get(String.valueOf(objects[0]));
				resultFb.replace(String.valueOf(objects[0]), oldValues, String.join("-", oldValues,String.valueOf(objects[1])));
				
			}else {
				resultFb.put(String.valueOf(objects[0]),String.valueOf(objects[1]));
			}
		}
		
		
		List<String> logs = new ArrayList<String>();
		
		double countAccountInSystem = Double.valueOf(String.valueOf(objCountAccountInSystem));
		int countEvaluationForAccount = 0;
		int countEvaluationForItem = 0;
		
		
		for (Map.Entry<String, String> entry : resultFb.entrySet()) {
			
			
			String[] arrayDocumentOfAccount = entry.getValue().split("-");
			
			//cosinesimilarity of first document in arrayDocumentOfAccount
			String firstDocumentId = arrayDocumentOfAccount[0];
			
			
			//Begin result 6 recommend document with 3 document cosine similarity by name, and 3 document cosine similarity by summary
			List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();
			// cosineByName
			cosinesimilarity cosineByName = cosineSimilarityService
					.getCosineSimilarityByKey(listCosineSimilarity.get(0).getKey());
			cosinesimilarity cosineBySummary = cosineSimilarityService
					.getCosineSimilarityByKey(listCosineSimilarity.get(1).getKey());

			JSONObject getJsonCosineByName = new JSONObject(cosineByName.getCosineSimilarity());
			JSONObject getJsonCosineBySummary = new JSONObject(cosineBySummary.getCosineSimilarity());
			
			JSONObject getCosineByIdOfJsonByName = getJsonCosineByName.getJSONObject(firstDocumentId);
			JSONObject getCosineByIdOfJsonBySummary = getJsonCosineBySummary.getJSONObject(firstDocumentId);

			
			HashMap<String, Double> tempResultRecommendByName = new HashMap<String, Double>();
			HashMap<String, Double> tempResultRecommendBySummary = new HashMap<String, Double>();

			Iterator<String> keys1 = getCosineByIdOfJsonByName.keys();
			Iterator<String> keys2 = getCosineByIdOfJsonBySummary.keys();

			// sort value cosine by name
			while (keys1.hasNext()) {
				String key1 = keys1.next();
				if (key1.equalsIgnoreCase(firstDocumentId)) {
					continue;
				}
				String tempValue1 = getCosineByIdOfJsonByName.get(key1).toString();
				double covertValue1 = Double.valueOf(tempValue1);
				tempResultRecommendByName.put(key1, covertValue1);

			}

			// sort value cosine by summary
			while (keys2.hasNext()) {
				String key2 = keys2.next();
				if (key2.equalsIgnoreCase(firstDocumentId)) {
					continue;
				}
				String tempValue2 = getCosineByIdOfJsonBySummary.get(key2).toString();
				double covertValue2 = Double.valueOf(tempValue2);
				tempResultRecommendBySummary.put(key2, covertValue2);

			}

			HashMap<String, Double> resultRecommendByName = sortByValue(tempResultRecommendByName);
			String[] arrayIdDocumentCosineName = resultRecommendByName.keySet().toArray(new String[0]);

			HashMap<String, Double> resultRecommendBySummary = sortByValue(tempResultRecommendBySummary);
			String[] arrayIdDocumentCosineSummary = resultRecommendBySummary.keySet().toArray(new String[0]);

			// contain 3 id document of cosine by name and 3 id document of cosine by
			// summary
			HashMap<Integer, Integer> tempListResult = new HashMap<Integer, Integer>();

			// add document of cosine by name
			for (int i = 0; i < 3; i++) {
				tempListResult.put(Integer.valueOf(arrayIdDocumentCosineName[i]),
						Integer.valueOf(arrayIdDocumentCosineName[i]));
			}

			// add document of cosine by summary
			for (int i = 0; i < arrayIdDocumentCosineSummary.length; i++) {
				if (tempListResult.size() == 6) {
					break;
				} // check id summary exist in tempResult
				else if (!tempListResult.containsKey(Integer.valueOf(arrayIdDocumentCosineSummary[i]))) {
					tempListResult.put(Integer.valueOf(arrayIdDocumentCosineSummary[i]),
							Integer.valueOf(arrayIdDocumentCosineSummary[i]));
				}

			}
			
			//end result recommend
			
			
			// 6 tai lieu, 3 goi y theo ten , 3 goi y theo tom tat
			List<String> listContainDocumentRecommendForFirstDocumentId = new ArrayList<String>();
			
			for (Map.Entry<Integer, Integer> keyOftempListResult : tempListResult.entrySet()) {
				listContainDocumentRecommendForFirstDocumentId.add(String.valueOf(keyOftempListResult.getKey()));
			}
			
			
			String stringTempLogs ="";
			for (String string : listContainDocumentRecommendForFirstDocumentId) {
				stringTempLogs = String.join("-",stringTempLogs,string);
			}
			
			
			int countTemp = 0;
			for (String documentIdFeedbackOfAccount : arrayDocumentOfAccount) {
					for (String documentIdOfRecommend : listContainDocumentRecommendForFirstDocumentId) {
					if(documentIdOfRecommend.equalsIgnoreCase(documentIdFeedbackOfAccount)) {
						countEvaluationForItem++;
						countTemp++;
					}
					
				}
			}
			
			if(countTemp > 0) {
				countEvaluationForAccount++;
				countTemp = 0;
			}
			
			
		String resultLogs= "AccountId: "+entry.getKey()+ ",documentIdFeedback: "+entry.getValue()+",DocumentRecommend:"+stringTempLogs;	
		logs.add(resultLogs);
		}
		List<Object> listAccountFeedback = feedbackService.listAccountFeedback();
		double evaluationAccount = Math.round(countEvaluationForAccount/countAccountInSystem * 100);
		double evaluationItem = Math.round(countEvaluationForItem/countAccountInSystem * 100);
		md.addAttribute("logs", logs);
		md.addAttribute("countAccountFeedback", listAccountFeedback.size());
		md.addAttribute("evaluationAccount", evaluationAccount);
		md.addAttribute("evaluationItem", evaluationItem);
		md.addAttribute("countAccountInSystem", Math.round(countAccountInSystem));
		md.addAttribute("countEvaluationForAccount", countEvaluationForAccount);
		md.addAttribute("countEvaluationForItem", countEvaluationForItem);
		
		
		return "evaluation";
		}catch (Exception e) {
			return "evaluation";
		}	
	}
	
	

	
	@PostMapping(value = "/createteacher", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String confirmCreateTeacher(@RequestParam(value = "filename") MultipartFile file, Model md)
			throws IllegalStateException, IOException, InvalidFormatException, MessagingException {
		String path = app.getRealPath("/static/upload/excel");
		File folder = new File(path);

		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!file.isEmpty()) {
			// upload file to server
			String fileName = file.getOriginalFilename();
			File uploadFile = new File(path, fileName);
			file.transferTo(uploadFile);

			// read file from server
			String pathFile = path + "/" + fileName;
			File readFile = new File(pathFile);
			try {
				readExcel(readFile);

			} catch (Exception e) {
				return "redirect:/admin/createteacher";
			}
			readFile.delete();
			return "redirect:/admin/createteacher";
		}
		return "createteacher";
	}

	
	public static String generateRandomPassword(int len) {
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return RandomStringUtils.random(len, chars);
	}
	
	private void readExcel(File readFile) throws InvalidFormatException, IOException, MessagingException {
		Workbook workbook = new XSSFWorkbook(readFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		HashMap<String,List<Object>> infoAccount = new HashMap<String, List<Object>>();
		boolean checkFlag = false;
		while (iterator.hasNext()) {
			Row row = iterator.next();
			if(row.getCell(0).toString().equalsIgnoreCase("STT")) {
				checkFlag= true;
				continue;
			}
			
			if(checkFlag) {
			String idTeacher = row.getCell(1).toString().replaceAll(".0$", "");
			String mailTeacher = row.getCell(3).toString();
			if(!teacherService.checkTeacher(idTeacher)) {

				teacher nteacher = new teacher();
				nteacher.setId(idTeacher);
				nteacher.setName(row.getCell(2).toString());
				nteacher.setMail(row.getCell(3).toString());
				nteacher.setPhone(row.getCell(4).toString().replaceAll(".0$", ""));
				nteacher.setDegree(row.getCell(5).toString());
				nteacher.setImage("newteacher.jpg");
				teacherService.saveTeacher(nteacher);
				
				String passwordAccount = generateRandomPassword(8);
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				account account = new account();
				account.setUsername(idTeacher);
				account.setPassword(encoder.encode(passwordAccount));
				
				try {
					accountService.saveAccount(account);
				}catch (Exception e) {
					accountService.saveAccount(account);
				}
				

				account_role account_role = new account_role();
				account_role.setAccount(accountService.findAccountByUserName(idTeacher));
				account_role.setRole(roleService.findRoleByRoleName("ROLE_TEACHER"));
				account_roleService.saveAccount_Role(account_role);
				
				List<Object> mailAndPassword = new ArrayList<Object>();
				mailAndPassword.add(mailTeacher);
				mailAndPassword.add(passwordAccount);
				
			
				infoAccount.put(idTeacher,mailAndPassword);
				
				
//				String subject = "Khoa học máy tính trường Đại học Cần Thơ";
//				String content = "Chào " + idTeacher + ". Tài khoản đăng nhập: "
//						+ idTeacher + " và mật khẩu: " + passwordAccount;
//				mailService.sendEmail(mailServer, mailTeacher, subject, content);
			}
			}
		}
		
		//file excel contain info account of student
				XSSFWorkbook workbook2 = new XSSFWorkbook();
				XSSFSheet sheet2 = workbook2.createSheet("TaiKhoanDangNhapGiangVien");
				XSSFFont font = workbook2.createFont();
				font.setBold(true);
				font.setFontHeight(14);
				XSSFCellStyle style = workbook2.createCellStyle();
				style.setFont(font);
				
				int rowNum = 0;
				//Title row1
				Row row_1 = sheet2.createRow(rowNum++);
				Cell cell_10 = row_1.createCell(0);
				cell_10.setCellValue("Danh sách tài khoản của giảng viên");
				cell_10.setCellStyle(style);
				
				Row row_2 = sheet2.createRow(rowNum++);
				Cell cell_20 = row_2.createCell(0);
				cell_20.setCellValue("STT");
				cell_20.setCellStyle(style);
				
				Cell cell_21 = row_2.createCell(1);
				cell_21.setCellValue("Mã số giảng viên");
				cell_21.setCellStyle(style);
				
				Cell cell_22 = row_2.createCell(2);
				cell_22.setCellValue("Mail");
				cell_22.setCellStyle(style);
				
				Cell cell_23 = row_2.createCell(3);
				cell_23.setCellValue("Mật khẩu");
				cell_23.setCellStyle(style);
				
				int i = 1;
				for (Map.Entry<String, List<Object>> entry : infoAccount.entrySet()) {
					Row rowAccountTeacher = sheet2.createRow(rowNum++);
					Cell cell_STT = rowAccountTeacher.createCell(0);
					cell_STT.setCellValue(i++);
					
					Cell cell_Msgv = rowAccountTeacher.createCell(1);
					cell_Msgv.setCellValue(entry.getKey());
					
					Cell cell_Mail = rowAccountTeacher.createCell(2);
					cell_Mail.setCellValue(entry.getValue().get(0).toString());
					
					
					Cell cell_Matkhau = rowAccountTeacher.createCell(3);
					cell_Matkhau.setCellValue(entry.getValue().get(1).toString());
					
				}
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String fName = "ListGV"+auth.getName()+".xlsx";
				String path = app.getRealPath("/static/upload/excel");
				FileOutputStream outputStream = new FileOutputStream(path+"/"+fName);
			    workbook2.write(outputStream);
			    outputStream.close();
		workbook.close();
		workbook2.close();
	}
	
	
	
	@GetMapping(value = "/download/file/excel")
	public void downloadFile(HttpServletResponse response) throws IOException {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String fName = "ListGV"+auth.getName()+".xlsx";
			File file = new File(app.getRealPath("/static/upload/excel/"+fName));
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			file.delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	
}
