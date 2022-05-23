package demo4.service;

import java.util.List;

import demo4.model.teacher;

public interface teacherService {
	public teacher getTeacherById(String id);
	public void updateTeacher(teacher teacher);
	public boolean checkTeacher(String id);
	public void saveTeacher(teacher teacher);
	public List<teacher> getAllTeacher();
}
