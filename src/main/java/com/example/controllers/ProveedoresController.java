package com.example.controllers;


import com.example.entity.Categoria;
import com.example.entity.Proveedores;
import com.example.entity.UnidadMedida;
import com.example.service.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/proveedores")
public class ProveedoresController {

    @Autowired
    private ProveedoresService proveedoresService;


    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(proveedoresService.findAll(), HttpStatus.OK);
    }

    @GetMapping("listar/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Proveedores getProveedor(@PathVariable Long id) {
        return proveedoresService.findById(id);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody Proveedores proveedores)
    {
        Long isExist = proveedoresService.countByNombre(proveedores.getNumruc());

        if (isExist == 0) {
            proveedores.setActivo(true);
            Proveedores nuevaCategoria = proveedoresService.insert(proveedores);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La categoría no se puede registrar porque " + proveedores.getNumruc().toUpperCase() + " ya existe!", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void actualizarCategoria(@PathVariable Long id, @RequestBody Proveedores proveedores) {
        Proveedores existingCategoria = proveedoresService.findById(id);
        if (existingCategoria != null) {
            proveedores.setId_proveedores(id);
            proveedoresService.update(proveedores);
        }
    }

    
    /*----------Borrrado logico-------------- */
    @PutMapping("desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivarProveedor(@PathVariable Long id) {
        Proveedores existingProveedor = proveedoresService.findById(id);
        if (existingProveedor != null) {
            existingProveedor.setActivo(false);
            proveedoresService.update(existingProveedor);
        }
    }


    @PutMapping("reactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void reactivarProveedor(@PathVariable Long id) {
        Proveedores existingProveedor = proveedoresService.findById(id);
        if (existingProveedor != null) {
            existingProveedor.setActivo(true);
            proveedoresService.update(existingProveedor);
        }
    }


    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticasCategorias() {
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", proveedoresService.count());
        estadisticas.put("activas", Long.valueOf(proveedoresService.findByActivos().size()));
        estadisticas.put("desactivadas", Long.valueOf(proveedoresService.findByDesactivados().size()));
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }


    @GetMapping("/listar/activos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarDesactivadas_GET() {
        return new ResponseEntity<>(proveedoresService.findByActivos(), HttpStatus.OK);
    }


    @GetMapping("/listar/desactivadas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarActivos_GET() {
        return new ResponseEntity<>(proveedoresService.findByDesactivados(), HttpStatus.OK);
    }







    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        proveedoresService.delete(id);
    }


}
