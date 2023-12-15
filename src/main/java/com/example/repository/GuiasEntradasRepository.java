package com.example.repository;

import com.example.entity.GEntradas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiasEntradasRepository extends JpaRepository<GEntradas, Long> {
    GEntradas findByNumeroserie(String numeroserie);


}
