package com.example.entity;

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
@Table(name = "proveedores")
public class    Proveedores implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proveedores;
    @Column
    private String nombre_prov;

    @Column
    private String nombre_emp;

    @Column
    private String tipodocumento;
    @Column
    private String razon_social;

    @Column
    private String departamento;

    @Column
    private String distrito;

    @Column
    private String provincia;

    @Column(unique = true, nullable = false)
    private String numruc;
    @Column
    private String direccion;
    @Column
    private String telefono;
    @Column
    private String email;
    @Column
    private boolean activo;



}
