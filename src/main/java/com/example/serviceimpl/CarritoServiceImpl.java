package com.example.serviceimpl;

import com.example.dto.CarritoItemDTO;
import com.example.dto.ClienteDto;
import com.example.dto.DatosComprobanteDTO;
import com.example.dto.TotalCarrito;
import com.example.entity.*;
import com.example.repository.*;
import com.example.service.CarritoService;

import com.example.service.ComprobanteService;
import com.example.service.ProductoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CarritoServiceImpl implements CarritoService {



    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoStockRepository productoStockRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private ComprobantesRepository comprobantesRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComprobantesDetalleRepository comprobantesDetalleRepository;






    @Override
    @Transactional
    public Carrito obtenerCarritoActual() {
        Optional<Carrito> carritoEnProgreso = carritoRepository.findByEstado(EstadoCarrito.EN_PROGRESO);
        return carritoEnProgreso.orElseGet(this::crearNuevoCarrito);
    }

    @Override
    @Transactional
    public  Long  finalizarCompra(DatosComprobanteDTO datosComprobanteDTO) {
        Carrito carrito = obtenerCarritoActual();

        // Verificar si hay elementos en el carrito
        if (carrito == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("No hay productos en el carrito para finalizar la compra");
        }

        // Obtener el cliente del DTO
        Cliente clienteDto = datosComprobanteDTO.getCliente();

        // Buscar un cliente existente con el mismo DNI en la base de datos
        Optional<Cliente> clienteExistente = clienteRepository.findByDni(clienteDto.getDni());

        // Crear un nuevo cliente o usar el existente si se encuentra en la base de datos
        Cliente cliente;
        if (clienteExistente.isPresent()) {
            cliente = clienteExistente.get();
        } else {
            // Crear un nuevo cliente y asignar los datos del DTO
            cliente = new Cliente();
            cliente.setNombreCompleto(clienteDto.getNombreCompleto());
            cliente.setDni(clienteDto.getDni());
            // Guardar el cliente en la base de datos
            cliente = clienteRepository.save(cliente);
        }
        // Crear el comprobante
        Comprobantes comprobante = new Comprobantes();
        comprobante.setCliente(cliente);
        comprobante.setEstado(true);

        // Establecer la fecha y hora de registro
        comprobante.setFechaRegistro(new Date());

        // Obtener la fecha y hora actual de Perú
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));
        comprobante.setHoraCreacion(Date.from(now.atZone(ZoneId.of("America/Lima")).toInstant()));

        // Obtener el total del carrito
        TotalCarrito totalCarrito = calcularTotalConIgv();
        comprobante.setPrecioTotal(totalCarrito.getTotal());
        comprobante.setCantidadTotal(totalCarrito.getCantidadtotal());

        // Guardar el comprobante en la base de datos
        comprobante = comprobantesRepository.save(comprobante);

//        Comprobantes comprobante = new Comprobantes();
//        comprobante.setCliente(cliente); // Asignar el cliente al comprobante
//        comprobante = comprobantesRepository.save(comprobante); // Guardar el comprobante en la base de datos
        // Capturar el ID del comprobante
        Long comprobanteId = comprobante.getId();


        // Agregar los detalles del comprobante desde el carrito
        List<ComprobanteDetalle> detalles = new ArrayList<>();
        for (CarritoItem carritoItem : carrito.getItems()) {
            ComprobanteDetalle detalle = new ComprobanteDetalle();
            detalle.setComprobante(comprobante);
            detalle.setProducto(carritoItem.getProducto());
            detalle.setCantidad(carritoItem.getCantidad());
            detalles.add(comprobantesDetalleRepository.save(detalle));
        }

        // Establecer la lista de detalles en el comprobante
        comprobante.setDetalles(detalles);

        // Actualizar el estado del carrito
        carrito.setEstado(EstadoCarrito.FINALIZADO);
        carritoRepository.save(carrito);

        return comprobanteId;
    }




    @Override
    @Transactional
    public void agregarItemAlCarrito(CarritoItemDTO carritoItemDTO) {
        // Obtener el carrito actual en curso
        Carrito carrito = obtenerCarritoActual();

        // Obtener el producto seleccionado
        Productos producto = carritoItemDTO.getProducto();

        // Verificar si el producto ya está en el carrito
        Optional<CarritoItem> optionalCarritoItem = carrito.getItems().stream()
                .filter(item -> item.getProducto().getIdproducto().equals(producto.getIdproducto()))
                .findFirst();

        if (optionalCarritoItem.isPresent()) {
            // El producto ya está en el carrito, actualiza la cantidad
            CarritoItem existingItem = optionalCarritoItem.get();
            int nuevaCantidad = existingItem.getCantidad() + carritoItemDTO.getCantidad();
            existingItem.setCantidad(nuevaCantidad);
            carritoItemRepository.save(existingItem);
        } else {
            // El producto no está en el carrito, procede con la lógica normal
            // Obtener el ProductoStock asociado al producto y almacén (puedes adaptar esta lógica según tu modelo de datos)
            ProductoStock productoStock = productoStockRepository.findByProductoAndAlmacenes(producto, carritoItemDTO.getAlmacen());

            // Verificar si hay suficiente stock para agregar al carrito
            if (productoStock != null && productoStock.getStock() >= carritoItemDTO.getCantidad()) {
                // Reducir el stock del ProductoStock
                productoStock.setStock(productoStock.getStock() - carritoItemDTO.getCantidad());
                productoStockRepository.save(productoStock);

                // Crear un nuevo CarritoItem
                CarritoItem carritoItem = new CarritoItem();
                carritoItem.setProducto(producto);
                carritoItem.setCarrito(carrito);
                carritoItem.setCantidad(carritoItemDTO.getCantidad());

                // Agregar el CarritoItem al carrito
                carrito.getItems().add(carritoItem);

                // Actualizar el carrito en la base de datos
                carritoRepository.save(carrito);
            } else {
                // Manejar la situación donde no hay suficiente stock
                throw new RuntimeException("No hay suficiente stock para agregar al carrito");
            }
        }
    }

    @Override
    public void eliminarItemDelCarrito(Long itemId) {
        CarritoItem carritoItem = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item del carrito no encontrado"));

        // Verificar si el carrito tiene un cliente asociado
        Carrito carrito = carritoItem.getCarrito();
        if (carrito.getCliente() == null) {
            // Asegúrate de manejar la actualización del stock u otras lógicas necesarias aquí
            carritoItemRepository.delete(carritoItem);
        } else {
            throw new RuntimeException("No se puede eliminar un item del carrito si el carrito está asociado a un cliente");
        }
    }

    @Override
    public void vaciarCarrito() {
        Carrito carrito = obtenerCarritoActual();

        // Verificar si el carrito tiene un cliente asociado
        if (carrito.getCliente() == null) {
            // Asegúrate de manejar la actualización del stock u otras lógicas necesarias aquí
            carrito.getItems().clear();
            carritoRepository.save(carrito);
        } else {
            throw new RuntimeException("No se puede vaciar el carrito si está asociado a un cliente");
        }
    }


    @Override
    @Transactional(readOnly = false)
    public List<CarritoItem> obtenerItemsDelCarrito() {
        Carrito carrito = obtenerCarritoActual();
        if (carrito.getCliente() == null) {
            return carrito.getItems();
        } else {
            throw new RuntimeException("No se pueden obtener los ítems del carrito si está asociado a un cliente");
        }
    }


    @Override
    public TotalCarrito calcularTotalConIgv() {
        Carrito carrito = obtenerCarritoActual();
        if (carrito.getCliente() == null) {
            List<CarritoItem> items = carrito.getItems();

            double subtotal = items.stream()
                    .mapToDouble(item -> item.getProducto().getPrecio_venta() * item.getCantidad())
                    .sum();

            double igv = subtotal * 0.18; // 18% de IGV
            double total = subtotal + igv;

            int cantidadTotal = items.stream()
                    .mapToInt(CarritoItem::getCantidad)
                    .sum();

            TotalCarrito totalCarrito = new TotalCarrito();
            totalCarrito.setSubtotal(subtotal);
            totalCarrito.setIgv(igv);
            totalCarrito.setTotal(total);
            totalCarrito.setCantidadtotal(cantidadTotal);

            return totalCarrito;
        } else {
            throw new RuntimeException("No se puede calcular el total del carrito si está asociado a un cliente");
        }
    }

    @Override
    @Transactional
    public void actualizarCantidadItemCarrito(Long itemId, int nuevaCantidad) {
        Optional<CarritoItem> optionalCarritoItem = carritoItemRepository.findById(itemId);

        if (optionalCarritoItem.isPresent()) {
            CarritoItem carritoItem = optionalCarritoItem.get();
            Carrito carrito = carritoItem.getCarrito();
            if (carrito.getCliente() == null) {
                carritoItem.setCantidad(nuevaCantidad);
                carritoItemRepository.save(carritoItem);
            } else {
                throw new RuntimeException("No se puede actualizar la cantidad de un item del carrito si está asociado a un cliente");
            }
        } else {
            throw new NoSuchElementException("No se encontró el item del carrito con ID: " + itemId);
        }
    }





    @Override
    @Transactional
    public Carrito crearNuevoCarrito() {
        Carrito carrito = new Carrito();
        carrito.setEstado(EstadoCarrito.EN_PROGRESO);
        return carritoRepository.save(carrito);
    }




}
