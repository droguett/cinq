package br.com.cinq.spring.data.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import br.com.cinq.spring.data.sample.repository.CountryRepository;

@Service
public class CityService implements CityServiceInterface{

	@Autowired
	private CityRepository cityDao;

	@Autowired
	private CountryRepository countryDao;

	@Override
	public List<City> getCitiesByCountryId(Integer id) throws Exception {
		if(getCountryById(id) != null) {
			List<City> cities = cityDao.findCitiesByCountryId(id) ;
			return cities;
		}
		throw new Exception();
	}
	
	private Country getCountryById(Integer countryId) {
		return countryDao.findById(countryId);
	}

	@Override
	public List<City> getCitiesByCountryName(String name) throws Exception{
		List<Country> countries = countryDao.findByNameLike(name);
		List<City> cities = new ArrayList<City>();
		if (countries != null && !countries.isEmpty()) {
			for(Country country : countries) {
				Integer countryId = country.getId();
				cities.addAll(getCitiesByCountryId(countryId));
			}
			return cities;
		} 
		throw new Exception();
	}

	@Override
	public City getCityById(Integer cityId) {
		City city = cityDao.findById(cityId);
		return city;
	}

	@Override
	public City saveCity(City newCity) throws Exception {
		Integer countryId = newCity.getCountryId();
		Country country = getCountryById(countryId);
		if (country != null) {
			City city = new City();
			city.setName(newCity.getName());
			city.setCountryId(countryId);
			cityDao.save(city);
			return city;
		} 
		throw new Exception();
	}

}
