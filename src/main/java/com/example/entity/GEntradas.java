    package com.example.entity;


    import com.example.entity.detalles.DetalleEntrada;
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


        public String getDetallesAsString() {
            // Lógica para convertir la colección de detalles a una cadena
            StringBuilder detallesAsString = new StringBuilder();
            for (DetalleEntrada detalle : detalles) {
                detallesAsString.append("ID Producto: ");
                detallesAsString.append(detalle.getProducto().getIdproducto());
                detallesAsString.append(", Nombre: ");
                detallesAsString.append(detalle.getProducto().getNombre());
                detallesAsString.append(", Cantidad: ");
                detallesAsString.append(detalle.getCantidad());
                detallesAsString.append("; ");
                // Puedes agregar más información según sea necesario
            }

            // Elimina el punto y coma adicional al final
            if (detallesAsString.length() > 0) {
                detallesAsString.setLength(detallesAsString.length() - 2);
            }

            return detallesAsString.toString();
        }


        private String generarNumeroSerie() {
                return "EN" + String.format("%04d", gentradasId);
            }
        }
