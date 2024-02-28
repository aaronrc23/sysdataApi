package com.example.controllers;

import com.example.dto.TotalCarrito;
import com.example.entity.Comprobantes;
import com.example.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/comprobante")
@CrossOrigin(origins = "http://localhost:5173/")
public class ComprobanteController {
    @Autowired
    private ComprobanteService comprobanteService;
    @GetMapping("/calcularTotalComprobante/{id}")

    public TotalCarrito calcularTotalComprobante(@PathVariable Long id) {
        return comprobanteService.calcularTotalConIgvPorComprobante(id);
    }

    @GetMapping("/listarcomprobantes")
    public ResponseEntity<List<Comprobantes>> listarComprobantes() {
        List<Comprobantes> comprobantes = comprobanteService.listarComprobantes();
        return ResponseEntity.ok(comprobantes);
    }

    @GetMapping("/listarcomprobantes/{id}")
    public ResponseEntity<Comprobantes> listarComprobantePorId(@PathVariable Long id) {
        Optional<Comprobantes> comprobanteOptional = comprobanteService.obtenerComprobantePorId(id);
        if (comprobanteOptional.isPresent()) {
            Comprobantes comprobante = comprobanteOptional.get();
            return ResponseEntity.ok(comprobante);
        } else {
            return ResponseEntity.notFound().build();
        }
        }

}
