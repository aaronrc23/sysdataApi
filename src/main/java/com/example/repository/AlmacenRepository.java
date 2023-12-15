package com.example.repository;

import com.example.entity.Almacenes;
import com.example.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlmacenRepository extends JpaRepository<Almacenes, Long> {


    @Query(value = "SELECT COUNT(*) FROM almacenes WHERE numero_almacen = :numero_almacen", nativeQuery = true)
    Long countByNombre(@Param("numero_almacen") String numero_almacen);



}
