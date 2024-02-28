package com.example.controllers;

import com.example.entity.Almacenes;
import com.example.entity.ProductoStock;
import com.example.entity.detalles.DetalleEntrada;
import com.example.repository.ProductoStockRepository;
import com.example.repository.detalles.DetalleEntradaRepository;
import com.example.service.ProductoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/productostock")
public class ProductoStockController {
    @Autowired
    private ProductoStockRepository productorepository;
    @Autowired
    private ProductoStockService productoStockService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(productoStockService.findAll(), HttpStatus.OK);
    }

    @GetMapping("listardetalle/{idstock}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ProductoStock getstock(@PathVariable Long idstock) {
        return productoStockService.findById(idstock);
    }


    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody ProductoStock unidadMedida)
    {
        ProductoStock nuevaCategoria = productoStockService.insert(unidadMedida);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);

    }

    @GetMapping("/buscar/{codigoBarras}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> buscarPorCodigoBarras_GET(@PathVariable String codigoBarras) {
        ProductoStock productoEncontrado = productoStockService.findByProducto_Codigo_barra(codigoBarras);
        if (productoEncontrado != null) {
            return new ResponseEntity<>(productoEncontrado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }





}
