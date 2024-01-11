package com.example.controllers;

import com.example.dto.MovimientoDTO;
import com.example.entity.MovientoAlmacenes;
import com.example.service.MovientoAlmacenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/movimiento-almacenes")
public class MovientoController {

    @Autowired
    private  MovientoAlmacenesService movimientoService;

    @Autowired
    public void MovimientoAlmacenController(MovientoAlmacenesService service) {
        this.movimientoService = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MovientoAlmacenes>> findAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }
    @GetMapping("/listar/{id}")
    public ResponseEntity<MovientoAlmacenes> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @PostMapping("/realizar")
    public ResponseEntity<Object> realizarMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        movimientoService.realizarMovimiento(movimientoDTO);
        return ResponseEntity.ok().build();
    }


}
