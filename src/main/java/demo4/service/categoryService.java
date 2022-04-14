package demo4.service;

import java.util.List;

import demo4.model.category;

public interface categoryService {
	public category getCategoryById(String id);
	public void saveCategory(category category);
	public void updateCategory(category category);
	public void deleteCategory(String id);
	public List<category> getAllCategory();
}
