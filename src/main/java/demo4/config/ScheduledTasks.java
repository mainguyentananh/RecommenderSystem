package demo4.config;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import demo4.model.cosinesimilarity;
import demo4.model.tempcosinesimilarity;
import demo4.service.cosineSimilarityService;
import demo4.service.documentService;
import demo4.service.notifyService;
import demo4.service.tempCosineSimilarityService;

@Component
@EnableScheduling
@EnableAsync
public class ScheduledTasks {
	
	@Autowired
	private documentService documentService;
	
	@Autowired
	private tempCosineSimilarityService tempCosineSimilarityService;
	
	@Value("${config.python}")
	private String python;
	
	@Autowired
	private notifyService notifyService;
	
	@Autowired
	private cosineSimilarityService cosineSimilarityService;
	
	@Async
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateRecommendation() throws Exception {
		demo4.model.notify notify = notifyService.getNotifyById("notify");
		if(notify.getMessage() != null) {
			tempCosineSimilarityByDocumentName();
			tempCosineSimilarityByDocumentSummary();
			
			notify.setMessage(null);
			notifyService.updateNotify(notify);
			
			List<tempcosinesimilarity> listTempCosineSimilarity = tempCosineSimilarityService.getAllTempCosineSimilarity();
			for (tempcosinesimilarity tempcosinesimilarity : listTempCosineSimilarity) {
				cosinesimilarity updateCosine = cosineSimilarityService.getCosineSimilarityByKey(tempcosinesimilarity.getKey());
				updateCosine.setCosineSimilarity(tempcosinesimilarity.getCosineSimilarity());
				updateCosine.setCountVectorName(tempcosinesimilarity.getCountVectorName());
				updateCosine.setVectorName(tempcosinesimilarity.getVectorName());
				updateCosine.setTime(tempcosinesimilarity.getTime());
				cosineSimilarityService.updateCosineSimilarity(updateCosine);
			}
			
		}
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
}
