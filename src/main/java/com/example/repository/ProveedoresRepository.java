package com.example.repository;

import com.example.entity.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProveedoresRepository extends JpaRepository<Proveedores,Long> {

    @Query(value="select count(*) from proveedores where numruc = :numruc", nativeQuery=true)
    Long countByNombre(@Param("numruc") String numruc);
}
