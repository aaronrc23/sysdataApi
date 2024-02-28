package com.example.repository;

import com.example.entity.Almacenes;
import com.example.entity.Categoria;
import com.example.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlmacenRepository extends JpaRepository<Almacenes, Long> {


    @Query(value = "SELECT COUNT(*) FROM almacenes WHERE nombre_almacen = :nombreAlmacen", nativeQuery = true)
    Long countByNombre(@Param("nombreAlmacen") String nombreAlmacen);

    List<Almacenes> findByActivo(boolean activo);
}
