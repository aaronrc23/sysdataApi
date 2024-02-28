package com.example.service;

import com.example.dto.TotalCarrito;
import com.example.entity.Comprobantes;

import java.util.List;
import java.util.Optional;

public interface ComprobanteService {

    TotalCarrito calcularTotalConIgvPorComprobante(Long idComprobante);
    List<Comprobantes> listarComprobantes();
    Optional<Comprobantes> obtenerComprobantePorId(Long id);
}
