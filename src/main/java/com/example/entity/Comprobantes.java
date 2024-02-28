package com.example.entity;

import com.example.dto.TotalCarrito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comprobantes")
public class Comprobantes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ComprobanteDetalle> detalles = new ArrayList<>();


    @Column
    private Date fechaRegistro;
    @Column
    private Date horaCreacion;
    @Column
    private Double precioTotal;
    @Column
    private Integer cantidadTotal;
    @Column
    private Double descuentoTotal;
    @Column
    private boolean estado = true;
    @Column
    private Date horaModificacion;
    @Column
    private Date fechaModificacion;



    public void setItemsDelCarrito(List<CarritoItem> itemsDelCarrito) {

        for (CarritoItem carritoItem : itemsDelCarrito) {
            ComprobanteDetalle detalle = new ComprobanteDetalle();
            detalle.setProducto(carritoItem.getProducto());
            detalle.setCantidad(carritoItem.getCantidad());
            // Establecer el id del producto en el ComprobanteDetalle
            detalle.getProducto().setIdproducto(carritoItem.getProducto().getIdproducto());
            this.detalles.add(detalle);
        }
    }


    // Método para establecer el total del carrito
    public void setTotalCarrito(TotalCarrito totalCarrito) {
        // Aquí puedes establecer el total del carrito si es necesario
        // Por ejemplo:
        // this.totalCarrito = totalCarrito;
    }
}
