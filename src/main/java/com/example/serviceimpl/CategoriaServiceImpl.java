package com.example.serviceimpl;


import com.example.entity.Categoria;
import com.example.repository.CategoriaRepository;
import com.example.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;


    @Override
    @Transactional
    public Categoria insert(Categoria categoria) {
        repository.save(categoria);
        return categoria;
    }

    @Override
    @Transactional
    public void update(Categoria categoria) {
        repository.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Long categoriaId) {
        repository.deleteById(categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria findById(Long categoriaId) {
        return repository.findById(categoriaId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByNombre(String nombre) {
        return repository.countByNombre(nombre);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<Categoria> findAllDesactivadas() {
        return repository.findByActivo(false);
    }

    @Override
    public List<Categoria> findAllActivos() {
        return repository.findByActivo(true);
    }


}
