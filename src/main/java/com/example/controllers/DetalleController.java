package com.example.controllers;

import com.example.entity.detalles.DetalleEntrada;
import com.example.reportes.Ent_report;
import com.example.repository.detalles.DetalleEntradaRepository;
import com.example.service.DetalleEntradaService;
import com.example.serviceimpl.DetalleEntradaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/detalleentrada")
public class DetalleController {

    @Autowired
    private DetalleEntradaRepository detallerepositry;
    @Autowired
    private DetalleEntradaService detalleService;
    @Autowired
    private DetalleEntradaServiceImpl detalleserviceimpl;


    private  DetalleEntrada detalleEntrada;
    @Autowired
    private Ent_report entradaReport;

    @GetMapping("/listardetalle")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> listar_GET()
    {
        return new ResponseEntity<>(detalleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("listardetalle/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public DetalleEntrada getProveedor(@PathVariable Long id) {
        return detalleService.findById(id);
    }

//    @GetMapping("/{detalleId}/export-pdf")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<byte[]> exportPdfById(@PathVariable Long detalleId) throws JRException, IOException {
//        byte[] pdfReport = detalleService.exportPdfById(detalleId);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("inline", "detalleReport.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfReport);
//    }

    @GetMapping("/{gentradasId}/export-pdf")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<byte[]> exportarPdfPorEntrada(@PathVariable Long gentradasId) throws JRException, IOException {
        List<DetalleEntrada> detalles = detalleService.listarDetallesPorEntrada(gentradasId);
        byte[] pdfReport = entradaReport.exportToPdf(detalles);

        // Verificar si la lista de detalles no está vacía
        if (detalles.isEmpty()) {
            // Manejar el caso en que no se encuentren detalles por la entrada ID
            throw new EntityNotFoundException("Detalles de entrada no encontrados para la entrada ID: " + gentradasId);
        }

        // Obtener el numserie del primer detalle de la lista (puedes ajustar según tus necesidades)
        String numserie = detalles.get(0).getNumserie();
        Map<String, Object> params = new HashMap<>();
        params.put("numserie", detalles.get(0).getNumserie());

        // Imprimir el numserie en la consola
        System.out.println("Número de serie obtenido: " + numserie);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("detalleReport", "detalleReport.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfReport);
    }




}
