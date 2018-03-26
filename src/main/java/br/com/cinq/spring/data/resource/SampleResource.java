package br.com.cinq.spring.data.resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.service.CityServiceInterface;
import br.com.cinq.spring.data.sample.service.CountryServiceInterface;

@Path("/")
@Component
public class SampleResource {
	
	@Autowired
	private CityServiceInterface cityService;

	@Autowired
	private CountryServiceInterface countryService;

	Logger logger = LoggerFactory.getLogger(SampleResource.class);

	@Path("/cities")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCities(@QueryParam("country") String country) {
		logger.info("Retrieving cities for country: " + country);
		
			try {
				if(isNumeroRegexp(country)) {
					Integer id = Integer.valueOf(country.toString());
				return (Response) retorno(cityService.getCitiesByCountryId(id));
				}
				String countryName = country.toString();
				return (Response) retorno(cityService.getCitiesByCountryName(countryName));
			} catch (Exception e) {
				return Response.status(Status.NOT_FOUND).entity("Not found " + country).build();
			}
	}


	@Path("/city")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCity(@QueryParam("cityId") String cityId) {
		logger.info("Retrieving city: " + cityId);
		if(isNumeroRegexp(cityId)) {
			return (Response) retorno(cityService.getCityById(Integer.valueOf(cityId)));
		}
		return (Response) retorno(null);
	}

	@Path("/city")
	@POST
	@Consumes("application/json")
	public Response saveCity(City city) {
		logger.info("Saving city: " + city);
		try {
			return retorno(cityService.saveCity(city));
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(city.toString()).build();
		}
	}

	@Path("/country")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCountry(@QueryParam("country") String country) {
		logger.info("Retrieving country: " + country);
		if(isNumeroRegexp(country)) {
			Integer id = Integer.valueOf(country.toString());
			return (Response) retorno(countryService.getCountryById(id));
		}
		String name = country.toString();
		return (Response) retorno(countryService.getCountryByNameLike(name));
	}

	@Path("/country")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response saveCountry(Country country) {
		logger.info("Saving country: " + country);
		try {
			return (Response) retorno(countryService.saveCountry(country.getName()));
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(country).build();
		}
	}

	@Produces(MediaType.APPLICATION_JSON)
	private Response retorno(Object object) {
		try {
			if (object != null) {
				return Response.status(Status.OK).entity(object).build();	
			} else {
				return Response.status(Status.NOT_FOUND).entity(object).build();
			}
		} catch (NumberFormatException e) {
			logger.info("Parameter:", object.toString(), " it's not a numer");
			return Response.status(Status.BAD_REQUEST).entity("Parameter: " + object.toString() +" it's not number").build();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(null).build();
		}

	}
	
	private static boolean isNumeroRegexp(String texto) {
		Pattern pat = Pattern.compile("[0-9]+");      
		Matcher mat = pat.matcher(texto);
		return mat.matches();
	}

}

