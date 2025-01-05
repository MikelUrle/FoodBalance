package com.mikel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="listacomida")
public class ListaComida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	private Comida comida;

	@Column(name="calorias")
	private double calorias;
	
	@Column(name="proteina")
	private double proteina;
	
	@Column(name="carbohidratos")
	private double carbohidratos;
	
	@Column(name="grasa")
	private double grasa;

	@Column(name="peso")
	private double peso;

	public ListaComida(int id, Comida comida, double calorias, double proteina, double carbohidratos, double grasa,
			double peso) {
		super();
		this.id = id;
		this.comida = comida;
		this.calorias = calorias;
		this.proteina = proteina;
		this.carbohidratos = carbohidratos;
		this.grasa = grasa;
		this.peso = peso;
	}

	public ListaComida() {
		super();
		this.id = id;
		this.comida = new Comida();
		this.calorias = 0.0;
		this.proteina = 0.0;
		this.carbohidratos = 0.0;
		this.grasa = 0.0;
		this.peso = 0.0;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Comida getComida() {
		return comida;
	}

	public void setComida(Comida comida) {
		this.comida = comida;
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

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "ListaComida [id=" + id + ", comida=" + comida + ", calorias=" + calorias + ", proteina=" + proteina
				+ ", carbohidratos=" + carbohidratos + ", grasa=" + grasa + ", peso=" + peso + "]";
	}
	
}

