package com.example.serviceimpl;

import com.example.entity.detalles.DetalleEntrada;
import com.example.reportes.Ent_report;
import com.example.repository.detalles.DetalleEntradaRepository;
import com.example.service.DetalleEntradaService;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DetalleEntradaServiceImpl implements DetalleEntradaService {

    DetalleEntradaRepository repository;
    Ent_report entradaRepo;

    public DetalleEntradaServiceImpl(DetalleEntradaRepository repository, Ent_report entradaRepo) {
        this.repository = repository;
        this.entradaRepo = entradaRepo;
    }

    @Override
    public DetalleEntrada insert(DetalleEntrada detalleEntrada) {
        repository.save(detalleEntrada);
        return detalleEntrada;
    }

    @Override
    public void update(DetalleEntrada detalleEntrada) {
        repository.save(detalleEntrada);
    }

    @Override
    public void delete(Long detalleentradaId) {
        repository.deleteById(detalleentradaId);
    }

    @Override
    public DetalleEntrada findById(Long detalleentradaId) {
        return repository.findById(detalleentradaId).orElse(null);
    }

    @Override
    public List<DetalleEntrada> findAll() {
        return repository.findAll();
    }
    @Override
    public List<DetalleEntrada> listarDetallesPorEntrada(Long gentradasId) {
        return repository.findByEntradas_GentradasId(gentradasId);
    }



    @Override
    public DetalleEntrada findProductById(Long detalleId) {
        return null;
    }

    @Override
    public byte[] exportPdfForProduct(DetalleEntrada detalleEntrada) throws JRException, FileNotFoundException {
        return new byte[0];
    }



    @Override
    public List<DetalleEntrada> findByEntradas_GentradasId(Long entradaId) {
        return repository.findByEntradas_GentradasId(entradaId);
    }

    //Metodos con Reportes con PDF Y EXCEL
    @Override
    public byte[] exportPdfById(Long entradaId) throws JRException, FileNotFoundException {
        DetalleEntrada detalle = findById(entradaId);
        if (detalle == null) {
            // Manejar el caso en el que no se encuentra el detalle con el ID dado
            throw new EntityNotFoundException("DetalleEntrada no encontrado con ID: " + entradaId);
        }


        // Exportar el informe a PDF utilizando los parámetros
        return entradaRepo.exportToPdf(List.of(detalle));
    }

    @Override
    public byte[] exportXls() throws JRException, FileNotFoundException {
        return entradaRepo.exportToPdf(repository.findAll());
    }


}