package demo4.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.documentDao;
import demo4.model.document;

@Service
@Transactional
public class documentServiceImpl implements documentService{

	@Autowired
	private documentDao repo;
	
	@Override
	public void saveDocument(document document) {
		repo.saveDocument(document);
		
	}

	@Override
	public void updateDocument(document document) {
		repo.updateDocument(document);
		
	}

	@Override
	public document getDocumentById(int id) {
		return repo.getDocumentById(id);
	}

	@Override
	public List<document> getAllDocumentByCategory(String idcategory) {
		return repo.getAllDocumentByCategory(idcategory);
	}

	@Override
	public List<document> getDocumentByCategoryForHome(String idcategory) {
		return repo.getDocumentByCategoryForHome(idcategory);
	}

	@Override
	public HashMap<String, HashMap<Integer, String>> createJsonContainIdAndNameDocument() {
		return repo.createJsonContainIdAndNameDocument();
	}

	@Override
	public HashMap<String, HashMap<Integer, String>> createJsonContainIdAndSummaryDocument() {
		return repo.createJsonContainIdAndSummaryDocument();
	}

	

	
	
}
