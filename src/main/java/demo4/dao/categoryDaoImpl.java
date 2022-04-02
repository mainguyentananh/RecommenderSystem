package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.category;

@Repository
public class categoryDaoImpl implements categoryDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public category getCategoryById(String id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(category.class, id);
	}

	
	
}
