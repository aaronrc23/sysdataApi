package com.example.entity;


import com.example.entity.detalles.DetalleMoviento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "almacenes")
public class Almacenes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombreAlmacen;

//    @Column(name = "numero_almacen", unique = true, nullable = false)
//    private String numeroAlmacen;

    @Column
    private String direccion;

    @OneToMany(mappedBy = "almacenOrigen")
    @JsonIgnore
    private List<DetalleMoviento> almacenOrigen= new ArrayList<>();

    @OneToMany(mappedBy = "almacenDestino")
    @JsonIgnore
    private List<DetalleMoviento> almacenDestino = new ArrayList<>();


    @Column
    private boolean activo;



    @Override
    public String toString() {
        return "Almacenes{" +
                "id=" + id +
                ", nombreAlmacen='" + nombreAlmacen + '\'' +
//                ", numeroAlmacen='" + numeroAlmacen + '\'' +
                ", direccion='" + direccion + '\'' +
                ", activo=" + activo +
                '}';
    }
}
