package com.example.serviceimpl;


import com.example.entity.Categoria;
import com.example.entity.Productos;
import com.example.repository.ProductoRepository;
import com.example.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;


    @Override
    @Transactional
    public void insert(Productos producto) {
        repository.save(producto);
    }

    @Override
    @Transactional
    public void update(Productos producto) {
        repository.save(producto);
    }

    @Override
    @Transactional
    public void delete(Integer productoId) {
        repository.deleteById(productoId);
    }

    @Override
    @Transactional
    public Productos findById(Integer productoId) {
        return repository.findById(productoId).orElse(null);

    }

    @Override
    @Transactional
    public List<Productos> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public boolean isExistCodigoBarra(String codigo_barra) {
        return !repository.findByCodigoBarra(codigo_barra).isEmpty();
    }

    @Override
    public List<Productos> findByActivos() {
        return repository.findByActivo(false);
    }

    @Override
    public List<Productos> findByDesactivados() {
        return repository.findByActivo(false);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public List<Productos> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }
}
