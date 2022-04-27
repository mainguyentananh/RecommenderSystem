package demo4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {
	
	@GetMapping(value = "/login")
	public String logIn(@RequestParam(value = "error", required = false) String error, ModelMap model) {
		if (error != null) {
			model.addAttribute("message",				
					"<div class=\"alert alert-danger\" role=\"alert\">Invalid login, please try again </div>"
				);
		}
		return "login";
	}
	
	@GetMapping(value = "/logout")
	public String logOut() {
		return "login";
	}
	
	@GetMapping(value = "/403")
	public String accessDenied() {
	    return "403";
	  }
	


	
}
 
