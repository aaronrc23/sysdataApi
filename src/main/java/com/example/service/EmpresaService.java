package com.example.service;

import com.example.entity.Empresa;

import java.util.Collection;

public interface EmpresaService {

    Empresa insert(Empresa empresa);
    void update(Empresa empresa);

    void delete(Long empresaId);

    Empresa findById(Long empresaId);

    Collection<Empresa>findAll();

    Long countByNombre(Integer ruc);


}
