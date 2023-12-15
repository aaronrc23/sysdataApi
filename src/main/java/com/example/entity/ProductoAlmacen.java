package com.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movimientosalmancen")
public class ProductoAlmacen implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pa")
    private Integer id;


//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private TipoGuia tipoGuia;
//


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos producto;


    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private LocalDate fechaMovimiento;




//    @Column(name = "stk_actual", nullable = false, columnDefinition = "INT DEFAULT 0")
//    private int stockActual;

}
