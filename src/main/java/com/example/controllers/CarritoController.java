package com.example.controllers;

import com.example.dto.CarritoItemDTO;

import com.example.dto.DatosComprobanteDTO;
import com.example.dto.TotalCarrito;
import com.example.entity.CarritoItem;
import com.example.entity.ComprobanteDetalle;
import com.example.entity.Comprobantes;
import com.example.service.CarritoService;
import com.example.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private ComprobanteService comprobanteService;
    @PostMapping("/agregarItem")
    public void agregarItemAlCarrito(@RequestBody CarritoItemDTO carritoItemDTO) {
        carritoService.agregarItemAlCarrito(carritoItemDTO);
    }




//    @PostMapping("/agregarServicio")
//    public void agregarServicioAlCarrito(@RequestBody CarritoServicioDTO carritoItemDTO) {
//        carritoService.agregarServicioAlCarrito(carritoItemDTO);
//    }

    @DeleteMapping("/eliminarItem/{itemId}")
    public void eliminarItemDelCarrito(@PathVariable Long itemId) {
        carritoService.eliminarItemDelCarrito(itemId);
    }

    @GetMapping("/vaciar")
    public void vaciarCarrito() {
        carritoService.vaciarCarrito();
    }

    @GetMapping("/obtenerItems")
    public List<CarritoItem> obtenerItemsDelCarrito() {
        return carritoService.obtenerItemsDelCarrito();
    }

    @GetMapping("/calcularTotal")
    public TotalCarrito calcularTotal() {
        return carritoService.calcularTotalConIgv();
    }







    @PostMapping("/finalizar-compra")
    public ResponseEntity<Long> finalizarCompra(@RequestBody DatosComprobanteDTO datosComprobanteDTO) {
        Long comprobanteId = carritoService.finalizarCompra(datosComprobanteDTO);
        return ResponseEntity.ok(comprobanteId);
    }



    @PutMapping("/actualizarCantidad/{itemId}/{nuevaCantidad}")
    public ResponseEntity<Void> actualizarCantidadItemCarrito(
            @PathVariable Long itemId,
            @PathVariable int nuevaCantidad) {
        try {
            carritoService.actualizarCantidadItemCarrito(itemId, nuevaCantidad);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Loguear el error para un diagnóstico más detallado
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
