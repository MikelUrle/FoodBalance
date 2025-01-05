package com.mikel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mikel.model.Comida;

import jakarta.transaction.Transactional;

@Repository
public interface ComidaRepository extends JpaRepository<Comida, Integer> {
	
	// Query para buscar la comida por la categoria
	@Query("SELECT c FROM Comida c WHERE c.categoria.id = :categoriaid")
	List<Comida> findByCategoria(@Param("categoriaid") int categoriaid);
	
	// Query para buscar la comida por su id
	@Query("SELECT c FROM Comida c WHERE c.id = :comida_id")
	List<Comida> findByComidaId(@Param("comida_id") int comida_id);

	// Query para modificar la comida pasandole la id y sus demas parametros
    @Modifying
    @Transactional
    @Query("UPDATE Comida c SET c.nombre = :nombre, c.calorias = :calorias, c.proteina = :proteina, c.grasa = :grasa, c.carbohidratos = :carbohidratos WHERE c.id = :id")
    int updateComida(
        @Param("id") int id,
        @Param("nombre") String nombre,
        @Param("calorias") double calorias,
        @Param("proteina") double proteina,
        @Param("grasa") double grasa,
        @Param("carbohidratos") double carbohidratos
    );
	
    // Query para eliminar la comida por su id
    @Modifying
    @Transactional
    @Query("DELETE FROM Comida c WHERE c.id = :id")
    int eliminarComida(@Param("id") int id);
    
}
