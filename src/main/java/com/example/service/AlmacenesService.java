package com.example.service;

import com.example.entity.Almacenes;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface AlmacenesService {
    Almacenes  insert(Almacenes almacenes);

     void update(Almacenes almacenes);
   void delete(Long alamcenId);
     Almacenes findById(Long alamcenId);
     Collection<Almacenes> findAll();

     Long countByNombre(String numeroAlmacen);

    byte[] exportPdf() throws JRException, FileNotFoundException;
    byte[] exportXls() throws JRException, FileNotFoundException;



}
