package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "marcas")
public class Marcas {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmarca;
    @Column(unique = true, nullable = false)
    private String nombre;

//    @OneToMany(mappedBy = "marcas", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Productos> productos = new ArrayList<>();
}
