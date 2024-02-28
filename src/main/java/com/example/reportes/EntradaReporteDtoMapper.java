package com.example.reportes;

import com.example.dto.EntradaReporteDto;
import com.example.dto.ProductoDto;
import com.example.entity.Productos;
import com.example.entity.detalles.DetalleEntrada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntradaReporteDtoMapper {
    public static EntradaReporteDto mapDetalleEntradaToDto(DetalleEntrada detalle) {
        // Obtenemos la información de DetalleEntrada
        Long detalleId = detalle.getDetalleId();
//        LocalDate fecha = detalle.getEntradas().getFecha(); // Suponiendo que la fecha está en Entradas
        Productos producto = detalle.getProducto();
        Integer idProducto = (producto != null) ? producto.getIdproducto() : null; // Obtenemos el ID del producto
        String nombreProducto = detalle.getProducto().getNombre();
        int cantidad = detalle.getCantidad();
        String numSerie = detalle.getEntradas().getNumeroserie(); // Si necesitas esto en el DTO

        // Construimos el ProductoDto
        ProductoDto productoDto = ProductoDto.builder()
                .idproducto(idProducto)
                .nombre(nombreProducto)
                .cantidad(cantidad)
                // Otros campos del ProductoDto si los tienes
                .build();

        // Construimos el EntradaReporteDto
        return EntradaReporteDto.builder()
//                .fecha(fecha)
                .productos(List.of(productoDto))  // Si necesitas una lista, ajusta según tu estructura
                .numserie(numSerie)
                .proveedorNombre(detalle.getEntradas().getProveedores().getNombre_prov())
                .build();

    }

    public static List<EntradaReporteDto> mappearDetallesAReporteDTO(List<DetalleEntrada> detalles) {
        // Lista para almacenar los resultados
        List<EntradaReporteDto> reporteDTOList = new ArrayList<>();
        // Iteramos sobre cada DetalleEntrada
        for (DetalleEntrada detalle : detalles) {
            // Aplicamos la lógica de mapeo para cada detalle
            EntradaReporteDto entradaReporteDto = mapDetalleEntradaToDto(detalle);

            // Agregamos el resultado a la lista
            reporteDTOList.add(entradaReporteDto);
        }

        // Retornamos la lista resultante
        return reporteDTOList;
    }
}
