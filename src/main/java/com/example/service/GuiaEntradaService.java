package com.example.service;

import com.example.dto.GEntradaRequest;
import com.example.dto.ProductoDto;
import com.example.entity.GEntradas;
import com.example.entity.Productos;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface GuiaEntradaService {

    GEntradas crearEntrada(GEntradaRequest gentradarequest);
    List<GEntradas> listarEntradas();
    List<ProductoDto> listarProductosEnGuiaEntradas(Long gentradasId);

    Optional<Productos> findById(Integer id);
    GEntradas findById(Long gentradasId);

    GEntradas obtenerEntradaPorId(Long gentradaId);

}
