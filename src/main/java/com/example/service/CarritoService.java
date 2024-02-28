package com.example.service;

import com.example.dto.CarritoItemDTO;
import com.example.dto.DatosComprobanteDTO;
import com.example.dto.TotalCarrito;
import com.example.entity.Carrito;
import com.example.entity.CarritoItem;
import com.example.entity.Comprobantes;


import java.util.List;
import java.util.Optional;

public interface CarritoService {
    void agregarItemAlCarrito(CarritoItemDTO carritoItem);
//    void agregarServicioAlCarrito(CarritoServicioDTO carritoItem);

    void eliminarItemDelCarrito(Long itemId);
    void vaciarCarrito();

    List<CarritoItem> obtenerItemsDelCarrito();

//    List<Comprobantes> listarComprobantes();
    TotalCarrito calcularTotalConIgv();

    Carrito obtenerCarritoActual();

    Long  finalizarCompra(DatosComprobanteDTO datosComprobanteDTO);

//    Optional<Comprobantes> obtenerComprobantePorId(Long id);
    void actualizarCantidadItemCarrito(Long itemId, int nuevaCantidad);
    Carrito crearNuevoCarrito(); // Método para crear un nuevo carrito
}
