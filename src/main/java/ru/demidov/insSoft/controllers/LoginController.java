package ru.demidov.insSoft.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Maxim Demiodov
 */
@Controller
@SessionAttributes("user")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

		ModelAndView model = new ModelAndView();

		if (error != null) {
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