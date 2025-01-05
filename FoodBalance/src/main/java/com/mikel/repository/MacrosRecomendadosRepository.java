package com.mikel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mikel.model.MacrosRecomendados;
import com.mikel.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface MacrosRecomendadosRepository extends JpaRepository<MacrosRecomendados, Integer> {
	
	// Query para recoger los datos de los macros recomendados por el usuario seleccionado
	List<MacrosRecomendados> findByUsuario(Usuario usuario);
	
	// Query para eliminar los macros recomendados ya calculados previamente de la base de datos
	@Modifying
	@Transactional
	@Query("DELETE FROM MacrosRecomendados m WHERE m.usuario = :usuario")
	void borrarPorUsuario(@Param("usuario") Usuario usuario);
	
}
