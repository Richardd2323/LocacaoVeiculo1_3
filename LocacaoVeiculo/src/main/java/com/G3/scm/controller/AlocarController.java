package com.G3.scm.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.G3.scm.model.Alocar;
import com.G3.scm.model.Cliente;
import com.G3.scm.model.Veiculo;
import com.G3.scm.servico.AlocarServicoI;
import com.G3.scm.servico.ClienteServico;
import com.G3.scm.servico.VeiculoServico;

@Controller
@RequestMapping(path = "/sig")
public class AlocarController {
	Logger logger = LogManager.getLogger(AlocarController.class);
	@Autowired
	AlocarServicoI servico;
	@Autowired
	ClienteServico servicoC;
	@Autowired
	VeiculoServico servicoV;
	
	@GetMapping("/alocados")
	public ModelAndView retornaFormDeConsultaTodosAlocacoes() {
		ModelAndView modelAndView = new ModelAndView("consultarAlocacoes");
		modelAndView.addObject("alocados", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/alocar")
	public ModelAndView retornaFormDeCadastroDe(Alocar alocar) {
		ModelAndView mv = new ModelAndView("alocar");
		mv.addObject("alocar", alocar);
		return mv;
	}

	@GetMapping("/alocados/{id}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarAlocacao(@PathVariable("id") Long id) {
		Alocar alocacao = servico.findById(id);
		ModelAndView modelAndView = new ModelAndView("desalocar");
		if(alocacao.isSituacao() == true) {
			modelAndView.addObject("alocar", servico.findById(id)); // o repositorio e injetado no controller
		}
		else {
			modelAndView.setViewName("consultarAlocacoes");
			modelAndView.addObject("alocados", servico.findAll());
			modelAndView.addObject("message", "Alocação já foi encerrada");
		}
		
		return modelAndView; // addObject adiciona objetos para view
	}

	

	@PostMapping("/alocados")
	public ModelAndView save(@Valid Alocar alocar, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAlocacoes");
		//Cliente cliente = servicoC.findByCpf(alocar.getClienteCpf());
		//Veiculo veiculo = servicoV.findByPlaca(alocar.getVeiculoPlaca());
		if (result.hasErrors()) {
			modelAndView.setViewName("alocar");
			//modelAndView.addObject("message", "Cliente ou veiculo não existe");
			
		} else {
			try {
				modelAndView = servico.save(alocar);
			}
			catch (HttpClientErrorException e) {
				modelAndView.addObject("message", "Dados invalidos - 400 Bad Request");
				modelAndView.setViewName("alocar");
			} catch (ConstraintViolationException e) {
				modelAndView.addObject("message", "Dados invalidos - constraint violation");
				modelAndView.setViewName("alocar");
			} catch (DataIntegrityViolationException e) {
				modelAndView.addObject("message", "Dados invalidos - não foi possivel efetuar a alcação");
				modelAndView.setViewName("alocar");
			} catch (Exception e) {
				modelAndView = new ModelAndView("alocar");
				modelAndView.addObject("message", "Erro não esperado - contate o administrador ==>" + e.getMessage());
				logger.error(">>>>> 5. erro nao esperado ==> " + e.getMessage());
				logger.error(">>>>> 5. erro nao esperado ==> " + e.toString());
			}
			
		}
		return modelAndView;
	}

	@PostMapping("/alocados/{id}")
	public ModelAndView atualizaAlocacao(@PathVariable("id") Long id, Alocar alocar, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAlocacoes");
		if (result.hasErrors()) {
			alocar.setId(id);
			return new ModelAndView("desalocar");
		}
		else {
			try {
				Alocar umaAlocagem = servico.findById(id);
				Cliente umCliente = servicoC.findByCpf(umaAlocagem.getClienteCpf());
				Veiculo umVeiculo = servicoV.findByPlaca(umaAlocagem.getVeiculoPlaca());
				umaAlocagem.setSituacao(false);
				umCliente.setAlocacao(false);
				umVeiculo.setLocado(false);
				modelAndView = servico.save2(umaAlocagem);
			}
			catch (HttpClientErrorException e) {
				modelAndView.addObject("message", "Dados invalidos - 400 Bad Request");
				modelAndView.setViewName("desalocar");
			} catch (ConstraintViolationException e) {
				modelAndView.addObject("message", "Dados invalidos - constraint violation");
				modelAndView.setViewName("desalocar");
			} catch (DataIntegrityViolationException e) {
				modelAndView.addObject("message", "Dados invalidos - Não foi possivel efetuar a desalocagem");
				modelAndView.setViewName("desalocar");
			} catch (Exception e) {
				modelAndView = new ModelAndView("desalocar");
				modelAndView.addObject("message", "Erro não esperado - contate o administrador ==>" + e.getMessage());
				logger.error(">>>>> 5. erro nao esperado ==> " + e.getMessage());
				logger.error(">>>>> 5. erro nao esperado ==> " + e.toString());
			}
			
			
		}
		return modelAndView;
	}
	
	/*@GetMapping("/desalocar")
	public ModelAndView desalocacao(Alocar alocar) {
		ModelAndView mv = new ModelAndView("desalocar");
		mv.addObject("alocar", alocar);
		return mv;
	}*/
	
	/*@PostMapping("/realizarDesalocacao")
	public ModelAndView DesalocarCliente( Alocar alocar) {
		ModelAndView modelAndView = new ModelAndView("consultarAlocacoes");
		Cliente umCliente = servicoC.findByCpf(alocar.getClienteCpf());
		Veiculo umVeiculo = servicoV.findByPlaca(alocar.getVeiculoPlaca());
		Alocar umaAlocagem = servico.findById(umCliente.getAlocacaoId());
		if(umaAlocagem.isSituacao() == false ) {
			return new ModelAndView("desalocar");
		}
		
		else {
			umaAlocagem.setSituacao(false);
			umCliente.setAlocacao(false);
			umVeiculo.setLocado(false);
			modelAndView = servico.saveOrUpdate(umaAlocagem);
			return modelAndView;
		}
		
	}*/
	
}
