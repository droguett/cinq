package br.com.cinq.spring.data.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cinq.spring.data.sample.entity.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

	@Query("Select c from Country c where c.name like %:name%")
	List<Country> findByNameLike(@Param("name")String name);

	Country findById(Integer id);
	
}
