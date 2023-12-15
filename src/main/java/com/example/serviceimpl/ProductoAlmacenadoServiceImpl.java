package com.example.serviceimpl;


import com.example.entity.Almacenes;
import com.example.entity.ProductoAlmacen;
import com.example.entity.Productos;
import com.example.repository.ProductoAlmacenRepository;
import com.example.repository.ProductoRepository;
import com.example.service.ProductoAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoAlmacenadoServiceImpl implements ProductoAlmacenService {


    @Autowired
    private ProductoAlmacenRepository repository;


    @Override
    public ProductoAlmacen insert(ProductoAlmacen prodAlmacenado) {
        repository.save(prodAlmacenado);
        return prodAlmacenado;
    }

    @Override
    public void update(ProductoAlmacen prodAlmacenado) {
        repository.save(prodAlmacenado);
    }

    @Override
    public void delete(Integer paId) {
        repository.deleteById(paId);
    }

    @Override
    public ProductoAlmacen findById(Integer paId) {
        return repository.findById(paId).orElse(null);
    }


    @Override
    public List<ProductoAlmacen> findAll() {
        return repository.findAll();
    }

//    @Override
//    public List<ProductoAlmacen> getProductosByAlmacen(Almacenes almacen) {
//        return repository.findByAlmacen(almacen);
//    }
//
//    @Override
//    public int getStockActualByProductoAndAlmacen(Productos producto, Almacenes almacen) {
//        return repository.findStockActualByProductoAndAlmacen(producto, almacen);
//    }
//
//    @Override
//    public void actualizarStockActual(Productos producto, Almacenes almacen, int nuevoStock) {
//        repository.actualizarStockActual(producto, almacen, nuevoStock);
//    }


}
