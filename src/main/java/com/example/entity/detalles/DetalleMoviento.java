package com.example.entity.detalles;

import com.example.entity.Almacenes;
import com.example.entity.MovientoAlmacenes;
import com.example.entity.Productos;
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
@Table(name = "detalle_mov")
public class DetalleMoviento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dtmovientoId;

    @ManyToOne
    @JoinColumn(name = "movientos_id")
    private MovientoAlmacenes movientoalmacenes;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Productos producto;

    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "almacenOrigen_id")
    private Almacenes almacenOrigen;

    @ManyToOne
    @JoinColumn(name = "almacenDestino_id")
    private Almacenes almacenDestino;

}
