package com.example.serviceimpl;

import com.example.entity.ProductoStock;
import com.example.repository.ProductoStockRepository;
import com.example.service.ProductoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductoStockServiceImpl  implements ProductoStockService {

    @Autowired
    ProductoStockRepository repository;

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
    public Collection<ProductoStock> findAll() {
        return repository.findAll();
    }
}
