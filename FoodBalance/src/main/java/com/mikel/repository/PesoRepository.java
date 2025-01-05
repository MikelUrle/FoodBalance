package com.mikel.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mikel.model.Peso;
import com.mikel.model.Usuario;

@Repository
public interface PesoRepository extends JpaRepository<Peso, Integer> {

	// Query para buscar el peso de un usuario seleccionado
	List<Peso> findByUsuario(Usuario usuario);
	
	// Query para buscar el ultimo registro de peso de un usuario seleccionado
    @Query("SELECT p FROM Peso p WHERE p.usuario = :usuario ORDER BY p.id DESC")
    Peso findLastByUsuario(@Param("usuario") Usuario usuario);

    // Query para recoger los datos de peso de todo el mes (actual) de un usuario seleccionado
    @Query("SELECT p FROM Peso p WHERE p.usuario = :usuario AND MONTH(p.fecha) = MONTH(CURRENT_DATE) AND YEAR(p.fecha) = YEAR(CURRENT_DATE)")
    List<Peso> findByUsuarioAndMonth(@Param("usuario") Usuario usuario);

    // Query para recoger los datos de peso de todo el mes seleccionado y del usuario seleccionado
    @Query("SELECT p FROM Peso p WHERE p.usuario = :usuario AND MONTH(p.fecha) = MONTH(:fecha) AND YEAR(p.fecha) = YEAR(:fecha)")
    List<Peso> findByUsuarioAndSpecificMonth(@Param("usuario") Usuario usuario, @Param("fecha") Date fecha);

    // Query para recoger los datos de peso de año (actual) del usuario seleccionado
    @Query("SELECT p FROM Peso p WHERE p.usuario = :usuario AND YEAR(p.fecha) = YEAR(CURRENT_DATE)")
    List<Peso> findByUsuarioAndCurrentYear(@Param("usuario") Usuario usuario);
    
    // Query para recoger los datos de peso del año seleccionado del usuario seleccionado
    @Query("SELECT p FROM Peso p WHERE p.usuario = :usuario AND YEAR(p.fecha) = YEAR(:fecha)")
    List<Peso> findByUsuarioAndSpecificYear(@Param("usuario") Usuario usuario, @Param("fecha") Date fecha);
    
}
