package demo4.service;

import java.util.List;

import demo4.model.document;

public interface documentService {
	public void saveDocument(document document);
	public void updateDocument(document document);
	public document getDocumentById(int id);
	public List<document> getAllDocumentByCategory(String idcategory);
	public List<document> getDocumentByCategoryForHome(String idcategory);

}
