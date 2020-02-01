package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.DetalhesDoFilme;
import br.com.caelum.ingresso.model.Filme;

@Component
public class OmdbClient {

	private static final String BUSCAR_DETALHES_FILME_URL = "https://omdb-fj22.herokuapp.com/movie?title=%s";

	public Optional<DetalhesDoFilme> request(Filme filme) {
		RestTemplate client = new RestTemplate();
		String titulo = filme.getNome().replace(" ", "+");
		String url = String.format(BUSCAR_DETALHES_FILME_URL, titulo);

		try {
			DetalhesDoFilme detalhesDoFilme = client.getForObject(url, DetalhesDoFilme.class);
			return Optional.of(detalhesDoFilme);
		} catch (RestClientException e) {
			return Optional.empty();
		}
	}

}
