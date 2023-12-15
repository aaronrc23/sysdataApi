package com.example.repository;

import com.example.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {
    @Query(value = "SELECT COUNT(*) FROM empresa WHERE ruc = :ruc", nativeQuery = true)
    Long countByNombre(@Param("ruc") Integer ruc);

}
