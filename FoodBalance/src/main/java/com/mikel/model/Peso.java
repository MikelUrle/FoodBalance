package com.mikel.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="peso")
public class Peso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="pesoTotal")
	private double pesoTotal;
	
	@Column(name="fecha")
	private LocalDate fecha;
	
	@ManyToOne
	private Usuario usuario;

	public Peso(int id, double pesoTotal, LocalDate fecha, Usuario usuario) {
		super();
		this.id = id;
		this.pesoTotal = pesoTotal;
		this.fecha = fecha;
		this.usuario = usuario;
	}

	public Peso() {
		super();
		this.id = 0;
		this.pesoTotal = 0.0;
		this.fecha = LocalDate.now();
		this.usuario = new Usuario();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(double pesoTotal) {
		this.pesoTotal = pesoTotal;
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
		return "Peso [id=" + id + ", pesoTotal=" + pesoTotal + ", fecha=" + fecha + ", usuario=" + usuario + "]";
	}
	
}
