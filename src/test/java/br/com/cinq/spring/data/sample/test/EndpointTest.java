package br.com.cinq.spring.data.sample.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cinq.spring.data.sample.application.Application;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CityRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@IntegrationTest("server.port=9000")
@ActiveProfiles("unit")
public class EndpointTest {

	private static Integer TEST_COUNTRY_ID = 3;
	private static String TEST_COUNTRY_NAME = "France";
	private static String TEST_NEW_COUNTRY_NAME = "Chile";
	private static String TEST_FAIL_COUNTRY_NAME = "zzz";
	private static String TEST_FAIL_CITY_ID = "999";
	private static String TEST_FAIL_COUNTRY_ID = "999";

	private static String TEST_CITY_PARIS = "Paris";


	Logger logger = LoggerFactory.getLogger(EndpointTest.class);

	private final String localhost = "http://localhost:";

	@Value("${local.server.port}")
	private int port;

	private RestTemplate restTemplate = new TestRestTemplate();

	@Autowired
	private CityRepository cityDao;


	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetCities() throws InterruptedException {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/")
				.queryParam("country", TEST_COUNTRY_NAME);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<List> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, List.class);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		Thread.sleep(2000L);

		List<City> cities = (List<City>) response.getBody();

		Assert.assertEquals(2, cities.size());

	}

	@Test
	public void testFailGetCities() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/")
				.queryParam("country", TEST_FAIL_COUNTRY_NAME);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void testGetCity() {

		Assert.assertNotNull(cityDao);
		//procurando por ID da cidade de Paris
		List<City> cities = (List<City>) cityDao.findAll();
		City city = null;
		for (City c :  cities) {
			if (c.getName().equals(TEST_CITY_PARIS)) {
				city = c;
				break;
			}
		}
		//chamada do serviço de busca de Cidade
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/city/")
				.queryParam("cityId", city.getId());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<City> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, City.class);

		//validação do retorno httpStatus OK
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		//validação do retorno da Cidade
		City city2 = response.getBody();
		Assert.assertEquals(city2.getName(), city.getName());
	}

	@Test
	public void testFailGetCity() {

		Assert.assertNotNull(cityDao);

		//chamada do serviço de busca de Cidade
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/city/")
				.queryParam("cityId", TEST_FAIL_CITY_ID);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<?> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, City.class);

		//validação do retorno httpStatus OK
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		City city = (City)response.getBody();
		Assert.assertNull(city.getId());
	}

	@Test
	public void testSaveCity() {

		City city = new  City();
		city.setName("Curitiba");
		city.setCountryId(1);

		//chamada do serviço para inserir Cidade
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/city/");

		HttpEntity<?> entity = new HttpEntity<City>(city, headers);

		ResponseEntity<City> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, City.class);

		//validação do retorno httpStatus OK
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testFailSaveCity() {

		City city = new  City();
		city.setName("Santo Andre");

		//chamada do serviço para inserir Cidade
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/city/");

		HttpEntity<?> entity = new HttpEntity<>(city, headers);

		ResponseEntity<String> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, String.class);

		//validação do retorno httpStatus OK
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testGetCountry() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/country/")
				.queryParam("country", TEST_COUNTRY_ID);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Country> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, Country.class);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		Country country = response.getBody();

		Assert.assertNotNull(country);


		Assert.assertEquals(country.getName(), TEST_COUNTRY_NAME);
	}

	@Test
	public void testFailGetCountry() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/country/")
				.queryParam("country", TEST_FAIL_COUNTRY_ID);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Country> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, Country.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}
	
	@Test
	public void testSaveCountry() {

		Country country = new  Country();
		country.setName(TEST_NEW_COUNTRY_NAME);

		//chamada do serviço para inserir Cidade
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/country/");

		HttpEntity<?> entity = new HttpEntity<>(country, headers);

		ResponseEntity<Country> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, Country.class);

		//validação do retorno httpStatus OK
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testFailSaveCountry() {

		Country country= new Country();
		country.setName(TEST_COUNTRY_NAME);

		//chamada do serviço para inserir Cidade
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/country/");

		HttpEntity<?> entity = new HttpEntity<>(country, headers);

		ResponseEntity<City> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, City.class);

		//validação do retorno httpStatus OK
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
}
