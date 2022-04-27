package demo4.dao;

import java.util.HashMap;
import java.util.List;

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

	@Override
	public List<document> getAllDocumentByCategory(String idcategory) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From document where d_category.id like :idcategory ORDER BY id DESC";
		List<document> list = session.createQuery(hql,document.class).setParameter("idcategory", idcategory).list();
		return list;
	}

	@Override
	public List<document> getDocumentByCategoryForHome(String idcategory) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From document where d_category.id like :idcategory ORDER BY id DESC";
		List<document> list = session.createQuery(hql,document.class).setParameter("idcategory", idcategory).setFirstResult(0).setMaxResults(4).list();
		return list;
	}

	@Override
	public HashMap<String, HashMap<Integer, String>> createJsonContainIdAndNameDocument() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From document ORDER BY id DESC";
		List<document> listDocument = session.createQuery(hql,document.class).list();
		
		
		HashMap<String, HashMap<Integer, String>> jsonContainIdAndNameDocument = new HashMap<String, HashMap<Integer, String>>();
		HashMap<Integer, String> idAndNameDocument = new HashMap<Integer, String>();
		for (document x : listDocument) {
			idAndNameDocument.put(x.getId(), x.getName());
		}
		
		jsonContainIdAndNameDocument.put("document", idAndNameDocument);
		return jsonContainIdAndNameDocument;
	}

	@Override
	public HashMap<String, HashMap<Integer, String>> createJsonContainIdAndSummaryDocument() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From document ORDER BY id DESC";
		List<document> listDocument = session.createQuery(hql,document.class).list();
		
		
		HashMap<String, HashMap<Integer, String>> jsonContainIdAndNameDocument = new HashMap<String, HashMap<Integer, String>>();
		HashMap<Integer, String> idAndSummaryDocument = new HashMap<Integer, String>();
		for (document x : listDocument) {
			idAndSummaryDocument.put(x.getId(), x.getSummary());
		}
		
		jsonContainIdAndNameDocument.put("document", idAndSummaryDocument);
		return jsonContainIdAndNameDocument;
	}
	
	
}
