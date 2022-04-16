import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
	
	
	public double tf(List<String> doc, String term) {
	    double result = 0;
	    for (String word : doc) {
	       if (term.equalsIgnoreCase(word))
	              result++;
	       }
	    return result / doc.size();
	}

	
	public double idf(List<List<String>> docs, String term) {
	    double n = 0;
	    for (List<String> doc : docs) {
	        for (String word : doc) {
	            if (term.equalsIgnoreCase(word)) {
	                n++;
	                break;
	            }
	        }
	    }
	    return Math.log(docs.size() / n);
	}

	public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
	    return tf(doc, term) * idf(docs, term);
	}
	
	public static void main(String[] args) {
		List<String> doc1 = Arrays.asList("lập", "trình", "web", "bằng", "java");
        List<String> doc2 = Arrays.asList("lập", "trình", "oop");
        List<String> doc3 = Arrays.asList("cơ", "sở", "dữ", "liệu", "mysql");
        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);
	   
        test calculator = new test();
        
        
        String[] a = {"dữ","liệu"};
        for (String string : a) {
        	double tfidf = calculator.tfIdf(doc1, documents, string);
            System.out.println("TF-IDF (ipsum) = " + tfidf);
            
		}
        
        
	 	}
}
