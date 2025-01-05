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
@Table(name="totaldiario")
public class TotalDiario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="fecha")
	private LocalDate fecha;
	
	@ManyToOne
	private Usuario usuario;

	public TotalDiario(int id, LocalDate fecha, Usuario usuario) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.usuario = usuario;
	}

	public TotalDiario() {
		super();
		this.id = id;
		this.fecha = LocalDate.now();
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "TotalDiario [id=" + id + ", fecha=" + fecha + ", usuario=" + usuario + "]";
	}
	
}
