package com.example.repository;


import com.example.entity.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    @Query(value="select count(*) from unidadmedida where nombre = :nombre", nativeQuery=true)
    Long countByNombre(@Param("nombre") String nombre);


    List<UnidadMedida> findByActivo(boolean activo);
}
