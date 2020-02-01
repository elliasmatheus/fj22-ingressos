package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.Filme;

@Component
public class OmdbClient {

	private static final String BUSCAR_DETALHES_FILME_URL = "https://omdb-fj22.herokuapp.com/movie?title=%s";

	public <T> Optional<T> request(Filme filme, Class<T> tClass) {
		RestTemplate client = new RestTemplate();
		String titulo = filme.getNome().replace(" ", "+");
		String url = String.format(BUSCAR_DETALHES_FILME_URL, titulo);

		try {
			return Optional.of(client.getForObject(url, tClass));
		} catch (RestClientException e) {
			return Optional.empty();
		}
	}

}
