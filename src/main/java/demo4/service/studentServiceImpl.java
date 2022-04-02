package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.studentDao;
import demo4.model.student;

@Transactional
@Service
public class studentServiceImpl implements studentService{

	@Autowired
	private studentDao repo;
	
	@Override
	public student getStudentById(String id) {
		return repo.getStudentById(id);
	}

	@Override
	public void updateStudent(student stu) {
		repo.updateStudent(stu);
		
	}

	@Override
	public boolean checkStudent(String id) {
		return repo.checkStudent(id);
	}

	@Override
	public void saveStudent(student student) {
		repo.saveStudent(student);
		
	}

}
