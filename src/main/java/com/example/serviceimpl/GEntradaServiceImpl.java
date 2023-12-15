package com.example.serviceimpl;

import com.example.Exception.ProductoNotFoundException;
import com.example.dto.GEntradaRequest;
import com.example.dto.ProductoDto;
import com.example.entity.*;
import com.example.entity.detalles.DetalleEntrada;
import com.example.repository.*;
import com.example.repository.detalles.DetalleEntradaRepository;
import com.example.service.GuiaEntradaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GEntradaServiceImpl  implements GuiaEntradaService {

     ProductoRepository productoRepository;
     GuiasEntradasRepository guiaEntradasRepository;
     DetalleEntradaRepository detalleEntradaRepository;
     ProveedoresRepository proveedoresRepository;
     ProductoStockRepository productoStockRepository;
     AlmacenRepository almacenRepository;





    public GEntradaServiceImpl(ProductoRepository productoRepository, GuiasEntradasRepository guiaEntradasRepository, DetalleEntradaRepository detalleEntradaRepository ,ProveedoresRepository proveedoresRepository,AlmacenRepository almacenRepository,ProductoStockRepository productoStockRepository) {
        this.productoRepository = productoRepository;
        this.guiaEntradasRepository = guiaEntradasRepository;
        this.detalleEntradaRepository = detalleEntradaRepository;
        this.proveedoresRepository = proveedoresRepository;
        this.almacenRepository= almacenRepository;
        this.productoStockRepository = productoStockRepository;


    }

    @Override
    public GEntradas crearEntrada(GEntradaRequest gentradarequest) {

        GEntradas pedido = new GEntradas();
        pedido.setFecha(gentradarequest.getFecha());

        Proveedores proveedor = proveedoresRepository.findById(gentradarequest.getProveedorId())
                .orElseThrow(() -> new ProductoNotFoundException("Proveedor con ID " + gentradarequest.getProveedorId() + " no encontrado"));

        pedido.setProveedores(proveedor);


        Almacenes almacen = almacenRepository.findById(gentradarequest.getAlmacenId())
                .orElseThrow(() -> new ProductoNotFoundException("Proveedor con ID " + gentradarequest.getAlmacenId() + " no encontrado"));

        pedido.setAlmacen(almacen);


        List<DetalleEntrada> detalles = new ArrayList<>();

        for (ProductoDto productoDto : gentradarequest.getProductos()) {
            Productos productoExistente = obtenerProductoPorId(productoDto.getIdproducto());

            if (productoExistente != null) {
                DetalleEntrada detallePedido = new DetalleEntrada();
                detallePedido.setEntradas(pedido);
                detallePedido.setProducto(productoExistente);
                detallePedido.setCantidad(productoDto.getCantidad());

                detalles.add(detallePedido);
            } else {
                throw new ProductoNotFoundException("Producto con ID " + productoDto.getIdproducto() + " no encontrado");
            }
        }

        // Guardar el pedido primero
        GEntradas pedidoGuardado = guiaEntradasRepository.save(pedido);

        // Asignar el pedido a los detalles y guardar los detalles
        detalles.forEach(detalle -> detalle.setEntradas(pedidoGuardado));
        detalleEntradaRepository.saveAll(detalles);

        // Asignar los detalles al pedido guardado
        pedidoGuardado.setDetalles(detalles);

        // Verificar y actualizar el stock y el almacén después de guardar la entrada
        verificarYActualizarStockYAlmacen(pedidoGuardado);
        return pedidoGuardado;
    }

    public void actualizarStockEnProductostock(GEntradas pedidoGuardado, List<DetalleEntrada> detalles) {
        for (DetalleEntrada detalle : detalles) {
            // Obtener el producto y almacen asociados al detalle
            Productos producto = detalle.getProducto();
            Almacenes almacen = pedidoGuardado.getAlmacen();

            // Actualizar el stock utilizando el repositorio
            productoStockRepository.actualizarStock(producto.getIdproducto(), almacen.getId(), detalle.getCantidad());
        }
    }
    // Nuevo método para verificar y actualizar el stock y el almacén
    private void verificarYActualizarStockYAlmacen(GEntradas pedidoGuardado) {
        for (DetalleEntrada detalle : pedidoGuardado.getDetalles()) {
            // Obtener el producto y almacen asociados al detalle
            Productos producto = detalle.getProducto();
            Almacenes almacen = pedidoGuardado.getAlmacen();

            // Verificar si existe el registro en ProductoStock
            ProductoStock productoStock = productoStockRepository.findByProductoAndAlmacenes(producto, almacen);

            // Si no existe, crear un nuevo registro en ProductoStock
            if (productoStock == null) {
                productoStock = new ProductoStock();
                productoStock.setProducto(producto);
                productoStock.setAlmacenes(almacen);
                productoStock.setStock(0); // Puedes establecer el stock inicial si es necesario
                productoStockRepository.save(productoStock);
            }

            // Actualizar el stock
            productoStockRepository.actualizarStock(producto.getIdproducto(), almacen.getId(), detalle.getCantidad());
        }
    }



    @Override
    public List<GEntradas> listarEntradas() {
        return guiaEntradasRepository.findAll();
    }

    @Override
    public List<ProductoDto> listarProductosEnGuiaEntradas(Long gentradasId) {
        return detalleEntradaRepository.findProductosEnGuiaEntradas(gentradasId);
    }


    @Override
    public Optional<Productos> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public GEntradas findById(Long gentradasId) {
        return guiaEntradasRepository.findById(gentradasId).orElse(null);
    }


    private Productos obtenerProductoPorId(Integer idProducto) {
        Optional<Productos> productoOptional = productoRepository.findById(idProducto);
        return productoOptional.orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + idProducto + " no encontrado"));
    }

    @Override
    public GEntradas obtenerEntradaPorId(Long gentradaId) {
        // Implementa la lógica para obtener la entrada por ID
        // Puedes usar el repositorio, por ejemplo:
        return guiaEntradasRepository.findById(gentradaId).orElse(null);
    }




}
