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
import demo4.service.notifyService;

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
	private notifyService notifyService;

	@Autowired
	private cosineSimilarityService cosineSimilarityService;

	@GetMapping(value = "/category")
	public String getAllDocumentByCategory(@RequestParam(value = "id") String idcategory, Model md) {
		List<document> listdocument = documentService.getAllDocumentByCategory(idcategory);
		md.addAttribute("listdocument", listdocument);
		return "listdocument";
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

			// check and remove id not exist in cosine similarity
			demo4.model.notify no = notifyService.getNotifyById("notify");
			String message = no.getMessage();
			if (message != null) {
				String[] arr_message = message.split("-");
				for (String idInNotify : arr_message) {
					if (document.getId() == Integer.valueOf(idInNotify)) {
						md.addAttribute("listFeedback", listFeedback);
						md.addAttribute("document", document);
						return "document";
					}

				}
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

			JSONObject getCosineByIdOfJsonByName = getJsonCosineByName.getJSONObject(String.valueOf(id));
			JSONObject getCosineByIdOfJsonBySummary = getJsonCosineBySummary.getJSONObject(String.valueOf(id));

			
			HashMap<String, Double> tempResultRecommendByName = new HashMap<String, Double>();
			HashMap<String, Double> tempResultRecommendBySummary = new HashMap<String, Double>();

			Iterator<String> keys1 = getCosineByIdOfJsonByName.keys();
			Iterator<String> keys2 = getCosineByIdOfJsonBySummary.keys();

			// sort value cosine by name
			while (keys1.hasNext()) {
				String key1 = keys1.next();
				if (key1.equalsIgnoreCase(String.valueOf(id))) {
					continue;
				}
				String tempValue1 = getCosineByIdOfJsonByName.get(key1).toString();
				double covertValue1 = Double.valueOf(tempValue1);
				tempResultRecommendByName.put(key1, covertValue1);

			}

			// sort value cosine by summary
			while (keys2.hasNext()) {
				String key2 = keys2.next();
				if (key2.equalsIgnoreCase(String.valueOf(id))) {
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

			List<document> listRecommendDocument = new ArrayList<document>();

			for (Map.Entry<Integer, Integer> entry : tempListResult.entrySet()) {
				listRecommendDocument.add(documentService.getDocumentById(entry.getKey()));
			}

			md.addAttribute("listRecommendDocument", listRecommendDocument);
			md.addAttribute("listFeedback", listFeedback);
			md.addAttribute("document", document);
			return "document";

		}
		return "403";
	}

	@GetMapping(value = "/search")
	public String searchDocument(@RequestParam(value = "search") String search, Model md) {
		List<document> listSearch = documentService.searchDocument(search.trim());
		if (listSearch != null) {
			List<document> tempListSearch = new ArrayList<document>();
			tempListSearch.addAll(listSearch);
			// check and remove id not exist in cosine similarity
			demo4.model.notify no = notifyService.getNotifyById("notify");
			String message = no.getMessage();
			if (message != null) {
				String[] arr_message = message.split("-");
				for (String idInNotify : arr_message) {
					for (int i = 0; i < tempListSearch.size(); i++) {
						if (tempListSearch.get(i).getId() == Integer.valueOf(idInNotify)) {
							tempListSearch.remove(tempListSearch.get(i));
						}
					}
				}
			}

			if (!tempListSearch.isEmpty()) {
				List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();
				// cosineByName
				cosinesimilarity cosineByName = cosineSimilarityService
						.getCosineSimilarityByKey(listCosineSimilarity.get(0).getKey());
				cosinesimilarity cosineBySummary = cosineSimilarityService
						.getCosineSimilarityByKey(listCosineSimilarity.get(1).getKey());

				JSONObject getJsonCosineByName = new JSONObject(cosineByName.getCosineSimilarity());
				JSONObject getJsonCosineBySummary = new JSONObject(cosineBySummary.getCosineSimilarity());

				List<JSONObject> listJO = new ArrayList<JSONObject>();
				for (int i = 0; i < tempListSearch.size(); i++) {
					if (i <= 4) {
						listJO.add(getJsonCosineByName.getJSONObject(String.valueOf(tempListSearch.get(i).getId())));
					}
				}

				HashMap<String, Double> tempResultByName = new HashMap<String, Double>();

				for (JSONObject jsonObject : listJO) {
					Iterator<String> keys = jsonObject.keys();

					while (keys.hasNext()) {
						String key = keys.next();
						String tempValue = jsonObject.get(key).toString();
						double convertValue = Double.valueOf(tempValue);
						if (tempResultByName.containsKey(key)) {
							if (tempResultByName.get(key) < convertValue) {
								tempResultByName.put(key, convertValue);
							}

						} else {
							tempResultByName.put(key, convertValue);
						}
					}
				}


				HashMap<String, Double> resultRecommendByName = sortByValue(tempResultByName);
				
				
				
				for (document l : tempListSearch) {
					resultRecommendByName.remove(String.valueOf(l.getId()));
				}

				
				String[] arrayIdDocumentCosineName = resultRecommendByName.keySet().toArray(new String[0]);

				
				HashMap<String, String> tempListResult = new HashMap<String, String>();
				int k = 0;
				for (String id :arrayIdDocumentCosineName) {
					if (k < 5) {
						tempListResult.put(id, id);
						k++;
					} else {
						break;
					}
				}
	
				// add id document in cosine summary
				JSONObject getCosineByIdOfJsonBySummary = getJsonCosineBySummary
						.getJSONObject(String.valueOf(tempListSearch.get(0).getId()));

				HashMap<String, Double> tempResultRecommendBySummary = new HashMap<String, Double>();
				Iterator<String> keys2 = getCosineByIdOfJsonBySummary.keys();

				// sort value cosine by summary
				while (keys2.hasNext()) {
					String key2 = keys2.next();
					if (key2.equalsIgnoreCase(String.valueOf(tempListSearch.get(0).getId()))) {
						continue;
					}
					String tempValue2 = getCosineByIdOfJsonBySummary.get(key2).toString();
					double covertValue2 = Double.valueOf(tempValue2);
					tempResultRecommendBySummary.put(key2, covertValue2);

				}

				// bỏ các tài liệu kết quả tìm kiếm cho các quả summary
				HashMap<String, Double> resultRecommendBySummary = sortByValue(tempResultRecommendBySummary);

				for (document l : tempListSearch) {
					resultRecommendBySummary.remove(String.valueOf(l.getId()));
				}


				String[] arrayIdDocumentCosineSummary = resultRecommendBySummary.keySet().toArray(new String[0]);
				//merger cosine by name and cosine by summary
				for (int i = 0; i < arrayIdDocumentCosineSummary.length; i++) {
					if (tempListResult.size() == 6) {
						break;
					} // check id summary exist in tempResult
					else if (!tempListResult.containsKey(String.valueOf(arrayIdDocumentCosineSummary[i]))) {
						tempListResult.put(String.valueOf(arrayIdDocumentCosineSummary[i]),
								String.valueOf(arrayIdDocumentCosineSummary[i]));
					}

				}

				List<document> listRecommendDocument = new ArrayList<document>();

				for (Map.Entry<String, String> entry : tempListResult.entrySet()) {
					listRecommendDocument.add(documentService.getDocumentById(Integer.valueOf(entry.getKey())));
				}
				
				md.addAttribute("listRecommend", listRecommendDocument);

			}
			md.addAttribute("list", listSearch);

		}
		return "searchdocument";
	}

	@PostMapping(value = "/{id}")
	public String commentDocument(@PathVariable(value = "id") int id, @RequestParam(value = "comment") String comment) {
		try {
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
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/document/" + id;
	}

	@GetMapping(value = "/{id}/star")
	public String rateStarDocument(@PathVariable(value = "id") int id, @RequestParam(value = "score") int score) throws InterruptedException {
		try {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(score);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedbackService.saveFeedback(feedback);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "redirect:/document/" + id;
	}

	@GetMapping(value = "/download/file/{id}")
	public void downloadFile(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {
		try {
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
			File file = new File(app.getRealPath("/static/upload/file/" + document.getFile()));
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
	
		}catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	@GetMapping(value = "/download/source/{id}")
	public void downloadSource(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {
		// get account
		try {
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
