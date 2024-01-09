package com.example.serviceimpl;

import com.example.dto.DetalleMovientoDto;
import com.example.dto.MovimientoDTO;
import com.example.entity.Almacenes;
import com.example.entity.MovientoAlmacenes;
import com.example.entity.ProductoStock;
import com.example.entity.Productos;
import com.example.entity.detalles.DetalleMoviento;
import com.example.repository.AlmacenRepository;
import com.example.repository.MovientoAlmacenesRepository;
import com.example.repository.ProductoRepository;
import com.example.repository.ProductoStockRepository;
import com.example.repository.detalles.DetalleMovientoRepository;
import com.example.service.MovientoAlmacenesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovientoAlmacenesServiceImpl implements MovientoAlmacenesService {

    MovientoAlmacenesRepository movientoAlmacenesRepository;
    ProductoRepository productosRepository;
    AlmacenRepository almacenesRepository;
    DetalleMovientoRepository detalleMovientoRepository;
    ProductoStockRepository productoStockRepository;

    public MovientoAlmacenesServiceImpl(MovientoAlmacenesRepository movientoAlmacenesRepository, ProductoRepository productosRepository,AlmacenRepository almacenesRepository, DetalleMovientoRepository detalleMovientoRepository,ProductoStockRepository productoStockRepository) {
        this.movientoAlmacenesRepository = movientoAlmacenesRepository;
        this.productosRepository = productosRepository;
        this.almacenesRepository = almacenesRepository;
        this.detalleMovientoRepository = detalleMovientoRepository;
        this.productoStockRepository= productoStockRepository;
    }


    @Override
    public MovientoAlmacenes findById(Long idmoviento) {
        return movientoAlmacenesRepository.findById(idmoviento).orElse(null);
    }

    @Override
    public List<MovientoAlmacenes> findAll() {
        return movientoAlmacenesRepository.findAll();
    }

    @Override
    @Transactional
    public void realizarMovimiento(MovimientoDTO movimientoDTO) {
        MovientoAlmacenes movimiento = new MovientoAlmacenes();
        movimiento.setDir_partida(movimientoDTO.getDirPartida());
        movimiento.setDir_llegada(movimientoDTO.getDirLlegada());
        movimiento.setMotivotraslado(movimientoDTO.getMotivoTraslado());

        // Configura el almacén de origen
        Almacenes almacenOrigen = almacenesRepository.findById(movimientoDTO.getIdAlmacenOrigen())
                .orElseThrow(() -> new RuntimeException("Almacén de origen no encontrado"));
        movimiento.setAlmacenOrigen(almacenOrigen);

        // Configura el almacén de destino
        Almacenes almacenDestino = almacenesRepository.findById(movimientoDTO.getIdAlmacenDestino())
                .orElseThrow(() -> new RuntimeException("Almacén de destino no encontrado"));
        movimiento.setAlmacenDestino(almacenDestino);

        movientoAlmacenesRepository.save(movimiento);

        // Procesa los detalles del movimiento
        for (DetalleMovientoDto detalleDTO : movimientoDTO.getDetalles()) {
            DetalleMoviento detalle = new DetalleMoviento();
            detalle.setMovientoalmacenes(movimiento);

            // Configura el producto
            Productos producto = productosRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detalle.setProducto(producto);

            detalle.setCantidad(detalleDTO.getCantidad());

            // Configura almacén de origen y destino en detalle (puedes ajustarlo según tu modelo)
            detalle.setAlmacenOrigen(almacenOrigen);
            detalle.setAlmacenDestino(almacenDestino);

            detalleMovientoRepository.save(detalle);

            // Actualiza el stock
            actualizarStock(producto, almacenOrigen, -detalleDTO.getCantidad());
            actualizarStock(producto, almacenDestino, detalleDTO.getCantidad());
        }
    }

    private void actualizarStock(Productos producto, Almacenes almacen, int cantidad) {
        ProductoStock productoStock = productoStockRepository.findByProductoAndAlmacenes(producto, almacen);

        if (productoStock == null) {
            throw new RuntimeException("Producto no encontrado en el stock del almacén: " + producto.getNombre() +
                    " en el almacén: " + almacen.getNombreAlmacen());
        }

        int nuevoStock = productoStock.getStock() + cantidad;
        if (nuevoStock < 0) {
            throw new RuntimeException("No hay suficiente stock disponible para el producto en el almacén");
        }

        productoStock.setStock(nuevoStock);
        productoStockRepository.save(productoStock);
    }
}
