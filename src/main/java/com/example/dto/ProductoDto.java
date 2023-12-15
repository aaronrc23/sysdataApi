package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDto {

    private Integer idproducto;
    private String nombre;
    private String codigo_barra;
    private Integer cantidad;

}
