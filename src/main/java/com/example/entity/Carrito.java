    package com.example.entity;

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
    @Table(name = "carritos")
    public class Carrito implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CarritoItem> items = new ArrayList<>();


        @ManyToOne
        @JoinColumn(name = "cliente_id")
        private Cliente cliente;

        @Enumerated(EnumType.STRING)
        @Column(name = "estado")
        private EstadoCarrito estado;


    }

