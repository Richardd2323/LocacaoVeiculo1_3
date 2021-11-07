package com.G3.scm.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;


@Entity
public class Alocar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@CPF(message = "CPF invalido")
	private String clienteCpf;
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@NotNull
	@Size(min = 7, max = 7, message = "Placa invalida")
	private String veiculoPlaca;
	@ManyToOne
	@JoinColumn(name = "veiculo_id")
	private Veiculo veiculo;
	private boolean situacao;
	private String dtInicioFormat;
	@NotNull
	@Size(min = 10, max = 10, message = "Data invalida")
	private String dtEntregaFormat;
	private LocalDate dtInicio;
	private LocalDate dtEntrega;
	private float valorTotal;
	
	
	
	public Alocar() {}
	public Alocar(String cpf, String placa, String dtInicioFormat, String dtEntregaFormat ) {
		this.clienteCpf = cpf;
		this.veiculoPlaca = placa;
		this.dtInicioFormat = dtInicioFormat;
		this.dtEntregaFormat = dtEntregaFormat;
		this.situacao = false;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getClienteCpf() {
		return clienteCpf;
	}
	public void setClienteCpf(String clienteCpf) {
		this.clienteCpf = clienteCpf;
	}
	
	public String getVeiculoPlaca() {
		return veiculoPlaca;
	}
	public void setVeiculoPlaca(String veiculoPlaca) {
		this.veiculoPlaca = veiculoPlaca;
	}
	
	public boolean isSituacao() {
		return situacao;
	}
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	public String getDtInicioFormat() {
		return dtInicioFormat;
	}
	public void setDtInicioFormat(String dtInicioFormat) {
		this.dtInicioFormat = dtInicioFormat;
	}
	public String getDtEntregaFormat() {
		return dtEntregaFormat;
	}
	public void setDtEntregaFormat(String dtEntregaFormat) {
		this.dtEntregaFormat = dtEntregaFormat;
	}
	public LocalDate getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(String dtInicio) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(dtInicio, formato); 
		this.dtInicio = data;
		
	}
	public LocalDate getDtEntrega() {
		return dtEntrega;
	}
	public void setDtEntrega(String dtEntrega) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(dtEntrega, formato); 
		this.dtEntrega = data;
	}
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	
	
	
	
}
