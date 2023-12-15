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
public class EntradaDto {

    private String fecha;
    private String numeroserie;
    private String proveedorNombre;
    private List<ProductoDto> productos;
}
