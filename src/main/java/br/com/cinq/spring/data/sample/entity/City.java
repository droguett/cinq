package br.com.cinq.spring.data.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="City")
public class City implements Serializable{

	private static final long serialVersionUID = -1093413030029689832L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	@Column(name="name")
	public String name;
	
	@Column(name="country_id")
	public Integer countryId;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "country_id", nullable = false, insertable=false, updatable=false)
//    private Country country;

	public City() {
	}

	public City(Integer id, String name, Integer countryId) {
		super();
		this.id = id;
		this.name = name;
		this.countryId = countryId;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


//	public Country getCountry() {
//		return country;
//	}
//
//
//	public void setCountry(Country country) {
//		this.country = country;
//	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

}
