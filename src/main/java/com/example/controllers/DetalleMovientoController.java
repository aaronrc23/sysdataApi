package com.example.controllers;


import com.example.entity.detalles.DetalleMoviento;
import com.example.service.DetalleMovientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/detalle-movimiento")
public class DetalleMovientoController {
    @Autowired
    private DetalleMovientoService service;

    @Autowired

    public void DetalleMovimientoController(DetalleMovientoService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public DetalleMoviento detalleMovientosaveDetalleMovimiento(@RequestBody DetalleMoviento detalleMovimiento) {
        return service.saveDetalleMoviento(detalleMovimiento);
    }
}
