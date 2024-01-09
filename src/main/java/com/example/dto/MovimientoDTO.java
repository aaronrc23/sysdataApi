package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoDTO {
    private Long idMovimiento;
    private String dirPartida;
    private String dirLlegada;
    private String motivoTraslado;
    private Long idAlmacenOrigen;
    private Long idAlmacenDestino;
    private List<DetalleMovientoDto> detalles;
}
