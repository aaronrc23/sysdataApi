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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> registrar_POST(@RequestBody Almacenes almacenes)
    {
        Long isExist = almacenesService.countByNombre(almacenes.getNombreAlmacen());

        if (isExist == 0) {
            almacenes.setActivo(true);
            Almacenes nuevaCategoria = almacenesService.insert(almacenes);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("El almacen no se puede registrar porque " + almacenes.getNombreAlmacen().toUpperCase() + " ya existe!", HttpStatus.CONFLICT);
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


    @PutMapping("reactivar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void reactivarCategoria(@PathVariable Long id) {
        Almacenes existingAlmacen = almacenesService.findById(id);
        if (existingAlmacen != null) {
            existingAlmacen.setActivo(true);
            almacenesService.update(existingAlmacen);
        }
    }



    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticasCategorias() {
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("total", almacenesService.count());
        estadisticas.put("activas", Long.valueOf(almacenesService.findByActivos().size()));
        estadisticas.put("desactivadas", Long.valueOf(almacenesService.findByDesactivados().size()));
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }





    @GetMapping("/listar/activos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarDesactivadas_GET() {
        return new ResponseEntity<>(almacenesService.findByActivos(), HttpStatus.OK);
    }


    @GetMapping("/listar/desactivadas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarActivos_GET() {
        return new ResponseEntity<>(almacenesService.findByDesactivados(), HttpStatus.OK);
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
