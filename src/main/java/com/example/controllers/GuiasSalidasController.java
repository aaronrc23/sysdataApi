package com.example.controllers;

import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("api/guiasSalidas")
public class GuiasSalidasController {

//    @Autowired
//    private GuiasSalidaService guiasSalidasService;
//
//    @GetMapping("/listar")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<?> listar_GET()
//    {
//        return new ResponseEntity<>(guiasSalidasService.findAll(), HttpStatus.OK);
//    }
//
//    @GetMapping("listar/{id}")
//    public GuiaSalidas getGuiasEntradas(@PathVariable Long id) {
//        return guiasSalidasService.findById(id);
//    }
//
//    @PostMapping("/registrar")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> registrar_POST(@RequestBody GuiaSalidas guiaSalidas)
//    {
//        GuiaSalidas nuevaGuiaEntradas = guiasSalidasService.insert(guiaSalidas);
//        return new ResponseEntity<>(nuevaGuiaEntradas, HttpStatus.CREATED);
//    }
//
//    @PutMapping("actualizar/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void actualizarGsalidas(@PathVariable Long id, @RequestBody GuiaSalidas guiasalidas) {
//        GuiaSalidas existingGuiaEntradas = guiasSalidasService.findById(id);
//        if (existingGuiaEntradas != null) {
//            guiasalidas.setIdGuia(id);
//            guiasSalidasService.update(guiasalidas);
//        }
//    }
//
//
//
//
//    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void eliminarCategoria(@PathVariable Long id) {
//        guiasSalidasService.delete(id);
//    }
}
