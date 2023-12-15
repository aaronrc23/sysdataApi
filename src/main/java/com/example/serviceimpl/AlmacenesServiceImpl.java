package com.example.serviceimpl;



import com.example.entity.Almacenes;
import com.example.reportes.AlmacenReportes;
import com.example.repository.AlmacenRepository;
import com.example.service.AlmacenesService;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.Collection;


@Service
public class AlmacenesServiceImpl implements AlmacenesService {

    @Autowired
    private AlmacenRepository repository;


    @Autowired
    private AlmacenReportes almacenReportes;


    @Override
    public Almacenes insert(Almacenes almacenes) {
        repository.save(almacenes);
        return almacenes;
    }

    @Override
    public void update(Almacenes almacenes) {
        repository.save(almacenes);
    }

    @Override
    public void delete(Long alamcenId) {
        repository.deleteById(alamcenId);
    }

    @Override
    public Almacenes findById(Long alamcenId) {
        return repository.findById(alamcenId).orElse(null);
    }

    @Override
    @Transactional(readOnly=true)
    public Collection<Almacenes> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Long countByNombre(String numeroAlmacen) {
        return repository.countByNombre(numeroAlmacen);
    }


    @Override
    public byte[] exportPdf() throws JRException, FileNotFoundException {
        return almacenReportes.exportToPdf(repository.findAll());
    }

    @Override
    public byte[] exportXls() throws JRException, FileNotFoundException {
        return almacenReportes.exportToXls(repository.findAll());
    }
}
