package demo4.service;

import demo4.model.document;

public interface documentService {
	public void saveDocument(document document);
	public void updateDocument(document document);
	public document getDocumentById(int id);
}
