package com.example.repository;

import com.example.entity.Almacenes;
import com.example.entity.ProductoStock;
import com.example.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductoStockRepository extends JpaRepository<ProductoStock,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE ProductoStock ps SET ps.stock = ps.stock + :cantidad WHERE ps.producto.idproducto = :productoId AND ps.almacenes.id = :almacenId")
    void actualizarStock(@Param("productoId") Integer productoId, @Param("almacenId") Long almacenId, @Param("cantidad") int cantidad);

    ProductoStock findByProductoAndAlmacenes(Productos producto, Almacenes almacenes);

    @Transactional
    @Modifying
    @Query("UPDATE ProductoStock p SET p.stock = p.stock - :cantidad WHERE p.producto.idproducto = :productoId")
    void reducirStock(@Param("productoId") Integer productoId, @Param("cantidad") int cantidad);

    @Query("SELECT ps FROM ProductoStock ps WHERE ps.producto.codigo_barra = :codigoBarras")
    ProductoStock findByProducto_Codigo_barra(@Param("codigoBarras") String codigoBarras);


}
