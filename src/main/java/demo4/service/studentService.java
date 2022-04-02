package demo4.service;

import demo4.model.student;

public interface studentService {
	public student getStudentById(String id);
	public void updateStudent(student stu);
	public boolean checkStudent(String id);
	public void saveStudent(student student);
}
