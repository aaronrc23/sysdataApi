package com.example.serviceimpl;

import com.example.dto.TotalCarrito;
import com.example.entity.ComprobanteDetalle;
import com.example.entity.Comprobantes;
import com.example.repository.ComprobantesRepository;
import com.example.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteServiceimpl  implements ComprobanteService {
    @Autowired
    ComprobantesRepository comprobanteRepository;


    @Override
    @Transactional
    public TotalCarrito calcularTotalConIgvPorComprobante(Long idComprobante) {
        Comprobantes comprobante = comprobanteRepository.findById(idComprobante)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado con ID: " + idComprobante));

        List<ComprobanteDetalle> detalles = comprobante.getDetalles();

        double subtotal = detalles.stream()
                .mapToDouble(detalle -> detalle.getProducto().getPrecio_venta() * detalle.getCantidad())
                .sum();

        double igv = subtotal * 0.18; // 18% de IGV
        double total = subtotal + igv;

        int cantidadTotal = detalles.stream()
                .mapToInt(ComprobanteDetalle::getCantidad)
                .sum();

        TotalCarrito totalComprobante = new TotalCarrito();
        totalComprobante.setSubtotal(subtotal);
        totalComprobante.setIgv(igv);
        totalComprobante.setTotal(total);
        totalComprobante.setCantidadtotal(cantidadTotal);

        return totalComprobante;
    }

    @Override
    @Transactional
    public List<Comprobantes> listarComprobantes() {
        return comprobanteRepository.findAllWithDetalles();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Comprobantes> obtenerComprobantePorId(Long id) {
        return comprobanteRepository.findById(id);
    }

}
