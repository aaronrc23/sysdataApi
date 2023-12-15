package com.example.entity.detalles;

import com.example.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "detalle_factura")
public class DetalleFactura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detallefac;

    @Column
    private int cantidad;

    @Column
    private double importe;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura facturas;


    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Productos producto;

    @ManyToOne
    @JoinColumn(name = "undmedida_id")
    private UnidadMedida unidadmedida;


    public Double getValorunidad() {
        return this.producto != null ? this.producto.getPrecio_venta() : null;
    }

    public String getUnidad() {
        return this.producto != null ? this.producto.getUnidadDeMedida().getAbreviacion() : "-------";
    }



}
