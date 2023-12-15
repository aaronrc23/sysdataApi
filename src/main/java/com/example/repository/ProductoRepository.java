package com.example.repository;

import com.example.entity.Categoria;
import com.example.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Productos,Integer> {

    @Query(value = "SELECT * FROM productos WHERE codigo_barra = :codigo", nativeQuery = true)
    List<Productos> findByCodigoBarra(@Param("codigo") String codigo);


    List<Productos> findByCategoria(Categoria categoria);



}
