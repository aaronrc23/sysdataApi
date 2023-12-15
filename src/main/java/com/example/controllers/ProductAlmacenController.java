package com.example.controllers;


import com.example.entity.Almacenes;
import com.example.entity.ProductoAlmacen;
import com.example.service.ProductoAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/productAlmacen")
public class ProductAlmacenController {

    @Autowired
    private ProductoAlmacenService productAlmacenService;


    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(productAlmacenService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_POST(@RequestBody ProductoAlmacen productoAlmacen) {

        productAlmacenService.insert(productoAlmacen);
        return new ResponseEntity<>(productoAlmacen, HttpStatus.CREATED);
    }


    /*----------Actualizar Almacen-------------- */

    @PutMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editar_PUT(@RequestBody ProductoAlmacen productoAlmacenNew,@PathVariable Integer id)
    {
        ProductoAlmacen almacenesDb=productAlmacenService.findById(id);

        if(almacenesDb!=null)
        {
            almacenesDb.setCantidad(productoAlmacenNew.getCantidad());
            almacenesDb.setFechaMovimiento(productoAlmacenNew.getFechaMovimiento());
            almacenesDb.setProducto(productoAlmacenNew.getProducto());
            productAlmacenService.update(almacenesDb);
            return new ResponseEntity<>("¡moviento de Almacen editado!",HttpStatus.OK);
        }

        return new ResponseEntity<>("moviento de Almacen ID "+id+" no encontrado!",HttpStatus.NOT_FOUND);
    }



}
