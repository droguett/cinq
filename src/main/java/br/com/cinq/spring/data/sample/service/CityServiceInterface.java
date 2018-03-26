package br.com.cinq.spring.data.sample.service;

import java.util.List;

import br.com.cinq.spring.data.sample.entity.City;

public interface CityServiceInterface {
	
	List<City> getCitiesByCountryId(Integer countryId) throws Exception;
	
	List<City> getCitiesByCountryName(String name) throws Exception;
	
	City getCityById(Integer cityId);
	
	City saveCity(City city) throws Exception;

}
