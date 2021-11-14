package com.G3.scm.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.G3.scm.model.Cliente;
import com.G3.scm.model.Usuario;
import com.G3.scm.servico.UserDetailsServiceI;

@Controller
@RequestMapping(path = "/sig")
public class UsuarioController {
	Logger logger = LogManager.getLogger(ClienteController.class);
	
	@Autowired
	UserDetailsServiceI servico;
	
	@GetMapping("/cadastrarUsuario")
	public ModelAndView retornaFormDeCadastroDe(Usuario usuario) {
		ModelAndView mv = new ModelAndView("cadastrarUsuario");
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@PostMapping("/cadastrarUsuario")
	public ModelAndView save(@Valid Usuario usuario, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("paginaLogin");
		if (result.hasErrors()) {
			logger.info(">>>>>> não vou salvar ");
			modelAndView.setViewName("cadastrarUsuario");
		} else {
			logger.info(">>>>>> Vou entrar para salva ");
			modelAndView = servico.saveOrUpdate(usuario);
		}
		return modelAndView;
	}
}
