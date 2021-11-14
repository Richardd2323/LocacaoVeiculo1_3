package com.G3.scm.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.G3.scm.controller.ClienteController;
import com.G3.scm.model.Usuario;
import com.G3.scm.model.UsuarioRepository;


@Component
@Transactional
public class UserDetailsServiceI implements UserDetailsService {
	Logger logger = LogManager.getLogger(ClienteController.class);
	@Autowired
	private UsuarioRepository repository;

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
}
