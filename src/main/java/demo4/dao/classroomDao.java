package demo4.dao;

import demo4.model.classroom;

public interface classroomDao {
	public classroom getClassroomById(String id);
	public void saveClassroom(classroom classroom);
	public boolean checkClassroomById(String id);
}
