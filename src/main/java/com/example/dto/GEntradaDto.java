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
public class GEntradaDto {
    private LocalDate fecha;
    private List<ProductoDto> productos;
    private String numserie;
    private String proveedorNombre;
    private Integer cantidadtotal;
    private Double preciototal;

    public GEntradaDto(GEntradaRequest entradaRequest, String numserie, String proveedorNombre, Integer cantidadtotal,Double preciototal) {
        this.fecha = entradaRequest.getFecha();
        this.productos = entradaRequest.getProductos();
        this.numserie = entradaRequest.getNumserie();
        this.proveedorNombre = proveedorNombre;
        this.cantidadtotal = cantidadtotal;
        this.preciototal=preciototal;
    }
}
