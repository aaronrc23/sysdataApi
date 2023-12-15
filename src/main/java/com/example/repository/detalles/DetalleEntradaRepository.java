package com.example.repository.detalles;

import com.example.dto.ProductoDto;
import com.example.entity.GEntradas;
import com.example.entity.detalles.DetalleEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {
    @Query("SELECT new com.example.dto.ProductoDto(p.idproducto, p.nombre, de.cantidad) " +
            "FROM DetalleEntrada de " +
            "JOIN de.producto p " +
            "WHERE de.entradas.gentradasId = :gentradasId")
    List<ProductoDto> findProductosEnGuiaEntradas(@Param("gentradasId") Long gentradasId);

    @Query(value = "SELECT D.* FROM detalle_entrada D JOIN entradas E ON D.entradas_id = E.gentradas_id WHERE E.gentradas_id = :entradaId", nativeQuery = true)
    List<DetalleEntrada> obtenerDetallesPorEntradaId(@Param("entradaId") Long entradaId);

    @Query(value = "SELECT d FROM DetalleEntrada d JOIN d.entradas e WHERE e.numeroserie = :numeroSerie")
    List<DetalleEntrada> findByNumeroSerie(@Param("numeroSerie") String numeroSerie);


    List<DetalleEntrada> findByEntradas_GentradasId(Long entradasId);


}
