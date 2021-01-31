package com.petersoft.mgl.filehandler;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.*;

public class ExcelFileHandler {

    public interface LoadListener {
        void setProgress(int percent);
    }

    public static Map<Integer, Set<String>> importDataFromKarbantartasExcel(String filename, LoadListener listener)
            throws IOException, DateTimeParseException {
        Map<Integer, String> stringMap = retrieveDataFromSheet(filename);
        if (listener != null)
            listener.setProgress(50);
        Map<Integer, Set<String>> karbantartasLepcsoSzamSzerint = convertStringToKarbantartasTipus(stringMap);
        if (listener != null)
            listener.setProgress(100);
        return karbantartasLepcsoSzamSzerint;
    }

    private static int getLengthOfMonth(XSSFCell cell) {
        String stringDate;
        int lengthOfMonth = 0;
        try {
            if (cell.getCellType() == CellType.STRING) {
                stringDate = cell.getStringCellValue();
                DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("yyyy. MMMM")
                        .toFormatter(new Locale("hu", "HU"));
                TemporalAccessor parsed = fmt.parse(stringDate);
                YearMonth yearMonth = YearMonth.from(parsed);
                lengthOfMonth = yearMonth.lengthOfMonth();
                return lengthOfMonth;
            }
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Hiba a dátumbeolvasás során\n" +
                    "Nincs az évszám után pont",
                    e.getParsedString(), 0);
        }
        return lengthOfMonth;
    }

    private static Map<Integer, Set<String>> convertStringToKarbantartasTipus(Map<Integer, String> stringMap) {
        Map<Integer, Set<String>> setMap = new TreeMap<>();

        for (Map.Entry<Integer, String> entry : stringMap.entrySet()) {
            Set<String> karbSet = new HashSet<>();
            String[] values = entry.getValue().trim().split(" ");
            Arrays.stream(values)
                    .filter(e -> !e.equals(""))
                    .forEach(karbSet::add);
            setMap.put(entry.getKey(), karbSet);
        }
        return setMap;
    }

    private static Map<Integer, String> retrieveDataFromSheet(String filename) throws IOException {
        Map<Integer, String> resultMap = new TreeMap<>();
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(filename))) {
            int numberOfSheets = xssfWorkbook.getNumberOfSheets();
            int COLUMN_INDEX_OF_MONTH = 5;
            int ROW_INDEX_OF_MONTH = 0;
            int FIRST_DATA_ROW_INDEX = 5;
            int FIRST_DATA_COLUMN_INDEX = 1;
            XSSFCell cellIndexOfYearAndMonth = xssfWorkbook.getSheetAt(0)
                    .getRow(ROW_INDEX_OF_MONTH).getCell(COLUMN_INDEX_OF_MONTH);
            int numberOfDays = getLengthOfMonth(cellIndexOfYearAndMonth);

            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet s = xssfWorkbook.getSheetAt(i);
                int lastRowNumber = s.getLastRowNum();
                if (i == 1) lastRowNumber = 66;
                for (int j = FIRST_DATA_ROW_INDEX; j <= lastRowNumber; j++) {
                    int lepcsoSzama = 0;
                    for (int k = FIRST_DATA_COLUMN_INDEX;
                         k <= numberOfDays + FIRST_DATA_COLUMN_INDEX; k++) {
                        XSSFCell cell = s.getRow(j).getCell(k);
                        if (cell != null) {
                            if (k == 1) {
                                lepcsoSzama = Integer.parseInt(cell.getRawValue());
                            } else if (cell.getCellType() == CellType.STRING) {
                                resultMap.put(lepcsoSzama, cell.getStringCellValue().trim());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return resultMap;
    }
}
