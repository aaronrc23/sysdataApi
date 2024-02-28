package com.example.controllers;


import com.example.entity.Categoria;
import com.example.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/categoria")
@CrossOrigin(origins = "https://sysdatademofront.web.app/")
//@CrossOrigin(origins = "http://localhost:4200/")
public class CategoriController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(categoriaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("listar/{id}")
    public Categoria getCategoria(@PathVariable Long id) {
        return categoriaService.findById(id);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody Categoria categoria) {
        Long isExist = categoriaService.countByNombre(categoria.getNombre());

        if (isExist == 0) {
            // Establecer activo como true por defecto
            categoria.setActivo(true);
            Categoria nuevaCategoria = categoriaService.insert(categoria);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La categoría no se puede registrar porque " + categoria.getNombre().toUpperCase() + " ya existe!", HttpStatus.CONFLICT);
        }
    }


    @PutMapping("actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria existingCategoria = categoriaService.findById(id);
        if (existingCategoria != null) {
            categoria.setIdcategoria(id);
            categoriaService.update(categoria);
        }
    }

    /*----------Borrrado logico-------------- */
    @PutMapping("desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivarCategoria(@PathVariable Long id) {
        Categoria existingCategoria = categoriaService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setActivo(false);
            categoriaService.update(existingCategoria);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
    }


    @GetMapping("/listar/desactivadas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarDesactivadas_GET() {
        return new ResponseEntity<>(categoriaService.findAllDesactivadas(), HttpStatus.OK);
    }


    @GetMapping("/listar/activos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarActivos_GET() {
        return new ResponseEntity<>(categoriaService.findAllActivos(), HttpStatus.OK);
    }


    @PutMapping("reactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void reactivarCategoria(@PathVariable Long id) {
        Categoria existingCategoria = categoriaService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setActivo(true);
            categoriaService.update(existingCategoria);
        }
    }
    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticasCategorias() {
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", categoriaService.count());
        estadisticas.put("activas", Long.valueOf(categoriaService.findAllActivos().size()));
        estadisticas.put("desactivadas", Long.valueOf(categoriaService.findAllDesactivadas().size()));
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }

}
