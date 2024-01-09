package com.example.service;

import com.example.dto.MovimientoDTO;
import com.example.entity.MovientoAlmacenes;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MovientoAlmacenesService {

    MovientoAlmacenes findById(Long idmoviento);
    List<MovientoAlmacenes>findAll();

    void realizarMovimiento(MovimientoDTO movimientoDTO);

}
