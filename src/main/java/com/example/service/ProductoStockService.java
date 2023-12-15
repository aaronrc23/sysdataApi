package com.example.service;

import com.example.entity.Almacenes;
import com.example.entity.ProductoStock;

import java.util.Collection;

public interface ProductoStockService {
    ProductoStock insert(ProductoStock productoStock);

    void update(ProductoStock productoStock);
    void delete(Long productoStockId);
    ProductoStock findById(Long productoStockId);
    Collection<ProductoStock> findAll();
}
