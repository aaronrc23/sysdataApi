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


    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        proveedoresService.delete(id);
    }


}
