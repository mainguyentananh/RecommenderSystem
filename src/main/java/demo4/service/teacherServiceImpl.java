package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.teacherDao;
import demo4.model.teacher;

@Service
@Transactional
public class teacherServiceImpl implements teacherService{

	@Autowired
	private teacherDao repo;
	
	@Override
	public teacher getTeacherById(String id) {
		return repo.getTeacherById(id);
	}

	@Override
	public void updateTeacher(teacher teacher) {
		repo.updateTeacher(teacher);
		
	}

	
	
}
