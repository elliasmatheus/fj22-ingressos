package br.com.caelum.ingresso.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.rest.OmdbClient;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

@Controller
public class SessaoController {

	private SalaDao salaDao;
	private FilmeDao filmeDao;
	private SessaoDao sessaoDao;
	private OmdbClient client;
	private Carrinho carrinho;
	
	@Autowired
	public SessaoController(SalaDao salaDao,
			FilmeDao filmeDao,
			SessaoDao sessaoDao,
			OmdbClient client,
			Carrinho carrinho) {
		this.filmeDao = filmeDao;
		this.salaDao = salaDao;
		this.sessaoDao = sessaoDao;
		this.client = client;
		this.carrinho = carrinho;
	}

	public SalaDao getSalaDao() {
		return salaDao;
	}

	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}

	public FilmeDao getFilmeDao() {
		return filmeDao;
	}

	public void setFilmeDao(FilmeDao filmeDao) {
		this.filmeDao = filmeDao;
	}
	
	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId,
			SessaoForm form) {
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");
		modelAndView.addObject("sala", this.salaDao.findOne(salaId));
		modelAndView.addObject("filmes", filmeDao.findAll());
		modelAndView.addObject("form", form);
		
		return modelAndView;
	}
	
	@PostMapping("/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return form(form.getSalaId(),form);
		}
		Sessao sessao = form.toSessao(salaDao, filmeDao);
		
		List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(sessao.getSala());
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		
		if(gerenciador.cabe(sessao)) {
			sessaoDao.save(sessao);
			return new ModelAndView(
					"redirect:/admin/sala/" + form.getSalaId() + "/sessoes");			
		}
		
		return form(form.getSalaId(),form);
	}
	
	@DeleteMapping("/admin/sessao/{idSessao}")
	@Transactional
	@ResponseBody
	public void delete(@PathVariable("idSessao") Integer idSessao) {
		sessaoDao.deletarPorId(idSessao);
	}
	
	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId) {
		ModelAndView modelAndView = new ModelAndView("sessao/lugares");
		
		Sessao sessao = sessaoDao.findOne(sessaoId);
		Optional<ImagemCapa> imagemCapa = client.request(sessao.getFilme(), ImagemCapa.class);
		
		modelAndView.addObject("sessao", sessao);
		modelAndView.addObject("imagemCapa", imagemCapa.orElse(new ImagemCapa()));
		modelAndView.addObject("tiposDeIngressos", TipoDeIngresso.values());
		modelAndView.addObject("carrinho", carrinho );
		
		return modelAndView;
	}
		
}
