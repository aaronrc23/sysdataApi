package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalCarrito {
    private double subtotal;
    private double igv;
    private double total;
    private int cantidadtotal;
}
