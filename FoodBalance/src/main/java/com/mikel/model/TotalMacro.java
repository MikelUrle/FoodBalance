package com.mikel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="totalmacro")
public class TotalMacro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="calorias")
	private double calorias;
	
	@Column(name="proteina")
	private double proteina;
	
	@Column(name="carbohidratos")
	private double carbohidratos;
	
	@Column(name="grasa")
	private double grasa;
	
	@Column(name="fecha")
	private LocalDate fecha;
	
	@ManyToOne
	private Usuario usuario;

	public TotalMacro(int id, double calorias, double proteina, double carbohidratos, double grasa, LocalDate fecha,
			Usuario usuario) {
		super();
		this.id = id;
		this.calorias = calorias;
		this.proteina = proteina;
		this.carbohidratos = carbohidratos;
		this.grasa = grasa;
		this.fecha = fecha;
		this.usuario = usuario;
	}

	public TotalMacro() {
		super();
		this.id = 0;
		this.calorias = 0.0;
		this.proteina = 0.0;
		this.carbohidratos = 0.0;
		this.grasa = 0.0;
		this.fecha = LocalDate.now();;
		this.usuario = new Usuario();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "TotalMacro [id=" + id + ", calorias=" + calorias + ", proteina=" + proteina + ", carbohidratos="
				+ carbohidratos + ", grasa=" + grasa + ", fecha=" + fecha + ", usuario=" + usuario + "]";
	}
	
}
