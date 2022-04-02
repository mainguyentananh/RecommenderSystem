package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.categoryDao;
import demo4.model.category;

@Service
@Transactional
public class categoryServiceImpl implements categoryService{

	@Autowired
	private categoryDao repo;
	
	@Override
	public category getCategoryById(String id) {
		return repo.getCategoryById(id);
	}

}
