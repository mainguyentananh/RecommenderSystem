package demo4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.teachDao;
import demo4.model.teach;

@Service
@Transactional
public class teachServiceImpl implements teachService{

	@Autowired
	private teachDao repo;
	@Override
	public void saveTeach(teach teach) {
		repo.saveTeach(teach);
	}
	@Override
	public teach findTeachByClassroomId(String classroomid) {
		return repo.findTeachByClassroomId(classroomid);
	}
	@Override
	public List<teach> getTeachByYearAndSemester(String teacherId, String year, String semester) {
		return repo.getTeachByYearAndSemester(teacherId, year, semester);
	}
	@Override
	public boolean checkTeachClassRoom(String teacherId, String classroomId) {
		return repo.checkTeachClassRoom(teacherId, classroomId);
	}
	
}
