/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.extremecomponents.table.view;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.calc.CalcResult;
import org.extremecomponents.table.calc.CalcUtils;
import org.extremecomponents.table.core.PreferencesConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.ExtremeUtils;

/**
 * com.extremecomp.table.view.XlsView.java -
 *
 * @author paul horn
 */
public class XlsView implements View {
    private static Log logger = LogFactory.getLog(XlsView.class);
    public static final int WIDTH_MULT = 240; // width per char
    public static final int MIN_CHARS = 8; // minimum char width
    public static final short DEFAULT_FONT_HEIGHT = 8;
    public static final double NON_NUMERIC = -.99999;
    public static final String DEFAULT_MONEY_FORMAT = "$###,###,##0.00";
    public static final String DEFAULT_PERCENT_FORMAT = "##0.0%";
    public static final String NBSP = "&nbsp;";
    public static final int SHEET_PER_PAGE = 65535;


    private HSSFWorkbook wb;
    private List<HSSFSheet> sheets;

    private HSSFPrintSetup ps;
    private Map styles;
    private int rownum;
    private int cellnum;
    private HSSFRow hssfRow;
    private String moneyFormat;
    private String percentFormat;
    private String encoding;

    private int sheetPerRecord = 0;//65535
    private int sheetPage;
    private int sheetIndex = 0;
    private int sheetHeadCount;

    public XlsView() {
    }

    public void beforeBody(TableModel model) {
        logger.debug("XlsView.init()");

        createSheetPage(model);

        moneyFormat = model.getPreferences().getPreference(PreferencesConstants.TABLE_EXPORTABLE + "format.money");
        if (StringUtils.isEmpty(moneyFormat)) {
            moneyFormat = DEFAULT_MONEY_FORMAT;
        }
        percentFormat = model.getPreferences().getPreference(PreferencesConstants.TABLE_EXPORTABLE + "format.percent");
        if (StringUtils.isEmpty(percentFormat)) {
            percentFormat = DEFAULT_PERCENT_FORMAT;
        }

        encoding = model.getExportHandler().getCurrentExport().getEncoding();

        wb = new HSSFWorkbook();

        sheets = new ArrayList<HSSFSheet>(sheetPage);
        for (int k = 0; k < sheetPage; k++) {
            HSSFSheet sheet = wb.createSheet("Sheet" + String.valueOf(k + 1));
            ps = sheet.getPrintSetup();
            sheet.setAutobreaks(true);
            ps.setFitHeight((short) 1);
            ps.setFitWidth((short) 1);
            sheets.add(sheet);
        }

        if (encoding.equalsIgnoreCase("UTF")) {
            // wb.setSheetName(0, "Export Workbook", HSSFWorkbook.ENCODING_UTF_16);
        } else if (encoding.equalsIgnoreCase("UNICODE")) {
            //  wb.setSheetName(0, "Export Workbook", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
        }

        styles = initStyles(wb);

        createHeader(model);


    }

    private void createSheetPage(TableModel model) {
        int sheetTotalCount = model.getLimit().getTotalRows();
        sheetHeadCount = model.getColumnHandler().getColumnDepth();
        sheetPerRecord = SHEET_PER_PAGE - sheetHeadCount;

        if (sheetTotalCount > 0) {
            if (sheetTotalCount % sheetPerRecord == 0) {
                sheetPage = sheetTotalCount / sheetPerRecord;
            } else {
                sheetPage = sheetTotalCount / sheetPerRecord + 1;
            }
        } else {
            sheetPage = 1;
        }

    }

    public void body(TableModel model, Column column) {
        HSSFSheet sheet = sheets.get(sheetIndex);
        if (column.isFirstColumn()) {
            rownum++;
            cellnum = 0;
            hssfRow = sheet.createRow(rownum);
        }

        String value = ExportViewUtils.parseXLS(column.getCellDisplay());

        HSSFCell hssfCell = hssfRow.createCell(cellnum);

        setCellEncoding(hssfCell);

        if (column.isEscapeAutoFormat()) {
            writeToCellAsText(sheet, hssfCell, value, "");
        } else {
            writeToCellFormatted(sheet, hssfCell, value, "");
        }
        cellnum++;

        if (rownum == sheetPerRecord) {
            sheetIndex++;
            rownum = sheetHeadCount-1;
        }
    }

    public Object afterBody(TableModel model) {
        if (model.getLimit().getTotalRows() != 0) {
            for (HSSFSheet sheet : sheets) {
                totals(model, sheet);
            }

        }
        return wb;
    }

    private void createHeader(TableModel model) {
        rownum = 0;
        cellnum = 0;
        for (int k = 0; k < sheetPage; k++) {
            HSSFSheet sheet = sheets.get(k);

            for (int d = 1; d <=sheetHeadCount; d++) {

                HSSFRow row = sheet.createRow(rownum);
                List columns = model.getColumnHandler().getHeaderColumns();
                for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
                    Column column = (Column) iter.next();
                    if (column.getDepth() == d) {

                        String title = column.getCellDisplay();
                        HSSFCell hssfCell = row.createCell(cellnum);
                        setCellEncoding(hssfCell);

                        hssfCell.setCellStyle((HSSFCellStyle) styles.get("titleStyle"));
                        hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        hssfCell.setCellValue(title);
                        int valWidth = (title + "").length() * WIDTH_MULT;
                        sheet.setColumnWidth(hssfCell.getColumnIndex(),  valWidth);

                        if (column.getColspan()>1 || column.getRowspan()>1) {
                            CellRangeAddress rangeAddress = new CellRangeAddress(rownum,rownum+column.getRowspan()-1,cellnum,cellnum+ column.getColspan()-1);
                            sheet.addMergedRegion(rangeAddress);
                        }
                        cellnum+=column.getColspan();

                    }

                }
                cellnum = 0;
                rownum++;
            }

        }
        rownum = sheetHeadCount-1;

    }

    private void writeToCellAsText(HSSFSheet sheet, HSSFCell cell, String value, String styleModifier) {
        // format text
        if (value.trim().equals(NBSP)) {
            value = "";
        }
        cell.setCellStyle((HSSFCellStyle) styles.get("textStyle" + styleModifier));
        fixWidthAndPopulate(sheet, cell, NON_NUMERIC, value);
    }

    private void writeToCellFormatted(HSSFSheet sheet, HSSFCell cell, String value, String styleModifier) {
        double numeric = NON_NUMERIC;

        try {
            numeric = Double.parseDouble(value);
        } catch (Exception e) {
            numeric = NON_NUMERIC;
        }

        if (value.startsWith("$") || value.endsWith("%") || value.startsWith("($")) {
            boolean moneyFlag = (value.startsWith("$") || value.startsWith("($"));
            boolean percentFlag = value.endsWith("%");

            value = StringUtils.replace(value, "$", "");
            value = StringUtils.replace(value, "%", "");
            value = StringUtils.replace(value, ",", "");
            value = StringUtils.replace(value, "(", "-");
            value = StringUtils.replace(value, ")", "");

            try {
                numeric = Double.parseDouble(value);
            } catch (Exception e) {
                numeric = NON_NUMERIC;
            }

            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

            if (moneyFlag) {
                // format money
                cell.setCellStyle((HSSFCellStyle) styles.get("moneyStyle" + styleModifier));
            } else if (percentFlag) {
                // format percent
                numeric = numeric / 100;
                cell.setCellStyle((HSSFCellStyle) styles.get("percentStyle" + styleModifier));
            }
        } else if (numeric != NON_NUMERIC) {
            // format numeric
            cell.setCellStyle((HSSFCellStyle) styles.get("numericStyle" + styleModifier));
        } else {
            // format text
            if (value.trim().equals(NBSP)) {
                value = "";
            }
            cell.setCellStyle((HSSFCellStyle) styles.get("textStyle" + styleModifier));
        }

        fixWidthAndPopulate(sheet, cell, numeric, value);
    }

    private void fixWidthAndPopulate(HSSFSheet sheet, HSSFCell cell, double numeric, String value) {
        int valWidth = 0;

        if (numeric != NON_NUMERIC) {
            cell.setCellValue(numeric);
            valWidth = (cell.getNumericCellValue() + "$,.").length() * WIDTH_MULT;
        } else {
            cell.setCellValue(value);
            valWidth = (cell.getStringCellValue() + "").length() * WIDTH_MULT;

            if (valWidth < (WIDTH_MULT * MIN_CHARS)) {
                valWidth = WIDTH_MULT * MIN_CHARS;
            }
        }

        if (valWidth > sheet.getColumnWidth(cell.getCellNum())) {
            sheet.setColumnWidth(cell.getCellNum(), (short) valWidth);
        }
    }

    private Map initStyles(HSSFWorkbook wb) {
        return initStyles(wb, DEFAULT_FONT_HEIGHT);
    }

    private Map<String,HSSFCellStyle> initStyles(HSSFWorkbook wb, short fontHeight) {
        Map<String,HSSFCellStyle> result = new HashMap<String,HSSFCellStyle>();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        HSSFCellStyle textStyle = wb.createCellStyle();
        HSSFCellStyle boldStyle = wb.createCellStyle();
        HSSFCellStyle numericStyle = wb.createCellStyle();
        HSSFCellStyle numericStyleBold = wb.createCellStyle();
        HSSFCellStyle moneyStyle = wb.createCellStyle();
        HSSFCellStyle moneyStyleBold = wb.createCellStyle();
        HSSFCellStyle percentStyle = wb.createCellStyle();
        HSSFCellStyle percentStyleBold = wb.createCellStyle();

        // Add to export totals
        HSSFCellStyle moneyStyle_Totals = wb.createCellStyle();
        HSSFCellStyle naStyle_Totals = wb.createCellStyle();
        HSSFCellStyle numericStyle_Totals = wb.createCellStyle();
        HSSFCellStyle percentStyle_Totals = wb.createCellStyle();
        HSSFCellStyle textStyle_Totals = wb.createCellStyle();

        result.put("titleStyle", titleStyle);
        result.put("textStyle", textStyle);
        result.put("boldStyle", boldStyle);
        result.put("numericStyle", numericStyle);
        result.put("numericStyleBold", numericStyleBold);
        result.put("moneyStyle", moneyStyle);
        result.put("moneyStyleBold", moneyStyleBold);
        result.put("percentStyle", percentStyle);
        result.put("percentStyleBold", percentStyleBold);

        // Add to export totals
        result.put("moneyStyle_Totals", moneyStyle_Totals);
        result.put("naStyle_Totals", naStyle_Totals);
        result.put("numericStyle_Totals", numericStyle_Totals);
        result.put("percentStyle_Totals", percentStyle_Totals);
        result.put("textStyle_Totals", textStyle_Totals);

        HSSFDataFormat format = wb.createDataFormat();

        // Global fonts
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints(fontHeight);

        HSSFFont fontBold = wb.createFont();
        fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontBold.setColor(HSSFColor.BLACK.index);
        fontBold.setFontName(HSSFFont.FONT_ARIAL);
        fontBold.setFontHeightInPoints(fontHeight);

        // Money Style
        moneyStyle.setFont(font);
        moneyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        moneyStyle.setDataFormat(format.getFormat(moneyFormat));

        // Money Style Bold
        moneyStyleBold.setFont(fontBold);
        moneyStyleBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        moneyStyleBold.setDataFormat(format.getFormat(moneyFormat));

        // Percent Style
        percentStyle.setFont(font);
        percentStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        percentStyle.setDataFormat(format.getFormat(percentFormat));

        // Percent Style Bold
        percentStyleBold.setFont(fontBold);
        percentStyleBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        percentStyleBold.setDataFormat(format.getFormat(percentFormat));

        // Standard Numeric Style
        numericStyle.setFont(font);
        numericStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        // Standard Numeric Style Bold
        numericStyleBold.setFont(fontBold);
        numericStyleBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        // Title Style
        titleStyle.setFont(font);
        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // Standard Text Style
        textStyle.setFont(font);
        textStyle.setWrapText(true);

        // Standard Text Style
        boldStyle.setFont(fontBold);
        boldStyle.setWrapText(true);

        // Money Style Total
        moneyStyle_Totals.setFont(fontBold);
        moneyStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        moneyStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        moneyStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        moneyStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        moneyStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        moneyStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        moneyStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        moneyStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        moneyStyle_Totals.setDataFormat(format.getFormat(moneyFormat));

        // n/a Style Total
        naStyle_Totals.setFont(fontBold);
        naStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        naStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        naStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        naStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        naStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        naStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        naStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        naStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // Numeric Style Total
        numericStyle_Totals.setFont(fontBold);
        numericStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        numericStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        numericStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        numericStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        numericStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        numericStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        numericStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        numericStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // Percent Style Total
        percentStyle_Totals.setFont(fontBold);
        percentStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        percentStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        percentStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        percentStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        percentStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        percentStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        percentStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        percentStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        percentStyle_Totals.setDataFormat(format.getFormat(percentFormat));

        // Text Style Total
        textStyle_Totals.setFont(fontBold);
        textStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        textStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        textStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        textStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        textStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        textStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        textStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        textStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return result;
    }

    // Add to export totals
    public void totals(TableModel model, HSSFSheet sheet) {
        Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();
        if (firstCalcColumn != null) {
            int rows = firstCalcColumn.getCalc().length;
            for (int i = 0; i < rows; i++) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                cellnum = 0;
                for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext(); ) {
                    Column column = (Column) iter.next();
                    if (column.isFirstColumn()) {
                        String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);
                        HSSFCell cell = row.createCell(cellnum);
                        setCellEncoding(cell);
                        if (column.isEscapeAutoFormat()) {
                            writeToCellAsText(sheet, cell, calcTitle, "_Totals");
                        } else {
                            writeToCellFormatted(sheet, cell, calcTitle, "_Totals");
                        }
                        cellnum++;
                        continue;
                    }

                    if (column.isCalculated()) {
                        CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
                        Number value = calcResult.getValue();
                        HSSFCell cell = row.createCell(cellnum);
                        setCellEncoding(cell);
                        if (value != null)
                            if (column.isEscapeAutoFormat()) {
                                writeToCellAsText(sheet, cell, value.toString(), "_Totals");
                            } else {
                                writeToCellFormatted(sheet, cell, ExtremeUtils.formatNumber(column.getFormat(), value, model.getLocale()), "_Totals");
                            }
                        else {
                            cell.setCellStyle((HSSFCellStyle) styles.get("naStyle_Totals"));
                            cell.setCellValue("n/a");
                        }
                        cellnum++;
                    } else {
                        HSSFCell cell = row.createCell(cellnum);
                        setCellEncoding(cell);
                        writeToCellFormatted(sheet, cell, "", "_Totals");
                        cellnum++;
                    }
                }
            }
        }

    }


    //add to set Cell encoding
    private void setCellEncoding(HSSFCell cell) {
        if (encoding.equalsIgnoreCase("UTF")) {
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        } else if (encoding.equalsIgnoreCase("UNICODE")) {
            //cell.setEncoding(HSSFCell.ENCODING_COMPRESSED_UNICODE);

        }
    }

}