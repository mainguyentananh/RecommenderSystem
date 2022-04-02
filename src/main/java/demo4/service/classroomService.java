package demo4.service;

import demo4.model.classroom;

public interface classroomService {
	public classroom getClassroomById(String id);
	public void saveClassroom(classroom classroom);
	public boolean checkClassroomById(String id);
}
