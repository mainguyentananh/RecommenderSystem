package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.account_role;

@Repository
public class account_roleDaoImpl implements account_roleDao{

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public void saveAccount_Role(account_role account_role) {
		Session session = sessionfactory.getCurrentSession();
		session.save(account_role);
		
	}
}
