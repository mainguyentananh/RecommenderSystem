package demo4.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import demo4.model.category;
import demo4.model.cosinesimilarity;
import demo4.service.categoryService;
import demo4.service.cosineSimilarityService;
import demo4.service.documentService;

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
		
		Calendar calendar = Calendar.getInstance();
		
		//check cosineSimilarity
		cosinesimilarity cosineSimilarity = cosineSimilarityService.getCosineSimilarityByKey(getValueKey);
		
		//update if != null else create
		if(cosineSimilarity != null) {
			cosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			cosineSimilarity.setVectorName(getValuesVectorName.toString());
			cosineSimilarity.setTime(calendar.getTime());
			cosineSimilarityService.updateCosineSimilarity(cosineSimilarity);
		}else {
			cosinesimilarity newCosineSimilarity = new cosinesimilarity();
			newCosineSimilarity.setKey(getValueKey);
			newCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			newCosineSimilarity.setVectorName(getValuesVectorName.toString());
			newCosineSimilarity.setTime(calendar.getTime());
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

		Calendar calendar = Calendar.getInstance();
		
		//check cosineSimilarity
		cosinesimilarity cosineSimilarity = cosineSimilarityService.getCosineSimilarityByKey(getValueKey);
		
		//update if != null else create
		if(cosineSimilarity != null) {
			cosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			cosineSimilarity.setVectorName(getValuesVectorName.toString());
			cosineSimilarity.setTime(calendar.getTime());
			cosineSimilarityService.updateCosineSimilarity(cosineSimilarity);
		}else {
			cosinesimilarity newCosineSimilarity = new cosinesimilarity();
			newCosineSimilarity.setKey(getValueKey);
			newCosineSimilarity.setCosineSimilarity(getValuesCosineSimilarity.toString());
			newCosineSimilarity.setVectorName(getValuesVectorName.toString());
			newCosineSimilarity.setTime(calendar.getTime());
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
		md.addAttribute("getCosinesimilarity", getCosinesimilarity);
		md.addAttribute("getVectorName", getVectorName.toString());
		return "detailcosinesimilarity";		
	}
	
	
}
