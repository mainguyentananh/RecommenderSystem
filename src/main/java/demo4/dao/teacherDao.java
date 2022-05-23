package demo4.dao;

import java.util.List;

import demo4.model.teacher;

public interface teacherDao {
	public teacher getTeacherById(String id);
	public void updateTeacher(teacher teacher);
	public boolean checkTeacher(String id);
	public void saveTeacher(teacher teacher);
	public List<teacher> getAllTeacher();
}
