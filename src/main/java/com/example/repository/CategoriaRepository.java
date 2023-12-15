package com.example.repository;

import com.example.entity.Almacenes;
import com.example.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value="select count(*) from categorias where nombre = :nombre", nativeQuery=true)
    Long countByNombre(@Param("nombre") String nombre);


}
