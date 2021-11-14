package com.G3.scm.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.G3.scm.servico.UserDetailsServiceI;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

	Logger logger = LogManager.getLogger(ConfiguracaoDeSeguranca.class);
	@Autowired
	private UserDetailsServiceI userDetailsService;

	// configuracao de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		logger.info(">>>>>> metodo configura http security executado");
		http.csrf().disable().authorizeRequests().antMatchers("HttpMethod.GET", "/").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/menuCliente").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/cliente").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/clientes").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.POST", "/sig/clientes").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/menuVeiculo").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/veiculo").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/veiculos").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.POST", "/sig/veiculos").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/alocar").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/alocados").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.POST", "/sig/alocados").hasAnyRole("ADMIN", "USER")
				.antMatchers("HttpMethod.GET", "/sig/cadastrarUsuario").permitAll()
				.antMatchers("HttpMethod.POST", "/sig/cadastrarUsuario").permitAll()
				.and().formLogin().loginPage("/login").permitAll().and().logout().logoutUrl("/login?logout").permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and().authorizeRequests()
				.antMatchers("/h2-console/**").hasRole("ADMIN").anyRequest().authenticated();
		/*
		 * http.authorizeRequests().antMatchers("/cliente").hasAnyRole("ADMIN", "VEND")
		 * // .antMatchers("/veiculo").hasRole("ADMIN") // somente login maria
		 * .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll
		 * ().and().logout() .logoutSuccessUrl("/login?logout").permitAll();
		 */
	}

	// configuracao de autenticacao
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("jose").password(pc().encode("123")).roles("ADMIN").and()
				.withUser("maria").password(pc().encode("456")).roles("VEND");

		logger.info(">>>>>> gerenciador de autenticacao = ");
		auth.userDetailsService(userDetailsService).passwordEncoder(pc());
	}

	@Bean
	public BCryptPasswordEncoder pc() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/h2-console/**");
	}
}
