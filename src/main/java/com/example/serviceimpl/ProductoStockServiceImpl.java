package com.example.serviceimpl;

import com.example.entity.ProductoStock;
import com.example.entity.detalles.DetalleEntrada;
import com.example.repository.ProductoStockRepository;
import com.example.repository.detalles.DetalleEntradaRepository;
import com.example.service.DetalleEntradaService;
import com.example.service.ProductoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProductoStockServiceImpl  implements ProductoStockService {

    @Autowired
    ProductoStockRepository repository;
    @Autowired
    private DetalleEntradaService detalleEntradaService;


    @Autowired
    DetalleEntradaRepository detalleEntradaRepository;

    public ProductoStockServiceImpl(ProductoStockRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductoStock insert(ProductoStock productoStock) {
        repository.save(productoStock);
        return productoStock;
    }

    @Override
    public void update(ProductoStock productoStock) {
        repository.save(productoStock);
    }

    @Override
    public void delete(Long productoStockId) {
        repository.deleteById(productoStockId);
    }

    @Override
    public ProductoStock findById(Long productoStockId) {
        return repository.findById(productoStockId).orElse(null);
    }


    @Override
    public ProductoStock findByProducto_Codigo_barra(String codigoBarras) {
        return repository.findByProducto_Codigo_barra(codigoBarras);
    }
    @Override
    public Collection<ProductoStock> findAll() {
        List<ProductoStock> productosStock = (List<ProductoStock>) repository.findAll();

        for (ProductoStock productoStock : productosStock) {
            // Obtener el id del producto asociado
            Integer productoId = productoStock.getProducto().getIdproducto();

            // Buscar el DetalleEntrada asociado al producto
            List<DetalleEntrada> detallesEntrada = detalleEntradaService.findByProducto_Idproducto(productoId);

            // Si hay al menos un detalleEntrada, puedes tomar el primer elemento, o iterar según sea necesario
            if (!detallesEntrada.isEmpty()) {
                DetalleEntrada detalleEntrada = detallesEntrada.get(0);
                productoStock.setPrecioCompra(detalleEntrada.getPreciocompra());
            }
        }

        return productosStock;
    }
}
