package demo4.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import demo4.model.teacher;
import demo4.service.accountService;
import demo4.service.account_roleService;
import demo4.service.categoryService;
import demo4.service.cosineSimilarityService;
import demo4.service.documentService;
import demo4.service.roleService;
import demo4.service.teacherService;

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
	
	@Value("${config.heroku}")
	private String heroku;
	
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
	
	
	private void cosineSimilarityByDocumentName() throws Exception{
		HashMap<String, HashMap<Integer, String>> json = documentService.createJsonContainIdAndNameDocument();		
		//Request server python
		Gson gson = new Gson();	
		String url=heroku+"cosinesimilaritybydocumentname";
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
		cosinesimilarity cosineSimilarity = cosineSimilarityService.getCosineSimilarityByKey(getValueKey);
		
		//update if != null else create
		if(cosineSimilarity != null) {
			cosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			cosineSimilarity.setVectorName(getValuesVectorName.toString());
			cosineSimilarity.setTime(calendar.getTime());
			cosineSimilarity.setCountVectorName(countVectorName);
			cosineSimilarityService.updateCosineSimilarity(cosineSimilarity);
		}else {
			cosinesimilarity newCosineSimilarity = new cosinesimilarity();
			newCosineSimilarity.setKey(getValueKey);
			newCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			newCosineSimilarity.setVectorName(getValuesVectorName.toString());
			newCosineSimilarity.setTime(calendar.getTime());
			newCosineSimilarity.setCountVectorName(countVectorName);
			cosineSimilarityService.saveCosineSimilarity(newCosineSimilarity);
		}
			
	}
	
	
	
	private void cosineSimilarityByDocumentSummary() throws Exception{
		HashMap<String, HashMap<Integer, String>> json = documentService.createJsonContainIdAndSummaryDocument();		
		//Request server python
		Gson gson = new Gson();	
		String url=heroku+"cosinesimilaritybydocumentsummary";
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
		cosinesimilarity cosineSimilarity = cosineSimilarityService.getCosineSimilarityByKey(getValueKey);
		
		//update if != null else create
		if(cosineSimilarity != null) {
			cosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			cosineSimilarity.setVectorName(getValuesVectorName.toString());
			cosineSimilarity.setTime(calendar.getTime());
			cosineSimilarity.setCountVectorName(countVectorName);
			cosineSimilarityService.updateCosineSimilarity(cosineSimilarity);
		}else {
			cosinesimilarity newCosineSimilarity = new cosinesimilarity();
			newCosineSimilarity.setKey(getValueKey);
			newCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			newCosineSimilarity.setVectorName(getValuesVectorName.toString());
			newCosineSimilarity.setTime(calendar.getTime());
			newCosineSimilarity.setCountVectorName(countVectorName);
			cosineSimilarityService.saveCosineSimilarity(newCosineSimilarity);
		}
			
	}
	
	
	@GetMapping(value = "/cosinesimilarity")
	public String cosineSimilarity(Model md) throws Exception {
		List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();
		
		if(!listCosineSimilarity.isEmpty()) {
			md.addAttribute("listCosineSimilarity", listCosineSimilarity);
			return "cosinesimilarity";
		}else {
			cosineSimilarityByDocumentSummary();
			cosineSimilarityByDocumentName();		
			return "redirect:/admin/cosinesimilarity";		
		}
	}
	
	@GetMapping(value = "/cosinesimilarity/update")
	public String updateCosineSimilarityByKey() throws Exception {
			cosineSimilarityByDocumentSummary();
			cosineSimilarityByDocumentName();
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
	
	
	@GetMapping(value = "/createteacher")
	public String createAccountTeacher() {
		return "createteacher";
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
				md.addAttribute("notify",
						"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Định dạng file không đúng</div>");
				return "createteacher";
			}

			md.addAttribute("notify",
					"<div class=\"alert alert-success text-center col-4 mt-2\" role=\"alert\">Khởi tạo thành công</div>");
			return "createteacher";
		}
		return "createteacher";
	}

	private void readExcel(File readFile) throws InvalidFormatException, IOException, MessagingException {
		Workbook workbook = new XSSFWorkbook(readFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		while (iterator.hasNext()) {
			Row row = iterator.next();
			if(row.getRowNum() > 2) {
			String idTeacher = row.getCell(1).toString().replaceAll(".0$", "");
			if(!teacherService.checkTeacher(idTeacher)) {

				teacher nteacher = new teacher();
				nteacher.setId(idTeacher);
				nteacher.setName(row.getCell(2).toString());
				nteacher.setMail(row.getCell(3).toString());
				nteacher.setPhone(row.getCell(4).toString().replaceAll(".0$", ""));
				nteacher.setDegree(row.getCell(5).toString());
				nteacher.setImage("newteacher.jpg");
				teacherService.saveTeacher(nteacher);
				
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				account account = new account();
				account.setUsername(idTeacher);
				account.setPassword(encoder.encode(idTeacher));
		
				accountService.saveAccount(account);

				account_role account_role = new account_role();
				account_role.setAccount(accountService.findAccountByUserName(idTeacher));
				account_role.setRole(roleService.findRoleByRoleName("ROLE_TEACHER"));
				account_roleService.saveAccount_Role(account_role);
				
			}
			}
		}
		workbook.close();
	}
	
}
