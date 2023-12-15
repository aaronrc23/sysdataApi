package com.example.repository;

import com.example.entity.Almacenes;
import com.example.entity.ProductoAlmacen;
import com.example.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductoAlmacenRepository extends JpaRepository<ProductoAlmacen, Integer> {


//    @Transactional
//    @Modifying
//    @Query("UPDATE ProductoAlmacen pa SET pa.cantidad = :cantidad WHERE pa.producto = :producto AND pa.almacen = :almacen")
//    void actualizarStockActual(@Param("producto") Productos producto, @Param("almacen") Almacenes almacen, @Param("cantidad") int cantidad);
//
//    List<ProductoAlmacen> findByAlmacen(Almacenes almacen);

}
