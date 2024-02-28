package com.example.dto;

import com.example.entity.Almacenes;
import com.example.entity.Productos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarritoItemDTO {
    private Productos producto;
    private int cantidad;
    private Almacenes almacen;

}
