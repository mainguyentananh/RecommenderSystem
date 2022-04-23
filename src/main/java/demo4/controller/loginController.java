package demo4.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class loginController {
	
	@GetMapping(value = "/login")
	public String logIn(@RequestParam(value = "error", required = false) String error, ModelMap model) {
		if (error != null) {
			model.addAttribute("message",				
					"<div class=\"alert alert-danger\" role=\"alert\">Invalid login, please try again </div>"
				);
		}
		return "login";
	}
	
	@GetMapping(value = "/logout")
	public String logOut() {
		return "login";
	}
	
	@GetMapping(value = "/403")
	public String accessDenied() {
	    return "403";
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
	
	@GetMapping(value = "/test")
	public String test() throws Exception{
		HashMap<String, List<String>> t = new HashMap<String, List<String>>();
		List<String> d = new ArrayList<String>();
		d.add("The sky is blue");
		d.add("The sun is bright");
		d.add("The sun in the sky is bright");
		d.add("we can see the shining sun, the bright sun");
		t.put("document", d);
		
		//Request server python
		Gson gson = new Gson();	
		String url="https://supportjava.herokuapp.com/test";
		URL object=new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestMethod("POST");
		con.getOutputStream().write(gson.toJson(t).getBytes());
		
		//Read and convert json to array 
		JSONObject jsonResult = new JSONObject(readUrl(con));
		JSONArray convertJsonToArray  = new JSONArray();
		convertJsonToArray.putAll(jsonResult.get("document"));
		System.out.println(convertJsonToArray);
		return "login";
	}
	
}
 
