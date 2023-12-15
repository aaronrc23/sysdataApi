package com.example.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturaDto {

    private Date fechavencimiento;
    private LocalDate fechaemision;
    private List<ProductoDto> productos;
    private String numeroserie;
    private Long empresaId;
    private String Moneda;
    private boolean activo;
    private Long unidadId;

    public FacturaDto(FacturaRequest facturaRequest) {
        this.fechavencimiento = facturaRequest.getFechavencimiento();
        this.fechaemision = facturaRequest.getFechaemision();
        this.productos = facturaRequest.getProductos();
        this.numeroserie = facturaRequest.getNumeroserie();
        this.empresaId = facturaRequest.getEmpresaId();
        this.unidadId = facturaRequest.getUnidadId();
        this.Moneda = facturaRequest.getMoneda();
        this.activo = facturaRequest.isActivo();
    }
}
