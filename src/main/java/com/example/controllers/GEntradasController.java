package com.example.controllers;



import com.example.dto.GEntradaRequest;
import com.example.dto.ProductoDto;
import com.example.entity.GEntradas;

import com.example.entity.detalles.DetalleEntrada;;
import com.example.service.DetalleEntradaService;
import com.example.service.GuiaEntradaService;
import com.example.serviceimpl.GEntradaServiceImpl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.slf4j.Logger;


@RestController
@RequestMapping("api/entradas")
public class GEntradasController {

    @Autowired
    private GuiaEntradaService gentradaservice;

    @Autowired
    private GEntradaServiceImpl entradasService;


    @Autowired
    private DetalleEntradaService detalleService;


    private static final Logger logger = LoggerFactory.getLogger(GEntradasController.class);


    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<GEntradas>> listarPedidos() {
        List<GEntradas> pedidos = gentradaservice.listarEntradas();
        return ResponseEntity.ok(pedidos);
    }


    @GetMapping("/listardetalle")   
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(detalleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("listardetalle/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public DetalleEntrada getProveedor(@PathVariable Long id) {
        return detalleService.findById(id);
    }




    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<GEntradas> crearPedido(@RequestBody GEntradaRequest gentradarequest) {
        GEntradas nuevoPedido = gentradaservice.crearEntrada(gentradarequest);


        return ResponseEntity.ok(nuevoPedido);
    }
    @GetMapping("/{gentradaId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<GEntradas> obtenerEntradaPorId(@PathVariable Long gentradaId) {
        GEntradas entrada = gentradaservice.obtenerEntradaPorId(gentradaId);

        if (entrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entrada);
    }


    @GetMapping("/{gentradasId}/productos")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<ProductoDto>> listarProductosEnPedido(@PathVariable Long gentradasId) {
        List<ProductoDto> productos = gentradaservice.listarProductosEnGuiaEntradas(gentradasId);
        return ResponseEntity.ok(productos);
    }


}
