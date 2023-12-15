    package com.example.serviceimpl;

    import com.example.Exception.ProductoNotFoundException;
    import com.example.dto.FacturaRequest;
    import com.example.dto.ProductoDto;
    import com.example.entity.*;

    import com.example.entity.detalles.DetalleFactura;
    import com.example.repository.*;
    import com.example.repository.detalles.DetalleFacturaRepository;
    import com.example.service.FacturaService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class FacturaServiceImpl implements FacturaService {

         EmpresaRepository empresaRepository;
         UnidadMedidaRepository unidadmedidaRepository;
         FacturaRepository facturaRepository;
         DetalleFacturaRepository detalleFacturaRepository;

         ProductoRepository productoRepository;
         ProductoStockRepository productoStockRepository;


        public FacturaServiceImpl(ProductoRepository productoRepository, FacturaRepository facturaRepository, DetalleFacturaRepository detalleFacturaRepository, UnidadMedidaRepository unidadmedidaRepository, EmpresaRepository empresaRepository,ProductoStockRepository productoStockRepository) {
            this.productoRepository = productoRepository;
            this.facturaRepository = facturaRepository;
            this.detalleFacturaRepository = detalleFacturaRepository;
            this.unidadmedidaRepository = unidadmedidaRepository;
            this.empresaRepository = empresaRepository;
            this.productoStockRepository = productoStockRepository;
        }


        @Override
        public Factura crearEntrada(FacturaRequest facturarequest) {
            Factura pedido = new Factura();
            pedido.setFechaemision(facturarequest.getFechaemision());
            pedido.setFechavencimiento(facturarequest.getFechavencimiento());
            pedido.setMoneda(facturarequest.getMoneda());
            pedido.setActivo(facturarequest.isActivo());


    //        UnidadMedida unidadmedida = unidadmedidaRepository.findById(facturarequest.getUnidadId())
    //                .orElseThrow(() -> new ProductoNotFoundException("Proveedor con ID " + facturarequest.getUnidadId() + " no encontrado"));
    //
    //        pedido.setUnidadMedida(unidadmedida);


            Empresa empresa = empresaRepository.findById(facturarequest.getEmpresaId())
                    .orElseThrow(() -> new ProductoNotFoundException("Empresa con ID " + facturarequest.getEmpresaId() + " no encontrado"));

            pedido.setEmpresas(empresa);


            List<DetalleFactura> detalles = new ArrayList<>();

            for (ProductoDto productoDto : facturarequest.getProductos()) {
                Productos productoExistente = obtenerProductoPorId(productoDto.getIdproducto());

                if (productoExistente != null) {
                    DetalleFactura detallePedido = new DetalleFactura();
                    detallePedido.setFacturas(pedido);
                    detallePedido.setProducto(productoExistente);
                    detallePedido.setCantidad(productoDto.getCantidad());

                    detalles.add(detallePedido);
                } else {
                    throw new ProductoNotFoundException("Producto con ID " + productoDto.getIdproducto() + " no encontrado");
                }
            }
            // Guardar el pedido primero
            Factura pedidoGuardado = facturaRepository.save(pedido);
            // Asignar el pedido a los detalles y guardar los detalles

            detalles.forEach(detalle -> detalle.setFacturas(pedidoGuardado));
            detalleFacturaRepository.saveAll(detalles);

            // Asignar los detalles al pedido guardado
            pedidoGuardado.setDetalles(detalles);

            // Verificar y reducir el stock después de guardar la factura
            verificarYReducirStock(pedidoGuardado);
            return pedidoGuardado;
        }

        private void verificarYReducirStock(Factura factura) {
            for (DetalleFactura detalle : factura.getDetalles()) {
                // Obtener el producto asociado al detalle
                Productos producto = detalle.getProducto();

                // Verificar y reducir el stock
                reducirStock(producto.getIdproducto(), detalle.getCantidad());
            }
        }

        private void reducirStock(Integer productoId, int cantidad) {
            // Actualizar el stock utilizando el repositorio
            productoStockRepository.reducirStock(productoId, cantidad);
        }



        @Override
        public List<Factura> listarfacturas() {
            return facturaRepository.findAll();
        }

        private Productos obtenerProductoPorId(Integer idProducto) {
            Optional<Productos> productoOptional = productoRepository.findById(idProducto);
            return productoOptional.orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + idProducto + " no encontrado"));
        }



        @Override
        public List<ProductoDto> listarProductosEnFacturas(Long facturaId) {
            return detalleFacturaRepository.findProductosEnFacturas(facturaId);
        }

        @Override
        public Optional<Productos> findById(Integer id) {
            return Optional.empty();
        }




    }
