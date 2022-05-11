package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.notify;

@Repository
public class notifyDaoImpl implements notifyDao{

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public notify getNotifyById(String id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(notify.class, id);
	}

	@Override
	public void saveNotify(notify n) {
		Session session = sessionfactory.getCurrentSession();
		session.save(n);
		
	}

	@Override
	public void updateNotify(notify n) {
		Session session = sessionfactory.getCurrentSession();
		session.update(n);
		
	}
	
	
}
