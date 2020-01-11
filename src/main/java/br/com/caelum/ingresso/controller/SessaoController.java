package br.com.caelum.ingresso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {

	private SalaDao salaDao;
	private FilmeDao filmeDao;
	
	@Autowired
	public SessaoController(SalaDao salaDao,
			FilmeDao filmeDao) {
		this.filmeDao = filmeDao;
		this.salaDao = salaDao;
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
	
}
