package com.example.entity;
import com.example.entity.detalles.DetalleMoviento;
import com.example.entity.detalles.DetallejsonProducto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "moviento_almacenes")
public class MovientoAlmacenes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moviento")
    private Integer idmoviento;

    @Column(name = "fechaemision", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaemision;
    @Column
    private Date fechainciotraslado;
    @Column
    private String dir_partida;
    @Column
    private String dir_llegada;
    @Column
    private String motivotraslado;
    @ManyToOne
    @JoinColumn(name = "almacen_origen_id")
    private Almacenes almacenOrigen;

    @ManyToOne
    @JoinColumn(name = "almacen_destino_id")
    private Almacenes almacenDestino;

    // Otros métodos y propiedades

    public Almacenes getAlmacenOrigen() {
        return this.almacenOrigen;
    }

    public Almacenes getAlmacenDestino() {
        return this.almacenDestino;
    }


    @OneToMany(mappedBy = "movientoalmacenes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleMoviento> detalles = new ArrayList<>();




    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));
        this.fechaemision = now;
    }


    public List<DetallejsonProducto> getDetallesAsJson() {
        List<DetallejsonProducto> detallesJson = new ArrayList<>();
        for (DetalleMoviento detalle : detalles) {
            DetallejsonProducto detalleJson = new DetallejsonProducto();
            detalleJson.setIdproducto(detalle.getProducto().getIdproducto());
            detalleJson.setNombre(detalle.getProducto().getNombre());  // Utiliza getNombre de Productos
            detalleJson.setCantidad(detalle.getCantidad());
            detalleJson.setCodigobarra(detalle.getProducto().getCodigo_barra());
            detalleJson.setAbreviacion(detalle.getProducto().getUnidadDeMedida().getAbreviacion());
            detallesJson.add(detalleJson);
        }
        return detallesJson;
    }
}
