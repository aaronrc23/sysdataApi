package com.example.repository;

import com.example.entity.Cliente;
import com.example.entity.Comprobantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComprobantesRepository extends JpaRepository<Comprobantes,Long> {
    @Query("SELECT f FROM Comprobantes f JOIN FETCH f.detalles d WHERE f.id = :comprobanteId")
    Comprobantes findFacturaWithDetalles(@Param("comprobanteId") Long comprobanteId);
    @Query("SELECT c FROM Comprobantes c JOIN FETCH c.detalles")
    List<Comprobantes> findAllWithDetalles();



}
