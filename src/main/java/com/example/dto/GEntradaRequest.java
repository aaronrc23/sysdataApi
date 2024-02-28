package com.example.dto;

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
public class GEntradaRequest {
//    private LocalDate fecha;
    private String tipoComprobante;
    private List<ProductoDto> productos;
    private String numserie;
    private Long proveedorId;
    private Long AlmacenId;
    private Integer cantidadtotal;
    private Double preciototal;


}
