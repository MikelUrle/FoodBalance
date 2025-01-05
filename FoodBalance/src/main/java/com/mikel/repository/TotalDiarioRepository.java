package com.mikel.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mikel.model.TotalDiario;
import com.mikel.model.Usuario;

@Repository
public interface TotalDiarioRepository extends JpaRepository<TotalDiario, Integer> {
	
	// Query para recoger el ultimo dato
    @Query("SELECT t FROM TotalDiario t ORDER BY t.id DESC LIMIT 1")
    TotalDiario findLastRecord();
 
    // Query para recoger el dato de la fecha actual
    @Query("SELECT t FROM TotalDiario t WHERE t.fecha = CURRENT_DATE")
    TotalDiario findByFechaActual();
    
    // Query para recoger los datos de la fecha actual de usuario seleccionado
    @Query("SELECT t FROM TotalDiario t WHERE t.fecha = CURRENT_DATE AND t.usuario.id = :idUsuario")
    TotalDiario findByFechaActualAndUsuario(@Param("idUsuario") int idUsuario);
    
    // Query para recoger los datos en formato LocalDate
    @Query("SELECT t FROM TotalDiario t WHERE t.fecha = :fecha")
    TotalDiario findByFechaLocalDate(@Param("fecha") LocalDate fechaCambioDeValores);

    // Query para recoger los datos de la fecha seleccionada en formato LocalDate de usuario seleccionado
	@Query("SELECT t FROM TotalDiario t WHERE t.fecha = :fecha AND t.usuario = :usuario")
    TotalDiario findByFechaAndUsuarioLocalDate(@Param("fecha") LocalDate fecha, @Param("usuario") Usuario usuario);
    
}
