package br.com.cinq.spring.data.sample.service;

import java.util.List;

import br.com.cinq.spring.data.sample.entity.Country;

public interface CountryServiceInterface {
	
	Country getCountryById(Integer countryId);
	
	List<Country> getCountryByNameLike(String name);
	
	Country saveCountry(String name) throws Exception;
	
}
