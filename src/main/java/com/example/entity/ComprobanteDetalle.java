package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comprobante_detalle")
public class ComprobanteDetalle  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "comprobantes_id", nullable = false)
    @JsonIgnore
    private Comprobantes comprobante;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Productos producto;

//    @ManyToOne
//    @JoinColumn(name = "servicio_id")
//    private Servicios servicio;


    @Column(nullable = false)
    private int cantidad;




}
