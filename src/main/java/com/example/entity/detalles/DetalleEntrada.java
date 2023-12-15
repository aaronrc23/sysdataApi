    package com.example.entity.detalles;

    import com.example.entity.GEntradas;
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
    @Table(name = "detalle_entrada")
    public class DetalleEntrada implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long detalleId;

        @ManyToOne
        @JoinColumn(name = "entradas_id")
        private GEntradas entradas;

        @ManyToOne
        @JoinColumn(name = "producto_id")
        @JsonIgnore
        private Productos producto;

        private int cantidad;


//        public String getNumserie(){
//            return this.entradas!=null? this.entradas.getNumeroserie():"-------";
//        }

        public String getNumserie() {
            return this.entradas != null ? this.entradas.getNumeroserie() : "------";
        }

        public String getUnidad() {
            return this.producto != null ? this.producto.getUnidadDeMedida().getAbreviacion() : "-------";
        }

        public Long getIdentrada() {
            return this.entradas != null ? this.entradas.getGentradasId() : null;
        }

        public String getCodigobarra(){
            return this.producto!=null? this.producto.getCodigo_barra():"-------";
        }

        public String getNombreprod(){
            return this.producto!=null? this.producto.getNombre():"-------";
        }







}
