package com.example.repository.detalles;


import com.example.entity.detalles.DetalleMoviento;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface DetalleMovientoRepository extends JpaRepository<DetalleMoviento, Long>{


}
