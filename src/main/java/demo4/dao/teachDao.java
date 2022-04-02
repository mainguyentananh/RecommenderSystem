package demo4.dao;

import java.util.List;

import demo4.model.teach;

public interface teachDao {
	public void saveTeach(teach teach);
	public teach findTeachByClassroomId(String classroomid);
	public List<teach> getTeachByYearAndSemester(String teacherId,String year,String semester);
	public boolean checkTeachClassRoom(String teacherId,String classroomId);
}
