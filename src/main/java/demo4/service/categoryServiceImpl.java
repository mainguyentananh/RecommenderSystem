package demo4.service;

import java.util.List;

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

	@Override
	public void saveCategory(category category) {
		repo.saveCategory(category);
		
	}

	@Override
	public void updateCategory(category category) {
		repo.updateCategory(category);
		
	}

	@Override
	public void deleteCategory(String id) {
		repo.deleteCategory(id);
		
	}

	@Override
	public List<category> getAllCategory() {
		return repo.getAllCategory();
	}

}
