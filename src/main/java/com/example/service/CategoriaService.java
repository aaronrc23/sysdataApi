package com.example.service;

import com.example.entity.Categoria;

import java.util.List;

public interface CategoriaService {

    public abstract Categoria insert(Categoria categoria);
    public abstract void update(Categoria categoria);
    public abstract void delete(Long categoriaId);
    public abstract Categoria findById(Long categoriaId);
    public abstract List<Categoria> findAll();

    Long countByNombre(String nombre);
}
