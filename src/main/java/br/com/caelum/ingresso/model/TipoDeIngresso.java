package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.descontos.Desconto;
import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudantes;
import br.com.caelum.ingresso.model.descontos.SemDesconto;

public enum TipoDeIngresso {
	INTEIRO(new SemDesconto()),
	ESTUDANTE(new DescontoParaEstudantes()),
	BANCO(new DescontoParaBancos());
	
	private final Desconto desconto;

	public Desconto getDesconto() {
		return desconto;
	}

	private TipoDeIngresso(Desconto desconto) {
		this.desconto = desconto;
	}
	
	public String getDescricao() {
		return this.desconto.getDescricao();
	}

	public BigDecimal aplicarDescontoSobre(BigDecimal preco) {
		return this.desconto.aplicarDescontoSobre(preco);
	}
	
	

}
