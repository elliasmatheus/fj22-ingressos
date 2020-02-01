package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudantes;
import br.com.caelum.ingresso.model.descontos.SemDesconto;
import junit.framework.Assert;

public class DescontoTest {
	
	private Sala sala;
	private Filme filme;
	private Sessao sessao;
	private Lugar lugar;

	@Before
	public void preparaTeste() {
		sala = new Sala("Eldorao - IMAX", new BigDecimal("20.5"));
		filme = new Filme("Rogue One", Duration.ofMinutes(120),
				"SCI-FI", new BigDecimal("12"));
		sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		lugar = new Lugar("A",1);
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugar);
		BigDecimal precoEsperado = new BigDecimal("32.50");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDescontoDe50PorCentoParaIngressoDeEstudante() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE, lugar);
		BigDecimal precoEsperado = new BigDecimal("16.25");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDesconto30PorCentoParaIngressoDeClientesDeBancos() {
		Ingresso ingresso = new Ingresso(sessao,TipoDeIngresso.BANCO, lugar);
		BigDecimal precoEsperado = new BigDecimal("22.75");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
		
	}

}
