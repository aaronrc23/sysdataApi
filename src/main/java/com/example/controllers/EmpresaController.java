package com.example.controllers;

import com.example.entity.Almacenes;
import com.example.entity.Empresa;
import com.example.entity.ProductoAlmacen;
import com.example.entity.UnidadMedida;
import com.example.repository.EmpresaRepository;
import com.example.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(empresaService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody Empresa empresas)
    {
        empresaService.insert(empresas);
        return new ResponseEntity<>(empresas, HttpStatus.CREATED);

    }

    /*----------Actualizar Almacen-------------- */

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void actualizarCategoria(@PathVariable Long id, @RequestBody Empresa emp) {
        Empresa existingCategoria = empresaService.findById(id);
        if (existingCategoria != null) {
            emp.setId_empresa(id);
            empresaService.update(emp);
        }
    }


    /*----------Borrrado logico-------------- */
    @PutMapping("desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivarCategoria(@PathVariable Long id) {
        Empresa existingCategoria = empresaService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setActivo(false);
            empresaService.update(existingCategoria);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        empresaService.delete(id);
    }


}
