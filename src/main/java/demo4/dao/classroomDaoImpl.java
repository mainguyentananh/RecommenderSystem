package demo4.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.classroom;

@Repository
public class classroomDaoImpl implements classroomDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public classroom getClassroomById(String id) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(classroom.class, id);
	}

	@Override
	public void saveClassroom(classroom classroom) {
		Session session = sessionfactory.getCurrentSession();
		session.save(classroom);
	}

	@Override
	public boolean checkClassroomById(String id) {
		Session session = sessionfactory.getCurrentSession();
		if(session.get(classroom.class, id) != null) {
			return true;
		}
		return false;
	}

	
	
}
