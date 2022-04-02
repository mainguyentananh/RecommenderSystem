package demo4.dao;

import demo4.model.teacher;

public interface teacherDao {
	public teacher getTeacherById(String id);
	public void updateTeacher(teacher teacher);
}
