package com.example.service;

import com.example.dto.FacturaRequest;

import com.example.dto.ProductoDto;

import com.example.entity.Factura;
import com.example.entity.Productos;
import java.util.List;
import java.util.Optional;

public interface FacturaService {


    Factura  crearEntrada(FacturaRequest facturarequest);
    List<Factura> listarfacturas();
    List<ProductoDto> listarProductosEnFacturas(Long facturaId);

    Optional<Productos> findById(Integer id);

    
}
