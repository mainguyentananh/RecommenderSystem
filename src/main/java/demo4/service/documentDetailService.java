package demo4.service;

import java.util.List;

import demo4.model.documentDetail;

public interface documentDetailService {
	public void saveDocumentDetail(documentDetail documentDetail);
	public List<documentDetail> getDocumentDetailByStudentId(String studentId);
	public documentDetail getDocumentDetailByPrimaryKey(String classroomId,String studentId);
	public void updateDocumentDetail(documentDetail documentDetail);
	public List<documentDetail> getDocumentDetailByClassroomId(String classroomId);
	
}
