package br.com.cinq.spring.data.repository.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.cinq.spring.data.sample.application.Application;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CityRepository;

/**
 * Eye candy: implements a sample in using JpaRespositories
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@IntegrationTest("server.port=9000")
@ActiveProfiles("unit")
public class CityRepositoryTest {

	private static Integer TEST_FRANCE_ID = 3;
	
	private static Integer TEST_FAILT_CITY_ID = 100;
	
	private static Integer TEST_FAIL_COUNTRY_ID = 99;

	private static String TEST_CITY_PARIS = "Paris";
	
	private static String TEST_FAIL_CITY = "ZZZ";
	
	private static String TEST_CITY_LIKE = "Par";

	private static Integer TEST_LIST_CITIES_SIZE_FRANCE = 2;

	@Autowired
	private CityRepository cityDao;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllCities() {
		Assert.assertNotNull(cityDao);

		long count = cityDao.count();

		Assert.assertTrue(count > 0);

		List<City> cities = (List<City>) cityDao.findAll();

		Assert.assertEquals((int) count, cities.size());
	}

	@Test
	public void testFindByCitiesByCountryId() {

		List<City> cities = cityDao.findCitiesByCountryId(TEST_FRANCE_ID);

		assertThat(TEST_LIST_CITIES_SIZE_FRANCE).isEqualTo(cities.size());
	}
	
	@Test
	public void testFailFindByCitiesByCountryId() {

		List<City> cities = cityDao.findCitiesByCountryId(TEST_FAIL_COUNTRY_ID);

		assertThat(cities).isEmpty();
	}

	@Test
	public void testFindById() {

		Assert.assertNotNull(cityDao);

		List<City> cities = (List<City>) cityDao.findAll();
		City city = null;
		for (City c :  cities) {
			if (c.getName().equals(TEST_CITY_PARIS)) {
				city = c;
				break;
			}
		}

		City city2 = cityDao.findById(city.getId());

		assertThat(city2.getName()).isEqualTo(TEST_CITY_PARIS);

	}
	
	@Test
	public void testFailFindById() {

		Assert.assertNotNull(cityDao);

		City city = cityDao.findById(TEST_FAILT_CITY_ID);
		Assert.assertNull(city);
	}

	@Test
	public void testFindByName() {
		Assert.assertNotNull(cityDao);

		City city = cityDao.findByName(TEST_CITY_PARIS);

		Assert.assertNotNull(city);

		assertThat(city.getName()).isEqualTo(TEST_CITY_PARIS);

	}
	
	@Test
	public void testFailtFindByName() {
		Assert.assertNotNull(cityDao);

		City city = cityDao.findByName(TEST_FAIL_CITY);

		Assert.assertNull(city);
	}

	@Test
	public void testFindByNameLike() {
		Assert.assertNotNull(cityDao);

        List<City> cities = cityDao.findByNameLike(TEST_CITY_LIKE);

        Assert.assertEquals(1, cities.size());
        
        City city = cities.get(0);

    	assertThat(city.getName()).isEqualTo(TEST_CITY_PARIS);
	}
	
	@Test
	public void testFailtFindByNameLike() {
		Assert.assertNotNull(cityDao);

		List<City> cities = cityDao.findByNameLike(TEST_FAIL_CITY);

		assertThat(cities).isEmpty();
	}
}
