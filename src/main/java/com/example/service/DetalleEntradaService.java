package com.example.service;
import com.example.entity.detalles.DetalleEntrada;
import net.sf.jasperreports.engine.JRException;
import java.io.FileNotFoundException;
import java.util.List;

public interface DetalleEntradaService {

      DetalleEntrada insert(DetalleEntrada detalleEntrada);
     void update(DetalleEntrada detalleEntrada);
      void delete(Long detalleentradaId);
      DetalleEntrada findById(Long detalleentradaId);
     List<DetalleEntrada> findAll();

    List<DetalleEntrada> findByEntradas_GentradasId(Long entradaId);

    byte[] exportPdfById(Long entradaId) throws JRException, FileNotFoundException;
    byte[] exportXls() throws JRException, FileNotFoundException;

    List<DetalleEntrada> listarDetallesPorEntrada(Long gentradasId);

    DetalleEntrada findProductById(Long detalleId);
    byte[] exportPdfForProduct(DetalleEntrada detalleEntrada) throws JRException, FileNotFoundException;
}
