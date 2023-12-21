package com.example.reportes;

import com.example.entity.detalles.DetalleEntrada;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Ent_report {
    public byte[] exportToPdf(List<DetalleEntrada> list) throws JRException, FileNotFoundException {
        try {
            return JasperExportManager.exportReportToPdf(getReport(list));
        } catch (IOException e) {
            // Manejar la excepción de IO o relanzarla como una JRException
            throw new JRException("Error al generar el informe PDF", e);
        }
    }

    public byte[] exportToXls(List<DetalleEntrada> list) throws JRException, FileNotFoundException {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
            exporter.setExporterOutput(output);
            exporter.exportReport();
            output.close();
            return byteArray.toByteArray();
        } catch (IOException e) {
            // Manejar la excepción de IO o relanzarla como una JRException
            throw new JRException("Error al generar el informe XLS", e);
        }
    }

//
//    public byte[] exportToPdf(List<DetalleEntrada> list) throws JRException, FileNotFoundException {
//        return JasperExportManager.exportReportToPdf(getReport(list));
//    }
//
//
//    public byte[] exportToXls(List<DetalleEntrada> list) throws JRException, FileNotFoundException {
//        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
//        JRXlsExporter exporter = new JRXlsExporter();
//        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
//        exporter.setExporterOutput(output);
//        exporter.exportReport();
//        output.close();
//        return byteArray.toByteArray();
//    }

//    private JasperPrint getReport(List<DetalleEntrada> list) throws FileNotFoundException, JRException {
//        Map<String, Object> params = new HashMap<>();
//        params.put("petsData", new JRBeanCollectionDataSource(list));
//        params.put("numserie", list.get(0).getEntradas().getNumeroserie());
//
//        //params.put("logo",
//
//        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
//                ResourceUtils.getFile("classpath:templates/report/report_entradaproductos.jrxml")
//                        .getAbsolutePath()), params, new JREmptyDataSource());
//
//        return report;
//    }
private JasperPrint getReport(List<DetalleEntrada> list) throws IOException, JRException {
    Map<String, Object> params = new HashMap<>();
    params.put("petsData", new JRBeanCollectionDataSource(list));
    params.put("numserie", list.get(0).getEntradas().getNumeroserie());

    // Utilizar ClassPathResource para cargar el JRXML desde el classpath
    ClassPathResource jrxmlResource = new ClassPathResource("templates/report/report_entradaproductos.jrxml");

    // Compilar y llenar el informe
    JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlResource.getInputStream());
    JasperPrint report = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

    return report;
}
}
