package br.com.cinq.spring.data.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cinq.spring.data.sample.entity.City;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
	
	List<City> findCitiesByCountryId(Integer countryId);
	
	City findById(Integer id);
	
	City findByName(String name);
	
	@Query("Select c from City c where c.name like %:name%")
	List<City> findByNameLike(@Param("name")String name);

}
