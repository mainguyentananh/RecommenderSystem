package demo4.service;

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

	
	
}
