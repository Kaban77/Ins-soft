package ru.demidov.insSoft.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@GetMapping(value = { "/", "/login" })
	public ModelAndView login(@RequestParam(value = "error", required = false) boolean error) {
		var model = new ModelAndView();

		if (error != false) {
			model.addObject("error", "Неверный логин или пароль!");
		}
		model.setViewName("login");
		return model;

	}

	@GetMapping(value = "/menu")
	public String menuPage() {
		return "menu";
	}
}
