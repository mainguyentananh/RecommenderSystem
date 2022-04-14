package demo4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.documentDetail;
import demo4.model.documentDetail.pk_documentDetail;

@Repository
public class documentDetailDaoImpl implements documentDetailDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public void saveDocumentDetail(documentDetail documentDetail) {
		Session session = sessionfactory.getCurrentSession();
		session.save(documentDetail);
	}

	@Override
	public List<documentDetail> getDocumentDetailByStudentId(String studentId) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From documentDetail where pk_documentDetail.student_id like :studentId";
		List<documentDetail> list = session.createQuery(hql,documentDetail.class).setParameter("studentId", studentId).list();
		return list;
	}

	@Override
	public documentDetail getDocumentDetailByPrimaryKey(String classroomId, String studentId) {
		Session session = sessionfactory.getCurrentSession();
		pk_documentDetail pk = new pk_documentDetail(classroomId,studentId);
		return session.get(documentDetail.class,pk);
	}

	@Override
	public void updateDocumentDetail(documentDetail documentDetail) {
		Session session = sessionfactory.getCurrentSession();
		session.update(documentDetail);
		
	}

	@Override
	public List<documentDetail> getDocumentDetailByClassroomId(String classroomId) {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From documentDetail where pk_documentDetail.classroom_id like :classroomId";
		List<documentDetail> list = session.createQuery(hql,documentDetail.class).setParameter("classroomId", classroomId).list();
		return list;
	}

	@Override
	public boolean checkDocumentDetailByPrimaryKey(String classroomId, String studentId) {
		Session session = sessionfactory.getCurrentSession();
		pk_documentDetail pk = new pk_documentDetail(classroomId,studentId);
		if(session.get(documentDetail.class,pk) != null) {
			return true;
		}
		return false;
	}

	

}
