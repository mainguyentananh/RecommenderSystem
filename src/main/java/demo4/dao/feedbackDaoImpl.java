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
		Object result = session.createQuery(hql,Object.class).setParameter("id", id).uniqueResult();
		return result;
	}

	@Override
	public List<Object[]> feedbackDocumentOfAccount() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "select f_account.id,f_document.id from feedback group by f_document.id,f_account.id order by f_account.id asc";
		List<Object[]> l = session.createQuery(hql, Object[].class).list();
		return l;
	}

	@Override
	public List<Object> listAccountFeedback() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "select f_account.id from feedback group by f_account.id";
		List<Object> l = session.createQuery(hql, Object.class).list();
		return l;
	}

	
	
}
