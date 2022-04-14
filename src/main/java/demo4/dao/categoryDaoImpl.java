package demo4.dao;

import java.util.List;

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

	@Override
	public void saveCategory(category category) {
		Session session = sessionfactory.getCurrentSession();
		session.save(category);
		
	}

	@Override
	public void updateCategory(category category) {
		Session session = sessionfactory.getCurrentSession();
		session.update(category);
		
	}

	@Override
	public void deleteCategory(String id) {
		Session session = sessionfactory.getCurrentSession();
		session.delete(getCategoryById(id));
	}

	@Override
	public List<category> getAllCategory() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From category";
		List<category> list = session.createQuery(hql,category.class).list();
		return list;
	}

	
	
}
