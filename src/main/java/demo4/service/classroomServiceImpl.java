package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.classroomDao;
import demo4.model.classroom;

@Service
@Transactional
public class classroomServiceImpl implements classroomService{

	@Autowired
	private classroomDao repo;
	
	@Override
	public classroom getClassroomById(String id) {
		return repo.getClassroomById(id);
	}

	@Override
	public void saveClassroom(classroom classroom) {
		repo.saveClassroom(classroom);
		
	}

	@Override
	public boolean checkClassroomById(String id) {
		return repo.checkClassroomById(id);
	}

	
	
}
