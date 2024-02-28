package com.example.Seguridad.controller;

import com.example.Seguridad.entity.RoleEntity;
import com.example.Seguridad.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesEntityController {

    @Autowired
    private  RolesRepository rolesRepository;


    @GetMapping("/listar")
    public ResponseEntity<List<RoleEntity>> getAllRoles() {
        List<RoleEntity> roles = rolesRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
