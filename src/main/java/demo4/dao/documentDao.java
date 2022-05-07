package demo4.dao;

import java.util.HashMap;
import java.util.List;

import demo4.model.document;

public interface documentDao {
	public void saveDocument(document document);
	public void updateDocument(document document);
	public document getDocumentById(int id);
	public List<document> getAllDocumentByCategory(String idcategory);
	public List<document> getDocumentByCategoryForHome(String idcategory);
	public HashMap<String, HashMap<Integer, String>> createJsonContainIdAndNameDocument();
	public HashMap<String, HashMap<Integer, String>> createJsonContainIdAndSummaryDocument();
	public List<document> searchDocument(String search);
}
