    package com.example.entity;


    import com.example.entity.detalles.DetalleEntrada;
    import com.example.entity.detalles.DetallejsonProducto;
    import com.example.entity.enums.TipoComprobante;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.Date;
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
//        @Column
//        private LocalDate fecha;

        @Column
        private Date fechaRegistro;
//        @Column
//        private Date horaCreacion;
        @Column
        private String numeroserie;

        @ManyToOne
        @JoinColumn(name = "almacen_id")
        private Almacenes almacen;

        @Column
        private Integer cantidadtotal;

        @Enumerated(EnumType.STRING)
        @Column
        private TipoComprobante tipoComprobante;

        @Column
        private Double preciototal;

        @Column
        private boolean estado ;




        @OneToMany(mappedBy = "entradas", cascade = CascadeType.ALL)
        @JsonIgnore
        private List<DetalleEntrada> detalles = new ArrayList<>();


        @ManyToOne
        @JoinColumn(name = "proveedor_id")
        private Proveedores proveedores;

//        @PrePersist
//        public void prePersist() {
//            this.fecha = LocalDate.now();
//        }
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
            if (gentradasId == null) {
                return "Número de serie no disponible";
            }
            String tipoComprobantePrefix = tipoComprobante.toString().substring(0, 1); // Tomar la primera letra del tipo de comprobante
            String almacenId = String.format("%03d", almacen.getId()); // Formatear el ID del almacén a tres dígitos
            String entradaId = String.format("%04d", gentradasId); // Asegurarse de que el ID tenga 6 dígitos
            return tipoComprobantePrefix + almacenId + "-" + entradaId;
        }


    }

