package org.gvozdetscky.demovaadin;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.gvozdetscky.demovaadin.lab1.server.Lab1;
import org.gvozdetscky.demovaadin.lab1.server.model.Record;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class TestExcel {

	Lab1 lab1 = new Lab1();

	@Test
	public void testExcelRead() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Employees sheet");

		List<Record> records = lab1.generateRecord();

		int rownum = 0;
		Cell cell;
		Row row;

		row = sheet.createRow(rownum);

		// EmpNo
		cell = row.createCell(0, CellType.STRING);
		cell.setCellValue("Param1");
		// EmpName
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue("Param2");
		// Salary
		cell = row.createCell(2, CellType.STRING);
		cell.setCellValue("Param3");

		// Data
		for (Record record : records) {
			rownum++;
			row = sheet.createRow(rownum);

			// EmpNo (A)
			cell = row.createCell(0, CellType.NUMERIC);
			cell.setCellValue(record.getParam1());
			// EmpName (B)
			cell = row.createCell(1, CellType.NUMERIC);
			cell.setCellValue(record.getParam2());
			// Salary (C)
			cell = row.createCell(2, CellType.NUMERIC);
			cell.setCellValue(record.getParam3());
		}
		File file = new File("/files/file.xls");
		file.getParentFile().mkdirs();

		FileOutputStream outFile = new FileOutputStream(file);
		workbook.write(outFile);
		System.out.println("Created file: " + file.getAbsolutePath());

	}
}
