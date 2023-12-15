package com.example.service;

import com.example.entity.Proveedores ;
import com.example.entity.Proveedores;

import java.util.List;

public interface ProveedoresService {

    public abstract Proveedores insert(Proveedores  proveedores );
    public abstract void update(Proveedores  proveedores );
    public abstract void delete(Long proveedores_Id);
    public abstract Proveedores  findById(Long proveedores_Id);
    public abstract List<Proveedores> findAll();

    Long countByNombre(String numruc);
}
