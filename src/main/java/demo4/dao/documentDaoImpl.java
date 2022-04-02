package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.document;

@Repository
public class documentDaoImpl implements documentDao{
	
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public void saveDocument(document document) {
	Session session = sessionfactory.getCurrentSession();
	session.save(document);
		
	}

	@Override
	public void updateDocument(document document) {
		Session session = sessionfactory.getCurrentSession();
		session.update(document);
		
	}

	@Override
	public document getDocumentById(int id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(document.class, id);
	}
	
	
}
