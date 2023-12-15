package com.example.service;

import com.example.entity.detalles.DetalleEntrada;
import com.example.entity.detalles.DetalleFactura;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface DetalleFacturaService {
    DetalleFactura insert(DetalleFactura detalleFactura);
    void update(DetalleFactura detalleFactura);
    void delete(Long detallefacturaId);
    DetalleFactura findById(Long detallefacturaId);
    List<DetalleFactura> findAll();

    List<DetalleFactura> findByFacturas_GfacturasId(Long facturaId);

    byte[] exportPdfById(Long facturaId) throws JRException, FileNotFoundException;
    byte[] exportXls() throws JRException, FileNotFoundException;

    List<DetalleFactura> listarDetallesPorEntrada(Long facturaId);

    DetalleFactura findProductById(Long detalleId);
    byte[] exportPdfForProduct(DetalleFactura detalleFactura) throws JRException, FileNotFoundException;
}
