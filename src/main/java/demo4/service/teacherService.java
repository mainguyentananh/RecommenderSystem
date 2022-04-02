package demo4.service;

import demo4.model.teacher;

public interface teacherService {
	public teacher getTeacherById(String id);
	public void updateTeacher(teacher teacher);
}
