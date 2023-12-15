package com.example.controllers;

import com.example.entity.Productos;
import com.example.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<Productos>> listarProductos() {
        List<Productos> productos = productoService.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Productos> obtenerProducto(@PathVariable Integer id) {
        Productos producto = productoService.findById(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody Productos producto) {
        // Verificar si ya existe un producto con el mismo código de barras
        if (productoService.isExistCodigoBarra(producto.getCodigo_barra())) {
            return new ResponseEntity<>("¡El producto ya existe!", HttpStatus.CONFLICT);
        }

        // Si no existe, registra el producto
        productoService.insert(producto);
        return new ResponseEntity<>("¡Producto creado!", HttpStatus.CREATED);
    }




    @PutMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Productos> actualizarProducto(@PathVariable Integer id, @RequestBody Productos producto) {
        Productos existingProducto = productoService.findById(id);
        if (existingProducto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        producto.setIdproducto(id);
        productoService.update(producto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        Productos producto = productoService.findById(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar/{productoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> buscar_GET(@PathVariable Integer productoId)
    {
        Productos productoDb=productoService.findById(productoId);

        if(productoDb!=null) {
            return new ResponseEntity<>(productoDb,HttpStatus.FOUND);
        }

        return new ResponseEntity<>("¡Producto ID "+productoId+" no encontrado!",HttpStatus.NOT_FOUND);
    }
}
