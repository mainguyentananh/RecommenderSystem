package demo4.dao;

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

	
	
}
