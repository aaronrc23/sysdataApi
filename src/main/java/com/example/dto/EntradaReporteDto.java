package com.example.dto;

import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntradaReporteDto {

    private LocalDate fecha;
    private List<ProductoDto> productos;
    private String numserie;
    private String proveedorNombre;






}
