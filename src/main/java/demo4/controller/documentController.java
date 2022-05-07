package demo4.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo4.model.account;
import demo4.model.cosinesimilarity;
import demo4.model.document;
import demo4.model.feedback;
import demo4.service.accountService;
import demo4.service.cosineSimilarityService;
import demo4.service.documentService;
import demo4.service.feedbackService;

@Controller
@RequestMapping(value = "/document")

public class documentController {

	@Autowired
	private documentService documentService;

	@Autowired
	private feedbackService feedbackService;

	@Autowired
	private accountService accountService;

	@Autowired
	private ServletContext app;

	@Autowired
	private cosineSimilarityService cosineSimilarityService;

	@GetMapping(value = "/category")
	public String getAllDocumentByCategory(@RequestParam(value = "id") String idcategory, Model md) {
		List<document> listdocument = documentService.getAllDocumentByCategory(idcategory);
		md.addAttribute("listdocument", listdocument);
		return "listdocument";
	}
	
	
		
	public HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
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
		return temp;
	}


	@GetMapping(value = "/{id}")
	public String getDocument(@PathVariable(value = "id") int id, Model md) throws Exception {
		document document = documentService.getDocumentById(id);
		if (document != null) {
			List<feedback> listFeedback = feedbackService.getFeedBackByIdDocument(id);
			Object result = feedbackService.avgStarDocument(id);
			if (result == null) {
				md.addAttribute("star", 0);
			} else {
				double temp = (double) result;
				long star = Math.round(temp);
				md.addAttribute("star", star);
			}

			List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();

			// cosineByName
			cosinesimilarity cosineByName = cosineSimilarityService
					.getCosineSimilarityByKey(listCosineSimilarity.get(0).getKey());
			
			
			// cosineBySummary
			cosinesimilarity cosineBySummary = cosineSimilarityService
					.getCosineSimilarityByKey(listCosineSimilarity.get(1).getKey());
			
			
			// Convert String to Json: CosineByName And CosineBySummary
			JSONObject getJsonCosineByName = new JSONObject(cosineByName.getCosineSimilarity());
			JSONObject getJsonCosineBySummary = new JSONObject(cosineBySummary.getCosineSimilarity());
			
			
			try {		
				List<JSONObject> listJO = new ArrayList<JSONObject>();
				listJO.add(getJsonCosineByName.getJSONObject(String.valueOf(id)));
				listJO.add(getJsonCosineBySummary.getJSONObject(String.valueOf(id)));
				HashMap<String, Double> tempResult = new HashMap<String, Double>();
				for (JSONObject jsonObject : listJO) {
					Iterator<String> keys = jsonObject.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						String tempValue = jsonObject.get(key).toString();
						double convertValue = Double.valueOf(tempValue);
						if(tempResult.containsKey(key)) {
							if(tempResult.get(key) < convertValue) {
								tempResult.put(key, convertValue);
							}
							
						}else {
							tempResult.put(key, convertValue);
						}
					}
				}
				
				//remove current id document
				tempResult.remove(String.valueOf((id)));
		
				HashMap<String, Double> resultRecommend = sortByValue(tempResult);
				String[] arrayIdDocument = resultRecommend.keySet().toArray(new String[0]);
				
				
				
				List<document> listRecommendDocument = new ArrayList<document>();	
				
				for (int i = 0; i < arrayIdDocument.length; i++) {
					if(i < 6) {
					listRecommendDocument.add(documentService.getDocumentById(Integer.valueOf(arrayIdDocument[i])));
					}else {
						break;
					}
				}			
							
				md.addAttribute("listRecommendDocument", listRecommendDocument);
				md.addAttribute("listFeedback", listFeedback);
				md.addAttribute("document", document);

				return "document";
				}catch (Exception e) {
					md.addAttribute("listFeedback", listFeedback);
					md.addAttribute("document", document);
					return "document";
				}
		}
		return "403";
	}

	
	@GetMapping(value = "/search")
	public String searchDocument(@RequestParam(value = "search") String search,Model md) {
		List<document> list = documentService.searchDocument(search.trim());
		if(list != null) {
		List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();

		// cosineByName
		cosinesimilarity cosineByName = cosineSimilarityService
				.getCosineSimilarityByKey(listCosineSimilarity.get(0).getKey());
		cosinesimilarity cosineBySummary = cosineSimilarityService
				.getCosineSimilarityByKey(listCosineSimilarity.get(1).getKey());
		
		JSONObject getJsonCosineByName = new JSONObject(cosineByName.getCosineSimilarity());
		JSONObject getJsonCosineBySummary = new JSONObject(cosineBySummary.getCosineSimilarity());
		
		
		List<JSONObject> listJO = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			if(i <= 1){
				listJO.add(getJsonCosineBySummary.getJSONObject(String.valueOf(list.get(i).getId())));
			}
			if(i <= 4) {
				listJO.add(getJsonCosineByName.getJSONObject(String.valueOf(list.get(i).getId())));
			}			
		}
		
		
		HashMap<String, Double> tempResult = new HashMap<String, Double>();
		
		for (JSONObject jsonObject : listJO) {
			Iterator<String> keys = jsonObject.keys();
			
			while (keys.hasNext()) {
				String key = keys.next();
				String tempValue = jsonObject.get(key).toString();
				double convertValue = Double.valueOf(tempValue);
				if(tempResult.containsKey(key)) {
					if(tempResult.get(key) < convertValue) {
						tempResult.put(key, convertValue);
					}
					
				}else {
					tempResult.put(key, convertValue);
				}
			}
		}
		
		for (document l : list) {
			tempResult.remove(String.valueOf((l.getId())));
		}
		
		HashMap<String, Double> resultRecommend = sortByValue(tempResult);
		
		List<document> listRecommendDocument = new ArrayList<document>();	
		
		for(Map.Entry<String, Double> entry : resultRecommend.entrySet()) {
			listRecommendDocument.add(documentService.getDocumentById(Integer.valueOf(entry.getKey())));
		}
		
		md.addAttribute("list", list);
		md.addAttribute("listRecommend", listRecommendDocument);
		}
		return "searchdocument";
	}
	

	
	
	

	@PostMapping(value = "/{id}")
	public String commentDocument(@PathVariable(value = "id") int id, @RequestParam(value = "comment") String comment) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(0);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedback.setComment(comment);
		feedbackService.saveFeedback(feedback);
		return "redirect:/document/" + id;
	}

	@GetMapping(value = "/{id}/star")
	public String rateStarDocument(@PathVariable(value = "id") int id, @RequestParam(value = "score") int score) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(score);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedbackService.saveFeedback(feedback);
		return "redirect:/document/" + id;
	}

	@GetMapping(value = "/download/file/{id}")
	public void downloadFile(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {

		// get account
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);

		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(0);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedbackService.saveFeedback(feedback);

		try {
			File file = new File(app.getRealPath("/static/upload/file/" + document.getFile()));
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@GetMapping(value = "/download/source/{id}")
	public void downloadSource(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {
		// get account
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		account account = accountService.findAccountByUserName(auth.getName());

		document document = documentService.getDocumentById(id);
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(0);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(account);
		feedbackService.saveFeedback(feedback);

		try {
			File file = new File(app.getRealPath("/static/upload/source/" + document.getSource()));
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
}
