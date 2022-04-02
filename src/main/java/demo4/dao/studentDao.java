package demo4.dao;

import demo4.model.student;

public interface studentDao {
	public student getStudentById(String id);
	public void updateStudent(student student);
	public boolean checkStudent(String id);
	public void saveStudent(student student);
}
