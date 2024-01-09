package com.example.serviceimpl;

import com.example.entity.detalles.DetalleMoviento;
import com.example.repository.detalles.DetalleMovientoRepository;
import com.example.service.DetalleMovientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleMovientoServiceImpl implements DetalleMovientoService {
    private final DetalleMovientoRepository repository;

    @Autowired
    public DetalleMovientoServiceImpl(DetalleMovientoRepository repository) {
        this.repository = repository;
    }

    @Override
    public DetalleMoviento saveDetalleMoviento(DetalleMoviento detalleMoviento) {
        return repository.save(detalleMoviento);
    }



}
