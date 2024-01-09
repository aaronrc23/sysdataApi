package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleMovientoDto {
    private Long idDetalleMovimiento;
    private Integer idProducto;
    private int cantidad;
    private String nombre;
    private String codigo_barra;
}
