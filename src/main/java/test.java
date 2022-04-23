import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import com.google.gson.Gson;

public class test {

	public static void main(String[] args) {
	
		HashMap<String, List<String>> t = new HashMap<String, List<String>>();
		
		List<String> d = new ArrayList<String>();
		d.add("The sky is blue");
		d.add("The sun is bright");
		d.add("The sun in the sky is bright");
		d.add("we can see the shining sun, the bright sun");
		
		t.put("document", d);
		
		String json = new Gson().toJson(t);
		System.out.println(json);
		
	}
	
}