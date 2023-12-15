package com.example.entity;
import com.example.entity.detalles.DetalleFactura;
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
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfactura;

    @Column
    private String numeroserie;

    @Column
    private Date fechavencimiento;

    @Column
    private LocalDate fechaemision;

    @Column
    private String Moneda;

    @Column
    private String condicionpago;
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresas;



    @OneToMany(mappedBy = "facturas", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleFactura> detalles = new ArrayList<>();

    @Column
    private boolean activo;

    @PrePersist
    public void prePersist() {
        this.fechaemision = LocalDate.now();
    }
    @PostPersist
    public void postPersist() {
        this.numeroserie = generarNumeroSerie();
    }



    private String generarNumeroSerie() {
        return "F001-" + String.format("%04d", idfactura);
    }
}
