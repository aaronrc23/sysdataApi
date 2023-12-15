package com.example.service;

import com.example.dto.ProductoDto;
import com.example.entity.Categoria;
import com.example.entity.Productos;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface ProductoService {

    void insert(Productos producto);
     void update(Productos producto);
    void delete(Integer productoId);
     Productos findById(Integer productoId);
     List<Productos> findAll();

    boolean isExistCodigoBarra(String codigo_barra);

    /*cammbio*/
    List<Productos> findByCategoria(Categoria categoria);




}
