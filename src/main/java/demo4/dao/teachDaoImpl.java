package demo4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.teach;
import demo4.model.teach.pk_teach;

@Repository
public class teachDaoImpl implements teachDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public void saveTeach(teach teach) {
		Session session = sessionfactory.getCurrentSession();
		session.save(teach);
	}

	@Override
	public teach findTeachByClassroomId(String classroomid) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From teach where pk_teach.classroom_id like :classroomid";
		teach t = session.createQuery(hql,teach.class).setParameter("classroomid", classroomid).uniqueResult();
		return t;
	}

	@Override
	public List<teach> getTeachByYearAndSemester(String teacherId, String year, String semester) {
		Session session = sessionfactory.getCurrentSession();	
		// year and semester all
		if(year.equalsIgnoreCase("-1-0") && semester.equalsIgnoreCase("0")) {
			String hql = "From teach where pk_teach.teacher_id like :teacherId";
			 List<teach> list = session.createQuery(hql,teach.class).setParameter("teacherId", teacherId).list();
			
			return list;
		}
		if(year.equalsIgnoreCase("-1-0")) { //year all
			String hql = "From teach where pk_teach.teacher_id like :teacherId and semester like :semester";
			List<teach> list = session.createQuery(hql,teach.class).setParameter("teacherId", teacherId)
					.setParameter("semester", semester)
					.list();
			return list;
		}
		if(semester.equalsIgnoreCase("0")) {  //semester all
			String hql = "From teach where pk_teach.teacher_id like :teacherId and year like :year";
			List<teach> list = session.createQuery(hql,teach.class).setParameter("teacherId", teacherId)
					.setParameter("year", year)
					.list();
			return list;
		}else {
		String hql = "From teach where pk_teach.teacher_id like :teacherId and year like :year and semester like :semester";
		List<teach> list = session.createQuery(hql,teach.class).setParameter("teacherId", teacherId)
				.setParameter("year", year)
				.setParameter("semester", semester)
				.list();	
		return list;
		}
	}

	@Override
	public boolean checkTeachClassRoom(String teacherId, String classroomId) {
		Session session = sessionfactory.getCurrentSession();
		pk_teach pk_teach = new pk_teach();
		pk_teach.setClassroom_id(classroomId);
		pk_teach.setTeacher_id(teacherId);
		if(session.get(teach.class, pk_teach) != null) {
			return true;
		}
		return false;
	}

	

}
