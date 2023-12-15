package com.example.reportes;

import com.example.entity.detalles.DetalleEntrada;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Ent_report {



    public byte[] exportToPdf(List<DetalleEntrada> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(list));
    }


    public byte[] exportToXls(List<DetalleEntrada> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private JasperPrint getReport(List<DetalleEntrada> list) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("petsData", new JRBeanCollectionDataSource(list));
        params.put("numserie", list.get(0).getEntradas().getNumeroserie());

        //params.put("logo",

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:templates/report/report_entradaproductos.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }
}
