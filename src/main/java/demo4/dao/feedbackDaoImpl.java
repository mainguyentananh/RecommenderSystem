package demo4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.feedback;

@Repository
public class feedbackDaoImpl implements feedbackDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public void saveFeedback(feedback feedback) {
		Session session = sessionfactory.getCurrentSession();
		session.save(feedback);
		
	}

	@Override
	public List<feedback> getFeedBackByIdDocument(int id) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From feedback where f_document.id =:id ORDER BY id DESC";
		List<feedback> list = session.createQuery(hql,feedback.class).setParameter("id", id).list();
		return list;
	}

	@Override
	public Object avgStarDocument(int id) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "select round(avg(score)) from feedback where score > 0 and f_document.id =:id";
		double object =  (double) session.createQuery(hql).setParameter("id", id).uniqueResult();
		int result = (int) Math.round(object);
		return result;
	}

	
	
}
