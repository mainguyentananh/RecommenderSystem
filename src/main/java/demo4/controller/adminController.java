package demo4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo4.model.category;
import demo4.service.categoryService;

@Controller
@RequestMapping(value = "/admin")
public class adminController {

	
	@Autowired
	private categoryService categoryService;
	
	@GetMapping(value = "/category")
	public String category(Model md) {
		md.addAttribute("listCategory", categoryService.getAllCategory());
		return "category";
	}
	

	@GetMapping(value = "/category/create")
	public String createCategory(Model md) {
		md.addAttribute("category", new category());
		return "createcategory";
	}
	
	@PostMapping(value = "/category/create")
	public String confirmCreateCategory(@ModelAttribute(value = "category") category category) {
		categoryService.saveCategory(category);
		return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/category/edit/{id}")
	public String editCategory(@PathVariable(value = "id") String id,Model md) {
		md.addAttribute("category", categoryService.getCategoryById(id));
		return "editcategory";
	}
	
	@PostMapping(value = "/category/edit")
	public String confirmEditCategory(@ModelAttribute(value = "category") category category) {
		categoryService.updateCategory(category);
		return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/category/delete/{id}")
	public String confirmDeleteCategory(@PathVariable(value = "id")String id) {
		categoryService.deleteCategory(id);
		return "redirect:/admin/category";
	}
}
