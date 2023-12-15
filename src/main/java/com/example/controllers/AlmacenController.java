package com.example.controllers;


import com.example.entity.Almacenes;
import com.example.entity.Categoria;
import com.example.entity.UnidadMedida;
import com.example.service.AlmacenesService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("api/almacen")
public class AlmacenController {

    @Autowired
    private AlmacenesService almacenesService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(almacenesService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody Almacenes unidadMedida)
    {
        Long isExist = almacenesService.countByNombre(unidadMedida.getNumeroAlmacen());

        if (isExist == 0) {
            Almacenes nuevaCategoria = almacenesService.insert(unidadMedida);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La categoría no se puede registrar porque " + unidadMedida.getNumeroAlmacen().toUpperCase() + " ya existe!", HttpStatus.CONFLICT);
        }
    }



    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void actualizarCategoria(@PathVariable Long id, @RequestBody Almacenes umd) {
        Almacenes existingCategoria = almacenesService.findById(id);
        if (existingCategoria != null) {
            umd.setId(id);
            almacenesService.update(umd);
        }
    }


    /*----------Borrrado logico-------------- */
//    @PutMapping("desactivar/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<String> desactivarCategoria(@PathVariable Long alamcenId) {
//        Almacenes existingAlmacenes = almacenesService.findById(alamcenId);
//
//        if (existingAlmacenes != null) {
//            existingAlmacenes.setActivo(false);
//            almacenesService.update(existingAlmacenes);
//            return new ResponseEntity<>("Almacén desactivado correctamente", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("No se encontró el almacén con el ID proporcionado", HttpStatus.NOT_FOUND);
//        }
//    }

    /*----------Borrrado logico-------------- */
    @PutMapping("desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivarCategoria(@PathVariable Long id) {
        Almacenes existingCategoria = almacenesService.findById(id);
        if (existingCategoria != null) {
            existingCategoria.setActivo(false);
            almacenesService.update(existingCategoria);
        }
    }



    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) {
        almacenesService.delete(id);
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("petsReport", "petsReport.pdf");
        return ResponseEntity.ok().headers(headers).body(almacenesService.exportPdf());
    }

    @GetMapping("/export-xls")
    public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
        var contentDisposition = ContentDisposition.builder("attachment")
                .filename("petsReport" + ".xls").build();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .body(almacenesService.exportXls());
    }








}
