package demo4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.documentDetailDao;
import demo4.model.documentDetail;

@Service
@Transactional
public class documentDetailServiceImpl implements documentDetailService{

	@Autowired
	private documentDetailDao repo;
	@Override
	public void saveDocumentDetail(documentDetail documentDetail) {
		repo.saveDocumentDetail(documentDetail);
		
	}
	@Override
	public List<documentDetail> getDocumentDetailByStudentId(String studentId) {
		return repo.getDocumentDetailByStudentId(studentId);
	}
	@Override
	public documentDetail getDocumentDetailByPrimaryKey(String classroomId, String studentId) {
		return repo.getDocumentDetailByPrimaryKey(classroomId, studentId);
	}
	@Override
	public void updateDocumentDetail(documentDetail documentDetail) {
		repo.updateDocumentDetail(documentDetail);
		
	}
	@Override
	public List<documentDetail> getDocumentDetailByClassroomId(String classroomId) {
		return repo.getDocumentDetailByClassroomId(classroomId);
	}

}
