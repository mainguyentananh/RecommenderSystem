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

	   
	    
	    String doc1 = "Lập trình web bằng java [và (spring framework, dùng spring mvc cho môn niên luận";
	    String doc2 = "Lập trình java cho luận văn";
	    String doc3 = "lập trình web php mysql";
	   
	    String[] t = doc1.split(" ");
	    test test = new test();

	    for (String string : t) {
			System.out.println(string.replaceAll("^[-(\\[,!\\{.]|[)\\],!.\\}-]$", ""));
		}
	
	
		
		
	}
}
