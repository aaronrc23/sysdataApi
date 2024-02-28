package com.example.repository;

import com.example.entity.ComprobanteDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobantesDetalleRepository extends JpaRepository<ComprobanteDetalle, Long> {
    ComprobanteDetalle save(ComprobanteDetalle comprobanteDetalle);
}
