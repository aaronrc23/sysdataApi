package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProveedoresDto {

    private Long id_proveedores;

    private String nombre_prov;

    private Integer numruc;

    private String direccion;

    private String telefono;

    private String email;

    private boolean estadoproveedor;

}
