package com.sirotenkod.test.usersmanage.utils.sheet;

import com.sirotenkod.test.usersmanage.utils.sheet.annotation.SheetColumn;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class BeanReader<T> {
    private final XSSFSheet sheet;

    private final Class<T> bean;
    private final Map<Integer, PropertyDescriptor> beanColumns;

    private Boolean skipHeader = false;

    public BeanReader(XSSFSheet sheet, Class<T> bean) {
        this.sheet = sheet;

        this.bean = bean;
        this.beanColumns = parseBeanColumns(bean);
    }

    public void setSkipHeader(Boolean skipHeader) {
        this.skipHeader = skipHeader;
    }

    public List<T> read() {
        int rowsCount = sheet.getPhysicalNumberOfRows();

        if (rowsCount == 0) {
            return Collections.emptyList();
        }

        List<T> beansList = new ArrayList<>();

        for (int r = skipHeader ? 1 : 0; r < rowsCount; r++) {
            XSSFRow row = sheet.getRow(r);

            if (Objects.isNull(row)) {
                continue;
            }

            T bean = convertRowToBean(row);

            if (Objects.nonNull(bean)) {
                beansList.add(bean);
            }
        }

        return beansList;
    }

    private Map<Integer, PropertyDescriptor> parseBeanColumns(Class<T> bean) {
        Map<Integer, PropertyDescriptor> columns = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : propertyDescriptors) {
                Method writer = descriptor.getWriteMethod();


                if (writer != null && writer.isAnnotationPresent(SheetColumn.class)) {
                    int columnIndex = writer.getAnnotation(SheetColumn.class).index();


                    columns.put(columnIndex, descriptor);
                }
            }
        } catch (IntrospectionException ex) {
            throw new RuntimeException("Unable to parse bean fields", ex);
        }

        return columns;
    }

    private T convertRowToBean(XSSFRow row) {
        Map<Integer, Object> rowData = getRowData(row);

        T instance = BeanUtils.instantiateClass(bean);

        for (Map.Entry<Integer, Object> entry : rowData.entrySet()) {
            PropertyDescriptor descriptor =
                    beanColumns.getOrDefault(entry.getKey(), null);

            if (descriptor == null) {
                continue;
            }

            MethodParameter methodParameter =
                    BeanUtils.getWriteMethodParameter(descriptor);

            methodParameter.getParameterType();
        }

        return instance;
    }

    private Map<Integer, Object> getRowData(XSSFRow row) {
        int cellsCount = row.getPhysicalNumberOfCells();

        if (cellsCount == 0) {
            return Collections.emptyMap();
        }

        Map<Integer, Object> rowData = new HashMap<>();

        for (int c = 0; c < cellsCount; c++) {
            XSSFCell cell = row.getCell(c);

            Integer index = cell.getColumnIndex();
            Object value = getCellValue(cell);

            rowData.put(index, value);
        }

        return rowData;
    }

    private Object getCellValue(XSSFCell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case BLANK:
            default:
                return null;
        }
    }
}
