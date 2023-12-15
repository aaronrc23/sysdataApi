package com.example.service;

import com.example.entity.Almacenes;
import com.example.entity.Categoria;
import com.example.entity.ProductoAlmacen;
import com.example.entity.Productos;

import java.util.List;

public interface ProductoAlmacenService {


    public abstract ProductoAlmacen insert(ProductoAlmacen prodAlmacenado);
    public abstract void update(ProductoAlmacen prodAlmacenado);
    public abstract void delete(Integer paId);
    public abstract ProductoAlmacen findById(Integer paId);
    public abstract List<ProductoAlmacen> findAll();



}
