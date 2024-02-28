package com.example.repository;

import com.example.entity.Carrito;
import com.example.entity.EstadoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito ,Long> {

    Optional<Carrito> findByEstado(EstadoCarrito estado);


}
