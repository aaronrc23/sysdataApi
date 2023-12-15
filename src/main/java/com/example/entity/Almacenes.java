package com.example.entity;


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
@Table(name = "almacenes")
public class Almacenes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombreAlmacen;

    @Column(name = "numero_almacen", unique = true, nullable = false)
    private String numeroAlmacen;

    @Column
    private String direccion;

    @Column
    private boolean activo;



    @Override
    public String toString() {
        return "Almacenes{" +
                "id=" + id +
                ", nombreAlmacen='" + nombreAlmacen + '\'' +
                ", numeroAlmacen='" + numeroAlmacen + '\'' +
                ", direccion='" + direccion + '\'' +
                ", activo=" + activo +
                '}';
    }




}
