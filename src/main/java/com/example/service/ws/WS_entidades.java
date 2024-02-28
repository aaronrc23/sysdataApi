package com.example.service.ws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WS_entidades {

    @Value("${api.token}")
    private String token; // Token de autenticación

    public Object obtenerRUC(String numero) {

        String apiUrl = "https://api.apis.net.pe/v1/ruc?numero=" + numero;

        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Configurar los encabezados de la solicitud, incluyendo el token de autenticación si es necesario
        // En este caso no se requiere token de autenticación
         HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", "Bearer " + token);

        // Realizar la solicitud a la API y obtener la respuesta
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(apiUrl, Object.class);

        // Obtener el cuerpo de la respuesta
        Object responseBody = responseEntity.getBody();

        // Devolver la respuesta
        return responseBody;
    }



    public Object obtenerDni(String numero) {

        String apiUrl = "https://api.apis.net.pe/v1/dni?numero=" + numero;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(apiUrl, Object.class);

        Object responseBody = responseEntity.getBody();

        return responseBody;
    }
}
