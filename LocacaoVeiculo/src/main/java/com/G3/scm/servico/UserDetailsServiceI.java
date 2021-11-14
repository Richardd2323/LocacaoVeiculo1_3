package com.G3.scm.servico;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.G3.scm.controller.ClienteController;
import com.G3.scm.model.Role;
import com.G3.scm.model.RoleRepository;
import com.G3.scm.model.Usuario;
import com.G3.scm.model.UsuarioRepository;


@Component
@Transactional
public class UserDetailsServiceI implements UserDetailsService {
	Logger logger = LogManager.getLogger(ClienteController.class);
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private RoleRepository repositoryR;
	
	@Autowired
	PasswordEncoder passwordEncoder; 

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = repository.findByLogin(login);
		if (usuario == null) {
			logger.info(">>>>>> usuario invalido");
			throw new UsernameNotFoundException("Usuario nao encontrado.");
		}
		logger.info(">>>>>> UserDetailsServiceI - usuario valido login = " + usuario.getLogin());
		logger.info(">>>>>> UserDetailsServiceI - usuario valido senha = " + usuario.getSenha());
		return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}
	
	public ModelAndView saveOrUpdate(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("paginaLogin");
		Role role = repositoryR.findByPapel(usuario.getPermissao());
		logger.info(role.getPapel());
		try {
			logger.info(">>>>>> To guardando ");
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			logger.info(">>>>>> To aqui ");
			usuario.getRoles().addAll(Arrays.asList(role));
			repository.save(usuario);
		} catch (Exception e) {
			logger.info(">>>>>> Não to guardando ");
			modelAndView.setViewName("cadastrarUsuario");
			
		}
		return modelAndView;
	}
}
