package com.petersoft.mgl.filehandler;

import com.petersoft.mgl.model.Alkatresz;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlkatreszFileHandler {

    public static List<Alkatresz> importExcelFile(String filename) throws IOException {
        List<Alkatresz> alkatreszList = new ArrayList<>();
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(filename))) {
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            int rowNumber = xssfWorkbook.getSheetAt(0).getLastRowNum();
            for (int i = 1; i <= rowNumber; i++) {
                Alkatresz alkatresz = new Alkatresz();
                for (int j = 1; j <= 7; j++) {
                    XSSFCell cell = sheet.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (j) {
                        case 1:
                            alkatresz.setHely(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            break;
                        case 2:
                            alkatresz.setLeiras(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            break;
                        case 3:
                            alkatresz.setTipus(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            break;
                        case 4:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                alkatresz.setCikkszam(String.valueOf(cell.getNumericCellValue()));
                            }
                            if (cell.getCellType() == CellType.STRING) {
                                alkatresz.setCikkszam(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            }
                          //  alkatresz.setCikkszam(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            break;
                        case 5:
                            alkatresz.setEgyseg(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            break;
                        case 6:
                            alkatresz.setDarabszam((int) cell.getNumericCellValue());
                            break;
                        case 7:
                            alkatresz.setMegjegyzes(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                            break;
                    }
                 //   System.out.println(cell.getCellType() + " " + j + " " + i + " " + cell.toString());
                }
                alkatreszList.add(alkatresz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getCause());
        }

        return alkatreszList;
    }
}


