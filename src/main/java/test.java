import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.google.gson.Gson;

public class test {

	public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double> > list =
               new LinkedList<Map.Entry<Integer, Double> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
	 
	    // Driver Code
	    public static void main(String[] args)
	    {
	 
	        HashMap<Integer, Double> hm = new HashMap<Integer, Double>();
	 
	        // enter data into hashmap
	        hm.put(1, 0.2);
	        hm.put(2, 0.3);
	        hm.put(5, 0.1);
	        hm.put(7, 0.5);
	        Map<Integer, Double> hm1 = sortByValue(hm);
	 
	        // print the sorted hashmap
	        for (Map.Entry<Integer, Double> en : hm1.entrySet()) {
	            System.out.println("Key = " + en.getKey() +
	                          ", Value = " + en.getValue());
	        }
	        
	        int a = 1;
	        System.out.println(String.valueOf(a));
	    }
	
}