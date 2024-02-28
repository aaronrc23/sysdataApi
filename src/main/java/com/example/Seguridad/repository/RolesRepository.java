package com.example.Seguridad.repository;

import com.example.Seguridad.entity.ERole;
import com.example.Seguridad.entity.RoleEntity;
import com.example.entity.Categoria;
import com.example.entity.Productos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RolesRepository extends CrudRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(ERole name);

    @Query("SELECT r FROM RoleEntity r WHERE r.name = ?1")
    Set<RoleEntity> findAllByName(ERole name);

    List<RoleEntity> findAll();

}
