package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.account;

@Repository
public class accountDaoImpl implements accountDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public account findAccountByUserName(String username) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From account where username like :username";
		account ac = session.createQuery(hql,account.class).setParameter("username", username).uniqueResult();
		return ac;
	}

	@Override
	public account getAccountById(int id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(account.class, id);
	}

	@Override
	public void updateAccount(account ac) {
		Session session = sessionfactory.getCurrentSession();
		session.update(ac);
	}

	@Override
	public boolean checkAccountId(int id) {
		Session session = sessionfactory.getCurrentSession();
		if (session.get(account.class, id) != null) {
			return true;
		}
		return false;
	}
	

	@Override
	public void saveAccount(account ac) {
		Session session = sessionfactory.getCurrentSession();
		session.save(ac);
	}

	@Override
	public Object countAccount() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "select count(*) from account";
		Object count = session.createQuery(hql,Object.class).getSingleResult();
		return count;
	}






	
}
