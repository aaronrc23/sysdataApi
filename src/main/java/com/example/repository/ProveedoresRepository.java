package com.example.repository;

import com.example.entity.Almacenes;
import com.example.entity.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProveedoresRepository extends JpaRepository<Proveedores,Long> {

    @Query(value="select count(*) from proveedores where numruc = :numruc", nativeQuery=true)
    Long countByNombre(@Param("numruc") String numruc);

    List<Proveedores> findByActivo(boolean activo);
}
