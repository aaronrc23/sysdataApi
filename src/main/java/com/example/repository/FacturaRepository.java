package com.example.repository;

import com.example.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FacturaRepository extends JpaRepository<Factura,Long> {
}
