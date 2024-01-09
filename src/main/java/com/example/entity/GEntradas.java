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
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Entity
        @Table(name = "entradas")
        public class GEntradas implements Serializable {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long gentradasId;
            @Column
            private LocalDate fecha;

            @Column
            private String numeroserie;

            @ManyToOne
            @JoinColumn(name = "almacen_id")
            private Almacenes almacen;

            @Column
            private Integer cantidadtotal;

            @Column
            private Double preciototal;

            @OneToMany(mappedBy = "entradas", cascade = CascadeType.ALL)
            @JsonIgnore
            private List<DetalleEntrada> detalles = new ArrayList<>();


            @ManyToOne
            @JoinColumn(name = "proveedor_id")
            private Proveedores proveedores;

            @PrePersist
            public void prePersist() {
                this.fecha = LocalDate.now();
            }
            @PostPersist
            public void postPersist() {
                this.numeroserie = generarNumeroSerie();
            }


        public List<DetallejsonProducto> getDetallesAsJson() {
            List<DetallejsonProducto> detallesJson = new ArrayList<>();
            for (DetalleEntrada detalle : detalles) {
                DetallejsonProducto detalleJson = new DetallejsonProducto();
                detalleJson.setIdproducto(detalle.getProducto().getIdproducto());
                detalleJson.setNombre(detalle.getProducto().getNombre());
                detalleJson.setCantidad(detalle.getCantidad());
                detalleJson.setPreciocompra(detalle.getPreciocompra());
                detalleJson.setCodigobarra(detalle.getProducto().getCodigo_barra());
                detalleJson.setAbreviacion(detalle.getProducto().getUnidadDeMedida().getAbreviacion());
                detallesJson.add(detalleJson);
            }
            return detallesJson;
        }


        private String generarNumeroSerie() {
                return "EN" + String.format("%04d", gentradasId);
            }
        }

