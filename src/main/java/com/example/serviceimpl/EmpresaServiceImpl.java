package com.example.serviceimpl;


import com.example.entity.Empresa;
import com.example.repository.EmpresaRepository;
import com.example.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    @Override
    public Empresa insert(Empresa empresa) {
        repository.save(empresa);
        return empresa;
    }

    @Override
    public void update(Empresa empresa) {
        repository.save(empresa);
    }

    @Override
    public void delete(Long empresaId) {
        repository.deleteById(empresaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Empresa findById(Long empresaId) {
        return repository.findById(empresaId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Empresa> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByNombre(Integer ruc) {
        return repository.countByNombre(ruc);
    }


}
