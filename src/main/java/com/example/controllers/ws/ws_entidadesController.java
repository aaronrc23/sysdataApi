package com.example.controllers.ws;


import com.example.service.ws.WS_entidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/entidades")
public class ws_entidadesController {

    @Autowired
    private  WS_entidades apiService;

    @GetMapping("/ruc")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Object obtenerRUC(@RequestParam("numero") String numeroRuc) {
        return apiService.obtenerRUC(numeroRuc);
    }

    @GetMapping("/obtenerdni")
    public Object obtenerDni(@RequestParam("numero") String numeroDni) {
        return apiService.obtenerDni(numeroDni);
    }


}
