package com.example.Seguridad.repository;

import com.example.Seguridad.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<RoleEntity,Long> {
}
