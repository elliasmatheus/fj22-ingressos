package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

public class SessaoTest {
	
	private static BigDecimal QUINHENTOS = new BigDecimal(500);
	private static BigDecimal DUZENTOS_E_CINQUENTA = new BigDecimal(250);
	
	private Filme matrix;
	private Sala iMax;
	
	@Before
	public void preparaSessao() {
		
		this.matrix = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", QUINHENTOS);
		this.iMax = new Sala("Sala 3D", DUZENTOS_E_CINQUENTA);
		
	}
	
	@Test
	public void garantePrecoSessaoIgualSomaPrecoSalaEPrecoFilme() {
		Sessao novaSessao = new Sessao(LocalTime.parse("13:00:00"),this.matrix, this.iMax);
		BigDecimal precoEsperado = this.matrix.getPreco().add(this.iMax.getPreco());
		Assert.assertEquals(precoEsperado, novaSessao.getPreco());
	}

}
