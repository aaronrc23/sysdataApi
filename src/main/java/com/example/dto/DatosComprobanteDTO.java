package com.example.dto;

import com.example.entity.CarritoItem;
import com.example.entity.Cliente;
import com.example.entity.EstadoCarrito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatosComprobanteDTO {
    private Cliente cliente;
    private List<CarritoItem> itemsDelCarrito ;
//    private List<Servicios> servicios = new ArrayList<>();
    private TotalCarrito totalCarrito;
    private EstadoCarrito estadoCarrito;



    public void setTotalCarrito(TotalCarrito totalCarrito) {
        this.totalCarrito = totalCarrito;
    }

}
