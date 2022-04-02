package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.student;

@Repository
public class studentDaoImpl implements studentDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public student getStudentById(String id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(student.class, id);
	}

	@Override
	public void updateStudent(student stu) {
		Session session = sessionfactory.getCurrentSession();
		session.update(stu);
	}

	@Override
	public boolean checkStudent(String id) {
		Session session = sessionfactory.getCurrentSession();
		if(session.get(student.class, id) != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public void saveStudent(student student) {
		Session session = sessionfactory.getCurrentSession();
		session.save(student);
	}

	
	
}
