package com.example.entity.detalles;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallejsonProducto {
    private Integer idproducto;
    private String nombre;
    private int cantidad;
    private Double preciocompra;
    private String codigobarra;
    private String abreviacion;
    private String almacenOrigen;
    private String almacenDestino;



    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }
}
