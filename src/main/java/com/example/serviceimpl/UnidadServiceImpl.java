package com.example.serviceimpl;

import com.example.entity.UnidadMedida;
import com.example.repository.UnidadMedidaRepository;
import com.example.service.UnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UnidadServiceImpl implements UnidadMedidaService {

    @Autowired
    private UnidadMedidaRepository repository;

    @Override
    @Transactional
    public UnidadMedida insert(UnidadMedida und) {
        repository.save(und);
        return und;
    }

    @Override
    @Transactional
    public void update(UnidadMedida und) {
        repository.save(und);
    }

    @Override
    @Transactional
    public void delete(Long umd_id) {
        repository.deleteById(umd_id);
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadMedida findById(Long umd_id) {
        return repository.findById(umd_id).orElse(null);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<UnidadMedida> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UnidadMedida> findAllDesactivadas() {
        return repository.findByActivo(false);
    }

    @Override
    public List<UnidadMedida> findAllActivos() {
        return repository.findByActivo(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByNombre(String nombre) {
        return repository.countByNombre(nombre);
    }




}
