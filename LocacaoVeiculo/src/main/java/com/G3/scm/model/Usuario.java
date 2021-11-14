package com.G3.scm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {
	@Id
	@NotNull
	@Size(min = 1, max = 50, message = "Login deve ser preenchido")
	private String login;
	@NotNull
	@Size(min = 1, max = 50, message = "Nome deve ser preenchido")
	private String nome;
	@NotNull(message = "O tipo de permissão deve ser selecionado")
	private String permissao;
	@NotNull
	@Size(min = 1, max = 9999, message = "Senha deve ser preenchido")
	private String senha;
	@ManyToMany
	@JoinTable(name = "usuarios_papeis", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "login"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "papel"))
	private List<Role> roles = new ArrayList<>();

	public Usuario() {
	}
	
	public Usuario(String login, String nome, String permissao, String senha, List<Role> roles) {
		super();
		this.login = login;
		this.nome = nome;
		this.permissao = permissao;
		this.senha = senha;
		this.roles = roles;
	}



	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login + ", nome=" + nome + ", senha=" + senha + "]";
	}
}
