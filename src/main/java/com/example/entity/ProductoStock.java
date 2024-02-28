package com.example.entity;

import com.example.entity.detalles.DetalleEntrada;
import com.example.entity.detalles.DetallejsonProducto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "productostock")
public class ProductoStock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idstock;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Productos producto;

    @ManyToOne
    @JoinColumn(name = "almacen_id")
    private Almacenes almacenes;

    @Column
    private int stock;

    @Column
    private Double precioCompra;


    public String getCodigobarra(){
        return this.producto!=null? this.producto.getCodigo_barra():"-------";
    }


}
