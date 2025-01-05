package com.mikel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mikel.model.ComidaDiario;

@Repository
public interface ComidaDiarioRepository extends JpaRepository<ComidaDiario, Integer> {
	
	// Query para buscar en comida diario por la foreign key idTotalDiario
    @Query("SELECT c FROM ComidaDiario c WHERE c.totalDiario.id = :idTotalDiario")
    List<ComidaDiario> findByIdTotalDiario(@Param("idTotalDiario") Integer idTotalDiario);
	
}
