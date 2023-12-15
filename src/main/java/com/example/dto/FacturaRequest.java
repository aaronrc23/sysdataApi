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
public class FacturaRequest {

    private Date fechavencimiento;
    private LocalDate fechaemision;

    private List<ProductoDto> productos;
    private String numeroserie;
    private Long unidadId;
    private Long empresaId;
    private String Moneda;
    private boolean activo;


}
