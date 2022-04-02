package demo4.dao;

import demo4.model.document;

public interface documentDao {
	public void saveDocument(document document);
	public void updateDocument(document document);
	public document getDocumentById(int id);
}
