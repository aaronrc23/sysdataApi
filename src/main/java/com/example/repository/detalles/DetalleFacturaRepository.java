package com.example.repository.detalles;

import com.example.dto.ProductoDto;

import com.example.entity.detalles.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

    @Query("SELECT new com.example.dto.ProductoDto(p.idproducto, p.nombre, de.cantidad) " +
            "FROM DetalleFactura de " +
            "JOIN de.producto p " +
            "WHERE de.facturas.idfactura = :idfactura")
    List<ProductoDto> findProductosEnFacturas(@Param("idfactura") Long idfactura);
}
