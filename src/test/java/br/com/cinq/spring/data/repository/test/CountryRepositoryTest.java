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
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CountryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@IntegrationTest("server.port=9000")
@ActiveProfiles("unit")
public class CountryRepositoryTest {
	
	private static String TEST_FRANCE = "France";
	
	private static Integer TEST_FRANCE_ID = 3;
	
	private static Integer TEST_FAIL_COUNTRY_ID = 99;
	
	private static String TEST_FRANCE_LIKE = "Fra";
	
	private static String TEST_FAIL_COUNTRY_LIKE = "ZZZ";

    @Autowired
    private CountryRepository countryDao;
    
    @Test
	public void contextLoads() {
	}

    @Test
    public void testGelAllCountries() {

        Assert.assertNotNull(countryDao);

        long count = countryDao.count();

        Assert.assertTrue(count > 0);

        List<Country> countries = (List<Country>) countryDao.findAll();

        Assert.assertEquals((int) count, countries.size());
    }
    
    @Test
    public void testfindById() {
    	
    	Assert.assertNotNull(countryDao);

    	Country country = countryDao.findById(TEST_FRANCE_ID);

		assertThat(country.getName()).isEqualTo(TEST_FRANCE);
    }
    
    @Test
    public void testFailfindById() {
    	
    	Assert.assertNotNull(countryDao);

    	Country country = countryDao.findById(TEST_FAIL_COUNTRY_ID);

		Assert.assertNull(country);
    }
    
    @Test
    public void testFindByNameLike() {

        Assert.assertNotNull(countryDao);

        List<Country> countries = countryDao.findByNameLike(TEST_FRANCE_LIKE);

        Assert.assertEquals(1, countries.size());
        
        Country country = countries.get(0);

    	assertThat(country.getName()).isEqualTo(TEST_FRANCE);

    }
    
    @Test
    public void testFailFindByNameLike() {

        Assert.assertNotNull(countryDao);

        List<Country> countries = countryDao.findByNameLike(TEST_FAIL_COUNTRY_LIKE);

        assertThat(countries).isEmpty();
    }

}
