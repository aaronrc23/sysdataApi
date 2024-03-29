package com.example.controllers;

import com.example.entity.Almacenes;
import com.example.entity.ProductoStock;
import com.example.entity.Productos;
import com.example.repository.ProductoRepository;
import com.example.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

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
            return new ResponseEntity<>(Collections.singletonMap("message", "¡El producto ya existe!"), HttpStatus.CONFLICT);
        }
        producto.setActivo(true);
        // Si no existe, registra el producto
        productoService.insert(producto);
        return new ResponseEntity<>(Collections.singletonMap("message", "¡Producto creado!"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void actualizarCategoria(@PathVariable Integer id, @RequestBody Productos producto) {
        Productos existingCategoria = productoService.findById(id);
        producto.setActivo(true);
        if (existingCategoria != null) {
            producto.setIdproducto(id);
            productoService.update(producto);
        }
    }


    @PutMapping("/reactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void activarProducto(@PathVariable Integer id) {
        Productos existingProducto = productoService.findById(id);
        if (existingProducto != null) {
            existingProducto.setActivo(true);
            productoService.update(existingProducto);
        }
    }



    @PutMapping("/desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivarProducto(@PathVariable Integer id) {
        Productos existingProducto = productoService.findById(id);
        if (existingProducto != null) {
            existingProducto.setActivo(false);
            productoService.update(existingProducto);
        }
    }



    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticasCategorias() {
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", productoService.count());
        estadisticas.put("activas", Long.valueOf(productoService.findByActivos().size()));
        estadisticas.put("desactivadas", Long.valueOf(productoService.findByDesactivados().size()));
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }


    @GetMapping("/listar/activos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarDesactivadas_GET() {
        return new ResponseEntity<>(productoService.findByActivos(), HttpStatus.OK);
    }


    @GetMapping("/listar/desactivadas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarActivos_GET() {
        return new ResponseEntity<>(productoService.findByDesactivados(), HttpStatus.OK);
    }






//    @PutMapping("/editar/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Productos> actualizarProducto(@PathVariable Integer id, @RequestBody Productos producto) {
//        Productos existingProducto = productoService.findById(id);
//        if (existingProducto == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        producto.setIdproducto(id);
//        productoService.update(producto);
//        return new ResponseEntity<>(producto, HttpStatus.OK);
//    }

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

    @GetMapping("/buscar")
    public ResponseEntity<List<Productos>> buscarProductos(
            @RequestParam(required = false) String codigo_barra,
            @RequestParam(required = false) String nombre) {

        if (codigo_barra != null) {
            return ResponseEntity.ok(productoRepository.findByCodigoBarra(codigo_barra));
        } else if (nombre != null) {
            return ResponseEntity.ok(productoRepository.findByNombreContainingIgnoreCase(nombre));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }




}
