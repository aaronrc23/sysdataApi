package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "productos")
public class Productos  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproducto;
    @Column(unique = true, nullable = false)
    private String codigo_barra;
    @Column(unique = true, nullable = false)
    private String nombre;

    @Column
    private double precio_venta;

//    @Column
//    private double precio_compra;

    @Column
    private double precio_pormayor;

    @Column
    private int stock;
    @Column
    private String descripcion;
    @Column(length = 1048576) // Tamaño personalizado para la columna imagen (en este caso, 1 MB)
    private String imagen;

    @Column
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;


    @ManyToOne
    @JoinColumn(name = "unidad_id")
    private UnidadMedida unidadDeMedida;







//    @ManyToOne
//    @JoinColumn(name = "marca_id")
//    private Marcas marcas;




}
