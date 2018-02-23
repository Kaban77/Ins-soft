package ru.demidov.insSoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Maxim Demiodov
 */
@Controller
public class LoginController {

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) boolean error) {

		ModelAndView model = new ModelAndView();

		if (error != false) {
			model.addObject("error", "Неверный логин или пароль!");
		}
		model.setViewName("login");
		return model;

	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menuPage() {

		return "menu";
	}
}
