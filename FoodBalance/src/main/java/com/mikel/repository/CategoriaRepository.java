package com.mikel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mikel.model.Categoria;

import jakarta.transaction.Transactional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	// Query para la busqueda de la categoria por su id
	@Query("SELECT c FROM Categoria c WHERE c.id = :categoria_id")
	Categoria findByCategoriaId(@Param("categoria_id") int categoria_id);
	
	// Query para la modificacion de la categoria pasandole su id y el nuevo nombre
	@Modifying
	@Transactional
	@Query("UPDATE Categoria c SET c.nombre = :nombre WHERE c.id = :id")
	int updateCategoria(
	    @Param("id") int id,
	    @Param("nombre") String nombre
	);

	// Query para eliminar la categoria por su id
	@Modifying
	@Transactional
	@Query("DELETE FROM Categoria c WHERE c.id = :categoria_id")
	void eliminarCategoria(@Param("categoria_id") int categoriaId);

	
}

