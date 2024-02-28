package com.example.Seguridad.controller;

import com.example.Seguridad.controller.request.CreateUserDTO;
import com.example.Seguridad.controller.request.RoleIdDTO;
import com.example.Seguridad.entity.UserEntity;
import com.example.Seguridad.repository.RolesRepository;
import com.example.Seguridad.repository.UserRepository;
import com.example.Seguridad.entity.ERole;
import com.example.Seguridad.entity.RoleEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:5173/")
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @GetMapping("/listUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>listUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

//    @PostMapping("/createUser")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
//        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
//                .map(role -> RoleEntity.builder()
//                        .name(ERole.valueOf(role))
//                        .build())
//                .collect(Collectors.toSet());
//
//        UserEntity userEntity = UserEntity.builder()
//                .username(createUserDTO.getUsername())
//                .password(passwordEncoder.encode(createUserDTO.getPassword()))
//                .email(createUserDTO.getEmail())
//                .roles(roles)
//                .enabled(createUserDTO.isEnabled())  // Ajuste para incluir el estado de activación
//                .build();
//
//        userRepository.save(userEntity);
//        return ResponseEntity.ok(userEntity);
//    }
//@PostMapping("/createUser")
//@PreAuthorize("hasRole('ADMIN')")
//public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
//    // Obtener roles desde los nombres proporcionados
//    Set<RoleEntity> roles = getRolesFromNames(createUserDTO.getRoles());
//
//
//    UserEntity userEntity = UserEntity.builder()
//            .username(createUserDTO.getUsername())
//            .password(passwordEncoder.encode(createUserDTO.getPassword()))
//            .email(createUserDTO.getEmail())
//            .roles(roles)
//            .enabled(createUserDTO.isEnabled())
//            .build();
//
//    userRepository.save(userEntity);
//    return ResponseEntity.ok(userEntity);
//}
//
//    // Método para obtener roles desde nombres
//    private Set<RoleEntity> getRolesFromNames(Set<String> roleNames) {
//        Set<RoleEntity> roles = new HashSet<>();
//        for (String roleName : roleNames) {
//            roles.addAll(rolesRepository.findAllByName(ERole.valueOf(roleName)));
//        }
//        return roles;
//    }


    @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        // Extraer los IDs de roles del DTO y buscar los roles correspondientes en la base de datos
        List<RoleEntity> roles = new ArrayList<>();
        for (RoleIdDTO roleIdDTO : createUserDTO.getRoleIds()) {
            Optional<RoleEntity> roleOptional = rolesRepository.findById(roleIdDTO.getId());
            if (roleOptional.isPresent()) {
                roles.add(roleOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró el rol con ID: " + roleIdDTO.getId());
            }
        }

        // Crear el usuario con los roles asociados
        UserEntity newUser = new UserEntity();
        newUser.setEmail(createUserDTO.getEmail());
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setPassword(createUserDTO.getPassword());
        newUser.setEnabled(createUserDTO.getEnabled());
        newUser.setRoles(new HashSet<>(roles));

        UserEntity savedUser = userRepository.save(newUser);

        // Devolver el usuario creado en formato JSON
        return ResponseEntity.ok(savedUser);
    }


    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("Se ha borrado el usuario con id " + id);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se ha autenticado ningún usuario");
        }
    }


    /*Processo de Activacion y reactivacion*/
    @PutMapping("/deactivateUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setEnabled(false);
            userRepository.save(user);
            return ResponseEntity.ok("Usuario desactivado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/reactivateUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reactivateUser(@PathVariable Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Usuario reactivado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }






    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CreateUserDTO updateUserDTO) {
        Map<String, Object> response = new HashMap<>(); // Crear un mapa para almacenar la respuesta

        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Actualizar todos los campos del usuario
            user.setEmail(updateUserDTO.getEmail());
            user.setUsername(updateUserDTO.getUsername());
            user.setEnabled(updateUserDTO.isEnabled());
            // Actualiza otros campos según sea necesario

            // Verificar si se proporciona un nuevo rol en la solicitud
            if (updateUserDTO.getRoleIds() != null && !updateUserDTO.getRoleIds().isEmpty()) {
                // Actualizar roles solo si se proporciona un nuevo rol
                List<RoleEntity> roles = new ArrayList<>();
                for (RoleIdDTO roleIdDTO : updateUserDTO.getRoleIds()) {
                    Optional<RoleEntity> roleOptional = rolesRepository.findById(roleIdDTO.getId());
                    if (roleOptional.isPresent()) {
                        roles.add(roleOptional.get());
                    } else {
                        response.put("error", "No se encontró el rol con ID: " + roleIdDTO.getId()); // Agregar mensaje de error al mapa de respuesta
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Devolver la respuesta con estado 400 y el mapa de respuesta
                    }
                }
                user.setRoles(new HashSet<>(roles));
            }

            userRepository.save(user);

            response.put("message", "Usuario actualizado con éxito"); // Agregar mensaje de éxito al mapa de respuesta
            return ResponseEntity.ok(response); // Devolver la respuesta con estado 200 y el mapa de respuesta
        } else {
            response.put("error", "Usuario no encontrado"); // Agregar mensaje de error al mapa de respuesta
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Devolver la respuesta con estado 404 y el mapa de respuesta
        }
    }




//    @PostMapping("/createRoles")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> createRoles(@RequestBody List<String> roleNames) {
//        try {
//            Set<RoleEntity> roles = new HashSet<>();
//
//            for (String roleName : roleNames) {
//                ERole eRole = ERole.valueOf(roleName);
//                RoleEntity roleEntity = RoleEntity.builder().name(eRole).build();
//                roles.add(roleEntity);
//            }
//
//            rolesRepository.saveAll(roles);
//
//            return ResponseEntity.ok("Roles creados exitosamente");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre de rol inválido");
//        }
//    }




}
