package com.petersoft.mgl.ui;

import com.petersoft.mgl.dto.JavitasDTO;
import com.petersoft.mgl.dto.KarbantartasDTO;
import com.petersoft.mgl.dto.LepcsoDTO;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Karbantartas;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reports {

    public Reports() {

    }

    public static void lepcsoReport(List<LepcsoDTO> lepcsoDTOList)
            throws FileNotFoundException, JRException {

        Map<String, Object> parameters = new HashMap<>();

        InputStream input = new FileInputStream(
                "/Users/gyoripeter/JaspersoftWorkspace/ETMKLepcsoReport/LepcsoReport.jrxml");

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters,
                new JRBeanCollectionDataSource(lepcsoDTOList, false));

        /*call jasper engine to display report in jasperviewer window*/
        JasperViewer.viewReport(jasperPrint);
    }

    public static void dailyReportJavitas(List<JavitasDTO> javitasDTOList)
            throws FileNotFoundException, JRException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream input = new FileInputStream("DailyReportJavitas.jrxml");

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters,
                new JRBeanCollectionDataSource(javitasDTOList, false));

        /*call jasper engine to display report in jasperviewer window*/
        if (jasperPrint.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nincs megjeleníthető adat");
        } else JasperViewer.viewReport(jasperPrint, false);
    }

    public static void dailyReportKarbantartas(List<KarbantartasDTO> karbantartasDTOList)
            throws FileNotFoundException, JRException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream input = new FileInputStream(
                "DailyReportKarbantartas.jrxml");

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters,
                new JRBeanCollectionDataSource(karbantartasDTOList, false));

        /*call jasper engine to display report in jasperviewer window*/
        if (jasperPrint.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nincs megjeleníthető adat");
        } else JasperViewer.viewReport(jasperPrint, false);
    }

    public static void javitasIntervalReport(
            List<Javitas> javitasIntervalList,
            Integer lepcsoSzama,
            LocalDate startDate,
            LocalDate endDate) throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(javitasIntervalList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LepcsoHistory", itemsJRBean);
        parameters.put("LepcsoSzama", lepcsoSzama);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        //read jrxml file and creating jasperdesign object
        InputStream input = new FileInputStream(new File(
                "LepcsoHistoryReport_A4.jrxml"));

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters, new JRBeanCollectionDataSource(javitasIntervalList, false));

        /*call jasper engine to display report in jasperviewer window*/
        if (jasperPrint.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nincs megjeleníthető adat");
        } else JasperViewer.viewReport(jasperPrint, false);
    }

    public static void karbantartasIntervalReport(
            List<Karbantartas> karbantartasIntervalList,
            Integer lepcsoSzama,
            LocalDate startDate,
            LocalDate endDate) throws FileNotFoundException, JRException {

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(karbantartasIntervalList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("KarbantartasHistory", itemsJRBean);
        parameters.put("LepcsoSzama", lepcsoSzama);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        //read jrxml file and creating jasperdesign object
        InputStream input = new FileInputStream(new File(
                "KarbantartasHistoryReport_A4.jrxml"));

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.NO_PAGES);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters, new JRBeanCollectionDataSource(karbantartasIntervalList, false));

        /*call jasper engine to display report in jasperviewer window*/
        if (jasperPrint.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nincs megjeleníthető adat");
        } else JasperViewer.viewReport(jasperPrint, false);


    }


    public static void hianyzoKarbListReport(List<KarbantartasDTO> hianyzoKarbList,
                                             LocalDate startDate,
                                             LocalDate endDate) throws FileNotFoundException, JRException {


        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(hianyzoKarbList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("HianyzoKarbParam", itemsJRBean);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        //read jrxml file and creating jasperdesign object
        InputStream input = new FileInputStream(new File(
                "HianyzoKarbantartas_A4.jrxml"));

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.NO_PAGES);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters, new JRBeanCollectionDataSource(hianyzoKarbList, false));

        /*call jasper engine to display report in jasperviewer window*/
        if (jasperPrint.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nincs megjeleníthető adat");
        } else JasperViewer.viewReport(jasperPrint, false);
    }
}

