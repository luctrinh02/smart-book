package com.dantn.bookStore.ultilities;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUltil {
	public static final int COLUMN_INDEX_ISBN=0;
	public static final int COLUMN_INDEX_AMOUNT=1;
	public static final int COLUMN_INDEX_CHARACTOR=2;
	public static final int COLUMN_INDEX_CONTENT=3;
	public static final int COLUMN_INDEX_DESCRIPTION=4;
	public static final int COLUMN_INDEX_DISCOUNT=5;
	public static final int COLUMN_INDEX_NAME=6;
	public static final int COLUMN_INDEX_NUMOFPAGE=7;
	public static final int COLUMN_INDEX_PRICE=8;
	public static final int COLUMN_INDEX_TYPE=9;
	public static final int COLUMN_INDEX_AUTHORID=11;
	public static final int COLUMN_INDEX_PUBLISHERID=10;
	public static final int COLUMN_INDEX_STATUSID=12;
	public static final int COLUMN_INDEX_HEIGHT=13;
	public static final int COLUMN_INDEX_LENGTH=14;
	public static final int COLUMN_INDEX_WEIGHT=15;
	public static final int COLUMN_INDEX_WIDTH=16;
	public static final int COLUMN_INDEX_IMAGE=17;
	public static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException,IllegalArgumentException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("Không phải định dạng excel");
		}
		return workbook;
	}

	public static Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellType();
		Object cellValue = null;
		switch (cellType) {
		case BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			cellValue = evaluator.evaluate(cell).getNumberValue();
			break;
		case NUMERIC:
			cellValue = cell.getNumericCellValue();
			break;
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
		case BLANK:
		case ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
}
