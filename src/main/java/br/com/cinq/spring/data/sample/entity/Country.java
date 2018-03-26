package br.com.cinq.spring.data.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Country")
public class Country implements Serializable{
	
	private static final long serialVersionUID = -8747070014720189380L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	public Integer id;

	@Column(name="name")
	public String name;
	
//	@OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            mappedBy = "country")
//    private List<City> cities;

	public Country() {
		super();
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
	
//	public List<City> getCities() {
//		return cities;
//	}
//
//	public void setCities(List<City> cities) {
//		this.cities = cities;
//	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";//", cities=" + cities + "]";
	}

}
