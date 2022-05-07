package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.teacher;

@Repository
public class teacherDaoImpl implements teacherDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public teacher getTeacherById(String id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(teacher.class, id);
	}

	@Override
	public void updateTeacher(teacher teacher) {
		Session session = sessionfactory.getCurrentSession();
		session.update(teacher);
		
	}

	@Override
	public boolean checkTeacher(String id) {
		Session session = sessionfactory.getCurrentSession();
		if(session.get(teacher.class, id) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void saveTeacher(teacher teacher) {
		Session session = sessionfactory.getCurrentSession();
		session.save(teacher);
	}

}
