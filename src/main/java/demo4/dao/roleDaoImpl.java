package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.role;

@Repository
public class roleDaoImpl implements roleDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public role findRoleByRoleName(String roleName) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From role where name like :roleName";
		role r = session.createQuery(hql,role.class).setParameter("roleName", roleName).uniqueResult();
		return r;
	}

}
