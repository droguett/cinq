package br.com.cinq.spring.data.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CountryRepository;

@Service
public class CountryService implements CountryServiceInterface{
	
	@Autowired
	private CountryRepository dao;

	@Override
	public Country getCountryById(Integer countryId) {
		return dao.findById(countryId);
	}
	
	@Override
	public List<Country> getCountryByNameLike(String name) {
		return dao.findByNameLike(name);
	}

	@Override
	public Country saveCountry(String name) throws Exception {
		List<Country> countries = getCountryByNameLike(name);
		for(Country c : countries) {
			if(c.getName().equals(name)) {
				throw new Exception();
			}
		}
		Country country = new Country();
		country.setName(name);
		dao.save(country);
		return country;
	}

}
