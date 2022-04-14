package demo4.dao;

import java.util.List;

import demo4.model.documentDetail;

public interface documentDetailDao {
	public void saveDocumentDetail(documentDetail documentDetail);
	public List<documentDetail> getDocumentDetailByStudentId(String studentId);
	public documentDetail getDocumentDetailByPrimaryKey(String classroomId,String studentId);
	public void updateDocumentDetail(documentDetail documentDetail);
	public List<documentDetail> getDocumentDetailByClassroomId(String classroomId);
	public boolean checkDocumentDetailByPrimaryKey(String classroomId,String studentId);

}
