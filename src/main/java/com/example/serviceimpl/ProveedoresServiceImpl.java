package com.example.serviceimpl;

import com.example.entity.Proveedores;
import com.example.repository.ProveedoresRepository;
import com.example.service.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedoresServiceImpl implements ProveedoresService {

    @Autowired
    private ProveedoresRepository repository;

    @Override
    @Transactional
    public Proveedores insert(Proveedores proveedores) {
        repository.save(proveedores);
        return proveedores;
    }

    @Override
    @Transactional
    public void update(Proveedores proveedores) {
        repository.save(proveedores);
    }

    @Override
    @Transactional
    public void delete(Long proveedores_Id) {
        repository.deleteById(proveedores_Id);
    }

    @Override
    @Transactional
    public Proveedores findById(Long proveedores_Id) {
        return repository.findById(proveedores_Id).orElse(null);
    }

    @Override
    @Transactional
    public List<Proveedores> findAll() {
        return repository.findAll();
    }

    @Override
    public Long countByNombre(String numruc) {
        return repository.countByNombre(numruc);
    }


}
