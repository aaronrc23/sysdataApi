package com.example.controllers;


import com.example.entity.Categoria;
import com.example.entity.Marcas;
import com.example.entity.UnidadMedida;
import com.example.service.UnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/undmedida")
public class UnidadMedidaController {
    
    @Autowired
    private UnidadMedidaService umdService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(umdService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody UnidadMedida unidadMedida)
    {
        Long isExist = umdService.countByNombre(unidadMedida.getNombre());

        if (isExist == 0) {
            UnidadMedida nuevaCategoria = umdService.insert(unidadMedida);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La categoría no se puede registrar porque " + unidadMedida.getNombre().toUpperCase() + " ya existe!", HttpStatus.CONFLICT);
        }
    }

    /*----------Actualizar Unidad de Medida-------------- */

    @PutMapping("actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void actualizarCategoria(@PathVariable Long id, @RequestBody UnidadMedida umd) {
        UnidadMedida existingCategoria = umdService.findById(id);
        if (existingCategoria != null) {
           umd.setId_umd(id);
            umdService.update(umd);
        }
    }

    /*----------Borrrado logico-------------- */
    @PutMapping("desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivarCategoria(@PathVariable Long id) {
        UnidadMedida existingCategoria = umdService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setActivo(false);
            umdService.update(existingCategoria);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        umdService.delete(id);
    }
    
}
