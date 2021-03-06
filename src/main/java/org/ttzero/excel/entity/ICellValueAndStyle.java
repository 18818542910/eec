/*
 * Copyright (c) 2017-2019, guanquan.wang@yandex.com All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ttzero.excel.entity;

import org.ttzero.excel.reader.Cell;
import org.ttzero.excel.util.DateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.ttzero.excel.entity.IWorksheetWriter.isBigDecimal;
import static org.ttzero.excel.entity.IWorksheetWriter.isBool;
import static org.ttzero.excel.entity.IWorksheetWriter.isChar;
import static org.ttzero.excel.entity.IWorksheetWriter.isDate;
import static org.ttzero.excel.entity.IWorksheetWriter.isDateTime;
import static org.ttzero.excel.entity.IWorksheetWriter.isDouble;
import static org.ttzero.excel.entity.IWorksheetWriter.isFloat;
import static org.ttzero.excel.entity.IWorksheetWriter.isInt;
import static org.ttzero.excel.entity.IWorksheetWriter.isLocalDate;
import static org.ttzero.excel.entity.IWorksheetWriter.isLocalDateTime;
import static org.ttzero.excel.entity.IWorksheetWriter.isLocalTime;
import static org.ttzero.excel.entity.IWorksheetWriter.isLong;
import static org.ttzero.excel.entity.IWorksheetWriter.isShort;
import static org.ttzero.excel.entity.IWorksheetWriter.isString;
import static org.ttzero.excel.entity.IWorksheetWriter.isTime;

/**
 * @author guanquan.wang at 2019-09-25 11:24
 */
public interface ICellValueAndStyle {
    /**
     * Setting cell value and cell styles
     *
     * @param row  the row number
     * @param cell the cell
     * @param e    the cell value
     * @param hc   the header column
     */
    void reset(int row, Cell cell, Object e, Sheet.Column hc);

    /**
     * Returns the worksheet name
     *
     * @return name of worksheet
     */
    String getFileSuffix();

    /**
     * Returns the cell style index
     *
     * @param rows the row number
     * @param hc the header column
     * @param o  the cell value
     * @return the style index in xf
     */
    int getStyleIndex(int rows, Sheet.Column hc, Object o);

    /**
     * Setting cell value
     *
     * @param row the row number
     * @param cell  the cell
     * @param e     the cell value
     * @param hc    the header column
     * @param clazz the cell value type
     */
    default void setCellValue(int row, Cell cell, Object e, Sheet.Column hc, Class<?> clazz) {
        if (e == null) {
            setNullValue(row, cell, hc);
            return;
        }
        boolean hasIntProcessor = hc.processor != null;
        if (isString(clazz)) {
            cell.setSv(e.toString());
        } else if (isDate(clazz)) {
            cell.setAv(DateUtil.toDateValue((java.util.Date) e));
        } else if (isDateTime(clazz)) {
            cell.setIv(DateUtil.toDateTimeValue((Timestamp) e));
        } else if (isChar(clazz)) {
            Character c = (Character) e;
            if (hasIntProcessor) conversion(row, cell, c, hc);
            else cell.setCv(c);
        } else if (isShort(clazz)) {
            Short t = (Short) e;
            if (hasIntProcessor) conversion(row, cell, t, hc);
            else cell.setNv(t);
        } else if (isInt(clazz)) {
            Integer n = (Integer) e;
            if (hasIntProcessor) conversion(row, cell, n, hc);
            else cell.setNv(n);
        } else if (isLong(clazz)) {
            cell.setLv((Long) e);
        } else if (isFloat(clazz)) {
            cell.setDv((Float) e);
        } else if (isDouble(clazz)) {
            cell.setDv((Double) e);
        } else if (isBool(clazz)) {
            cell.setBv((Boolean) e);
        } else if (isBigDecimal(clazz)) {
            cell.setMv((BigDecimal) e);
        } else if (isLocalDate(clazz)) {
            cell.setAv(DateUtil.toDateValue((java.time.LocalDate) e));
        } else if (isLocalDateTime(clazz)) {
            cell.setIv(DateUtil.toDateTimeValue((java.time.LocalDateTime) e));
        } else if (isTime(clazz)) {
            cell.setTv(DateUtil.toTimeValue((java.sql.Time) e));
        } else if (isLocalTime(clazz)) {
            cell.setTv(DateUtil.toTimeValue((java.time.LocalTime) e));
        } else {
            cell.setSv(e.toString());
        }
    }

    /**
     * Setting cell value as null
     *
     * @param row the row number
     * @param cell  the cell
     * @param hc    the header column
     */
    default void setNullValue(int row, Cell cell, Sheet.Column hc) {
        boolean hasIntProcessor = hc.processor != null;
        if (hasIntProcessor) {
            conversion(row, cell, 0, hc);
        } else
            cell.setBlank();
    }

    /**
     * Int value conversion to others
     *
     * @param row the row number
     * @param cell the cell
     * @param n    the cell value
     * @param hc   the header column
     */
    default void conversion(int row, Cell cell, int n, Sheet.Column hc) {
        Object e = hc.processor.conversion(n);
        if (e != null) {
            Class<?> clazz = e.getClass();
            if (isInt(clazz)) {
                if (isChar(clazz)) {
                    cell.setCv((Character) e);
                } else if (isShort(clazz)) {
                    cell.setNv((Short) e);
                } else {
                    cell.setNv((Integer) e);
                }
            } else {
                setCellValue(row, cell, e, hc, clazz);
            }
        } else {
            cell.setBlank();
        }
    }
}
