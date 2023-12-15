package com.example.controllers;

import com.example.dto.FacturaRequest;
import com.example.dto.ProductoDto;
import com.example.entity.Factura;
import com.example.service.FacturaService;
import com.example.serviceimpl.FacturaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaServiceImpl facturasService;


    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<Factura>> listarPedidos() {
        List<Factura> pedidos = facturaService.listarfacturas();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{facturaId}/productos")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<ProductoDto>> listarProductosEnPedido(@PathVariable Long facturaId) {
        List<ProductoDto> productos = facturaService.listarProductosEnFacturas(facturaId);
        return ResponseEntity.ok(productos);
    }
    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Factura> crearPedido(@RequestBody FacturaRequest facturarequest) {
        Factura nuevoPedido = facturaService.crearEntrada(facturarequest);
        return ResponseEntity.ok(nuevoPedido);
    }



}
