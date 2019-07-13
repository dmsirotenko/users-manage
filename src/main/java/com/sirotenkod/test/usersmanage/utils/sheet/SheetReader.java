package com.sirotenkod.test.usersmanage.utils.sheet;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SheetReader {
    private final XSSFWorkbook workbook;

    public SheetReader(InputStream inputStream) {
        try {
            this.workbook = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to open a sheet from input stream", ex);
        }
    }

    public Integer getNumberOfSheets() {
        return workbook.getNumberOfSheets();
    }

    public <T> BeanReader<T> getBeanReader(Integer index, Class<T> bean) {
        XSSFSheet sheet = workbook.getSheetAt(index);

        if (Objects.isNull(sheet)) {
            throw new IllegalArgumentException(String.format("Sheet with index %d not found", index));
        }

        return new BeanReader<>(sheet, bean);
    }
}
