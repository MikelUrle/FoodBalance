package com.mikel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comida")
public class Comida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="calorias")
	private double calorias;
	
	@Column(name="proteina")
	private double proteina;
	
	@Column(name="carbohidratos")
	private double carbohidratos;
	
	@Column(name="grasa")
	private double grasa;
	
	@ManyToOne
	private Categoria categoria;

	public Comida(int id, String nombre, double calorias, double proteina, double carbohidratos, double grasa,
			Categoria categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.calorias = calorias;
		this.proteina = proteina;
		this.carbohidratos = carbohidratos;
		this.grasa = grasa;
		this.categoria = categoria;
	}

	public Comida() {
		super();
		this.id = 0;
		this.nombre = "";
		this.calorias = 0.0;
		this.proteina = 0.0;
		this.carbohidratos = 0.0;
		this.grasa = 0.0;
		this.categoria = new Categoria();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCalorias() {
		return calorias;
	}

	public void setCalorias(double calorias) {
		this.calorias = calorias;
	}

	public double getProteina() {
		return proteina;
	}

	public void setProteina(double proteina) {
		this.proteina = proteina;
	}

	public double getCarbohidratos() {
		return carbohidratos;
	}

	public void setCarbohidratos(double carbohidratos) {
		this.carbohidratos = carbohidratos;
	}

	public double getGrasa() {
		return grasa;
	}

	public void setGrasa(double grasa) {
		this.grasa = grasa;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Comida [id=" + id + ", nombre=" + nombre + ", calorias=" + calorias + ", proteina=" + proteina
				+ ", carbohidratos=" + carbohidratos + ", grasa=" + grasa + ", categoria=" + categoria + "]";
	}
	
}
