package org.gvozdetscky.demovaadin.lab1.server;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.gvozdetscky.demovaadin.lab1.server.model.Record;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Lab1 {

	public final static int EVCLID_MOD = 0;
	public final static int MANCHTAN_MOD = 1;

	private List<Record> recordList = new ArrayList<>();

	private List<Record> k1 = new ArrayList<>();
	private List<Record> k2 = new ArrayList<>();
	private List<Record> k3 = new ArrayList<>();

	private Record point1;
	private Record point2;
	private Record point3;

	public int run(int mod) {

		int count = 1;

		System.out.println("===========Нормируем значения================");

		for (Record record: recordList) {
			record.setParam1(norming(1, 0, record.getParam1()));
			System.out.print("Нормированый 1 параметр = " + record.getParam1());
			record.setParam2(norming(2, -2, record.getParam2()));
			System.out.print(" Нормированый 2 параметр " + record.getParam2());
			System.out.println(" Нормированый 3 параметр " + record.getParam3());
		}

		System.out.println("=======Выбираем первые три точки точки==========");
		point1 = recordList.get(0);
		System.out.println("Первая точка " + point1);
		point2 = recordList.get(1);
		System.out.println("Вторая точка " + point1);
		point3 = recordList.get(2);
		System.out.println("Третья точка " + point1);
		System.out.println("=======Запускаем бесконечный цикл==========");
		while (true) {

			k1.clear();
			k2.clear();
			k3.clear();

			for (Record record: recordList) {

				double k11 = 0, k22 = 0, k33 = 0;

				if (mod == EVCLID_MOD) {
					k11 = euclideanDistance(record, point1);
					k22 = euclideanDistance(record, point2);
					k33 = euclideanDistance(record, point3);

				} else if (mod == MANCHTAN_MOD) {
					k11 = manhatanDistance(record, point1);
					k22 = manhatanDistance(record, point2);
					k33 = manhatanDistance(record, point3);

				}

				System.out.print("1: " + k11 + "; ");
				System.out.print("2: " + k22 + "; ");
				System.out.println("3: " + k33 + "; ");

				if (k11 < k22 && k11 < k33) {
					k1.add(record);
				}
				if (k22 < k11 && k22 < k33) {
					k2.add(record);
				}
				if (k33 < k22 && k33 < k11) {
					k3.add(record);
				}
			}

			System.out.println("Количество с1 :" + k1.size());
			System.out.println("Количество с2 :" + k2.size());
			System.out.println("Количество с3 :" + k3.size());

			System.out.println("==========Вычисляем новуы точки=========");

			System.out.println("==========кластер 1=========");
			final Record newPoint1 = newPoint(k1);
			System.out.println("==========кластер 2=========");
			final Record newPoint2 = newPoint(k2);
			System.out.println("==========кластер 3=========");
			final Record newPoint3 = newPoint(k3);

			if (equlsePoint(point1, newPoint1) && equlsePoint(point2, newPoint2) && equlsePoint(point3, newPoint3)) {
				System.out.println("==========точки совпали=========");
				break;
			} {
				System.out.println("==========точки не совпали=========");
			}

			System.out.println("==========старые точки=========");
			System.out.println(point1);
			System.out.println(point2);
			System.out.println(point3);
			System.out.println("==========новые точки=========");
			System.out.println(newPoint1);
			System.out.println(newPoint2);
			System.out.println(newPoint3);
			System.out.println("==========переназначаем точки=========");
			point1 = newPoint1;
			point2 = newPoint2;
			point3 = newPoint3;

			count++;
		}
		System.out.println("Алгоритм работал = " + count + " раз");

		save();

		for (Record record: recordList) {
			record.setParam1(uNorming(1, 0, record.getParam1()));
			System.out.print("Нормированый 1 параметр = " + record.getParam1());
			record.setParam2(uNorming(2, -2, record.getParam2()));
			System.out.print(" Нормированый 2 параметр " + record.getParam2());
		}

		return count;
	}

	private double norming(int max, int min, double param) {
		return (param - min) / (max - min);
	}

	private double uNorming(int max, int min, double param) {
		return param * (max - min) + min;
	}

	private double euclideanDistance(Record record, Record point) {
		return Math.sqrt(Math.pow(record.getParam1() - point.getParam1(), 2) +
				Math.pow(record.getParam2() - point.getParam2(), 2) +
				Math.pow(record.getParam3() - point.getParam3(), 2));
	}

	private double manhatanDistance(Record record, Record point) {
		return Math.abs(record.getParam1() - point.getParam1()) +
				Math.abs(record.getParam1() - point.getParam1()) +
				Math.abs(record.getParam1() - point.getParam1());
	}

	private Record newPoint(List<Record> claster) {
		double summX = 0;

		for (Record record: claster) {
			summX += record.getParam1();
		}

		double newX = summX / claster.size();
		System.out.print("Новая X :" + newX);

		double summY = 0;

		for (Record record: claster) {
			summY += record.getParam2();
		}

		double newY = summY / claster.size();
		System.out.print(" Новая Y :" + newY);

		double summZ = 0;

		for (Record record: claster) {
			summZ += record.getParam3();
		}

		double newZ = summZ / claster.size();
		System.out.println(" Новая Z :" + newZ);

		final Record record = new Record();
		record.setParam1(newX);
		record.setParam2(newY);
		record.setParam3(newZ);

		return record;
	}

	private boolean equlsePoint(Record point, Record newPoint) {
		return point.getParam1() == newPoint.getParam1() &&
				point.getParam2() == newPoint.getParam2() &&
				point.getParam3() == newPoint.getParam3();
	}

	public List<Record> generateRecord() {

		recordList.clear();

		final Random random = new Random(System.currentTimeMillis());

		System.out.println("===========Генерируем числа================");

		final ArrayList<Record> resultList = new ArrayList<>();

		for (int i = 0; i <50; i++) {
			final Record record = new Record();
			record.setParam1((double) random.nextInt(101) / 100);
			record.setParam2(((double) random.nextInt(400) - 200) / 100);
			record.setParam3(random.nextInt(2));

			System.out.println(record);
			recordList.add(record);
			resultList.add(record);
		}

		System.out.println("Размер листа = " + recordList.size());

		return resultList;
	}

	public void saveFileExcel(String pathFile) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Employees sheet");

		writeClass(sheet);

		File file = new File(pathFile);
		file.getParentFile().mkdirs();

		FileOutputStream outFile = new FileOutputStream(file);
		workbook.write(outFile);
		System.out.println("Файл записан в: " + file.getAbsolutePath());

		outFile.close();
	}

	private void writeClass(HSSFSheet sheet) {
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
		// Salary
		cell = row.createCell(3, CellType.STRING);
		cell.setCellValue("Claster");

		// Data
		for (Record record : k1) {
			rownum++;
			row = sheet.createRow(rownum);

			// Param1 (A)
			cell = row.createCell(0, CellType.NUMERIC);
			cell.setCellValue(record.getParam1());
			// Param2 (B)
			cell = row.createCell(1, CellType.NUMERIC);
			cell.setCellValue(record.getParam2());
			// Param3 (C)
			cell = row.createCell(2, CellType.NUMERIC);
			cell.setCellValue(record.getParam3());
			cell = row.createCell(3, CellType.NUMERIC);
			cell.setCellValue(1);
		}

		// Data
		for (Record record : k2) {
			rownum++;
			row = sheet.createRow(rownum);

			// Param1 (A)
			cell = row.createCell(0, CellType.NUMERIC);
			cell.setCellValue(record.getParam1());
			// Param2 (B)
			cell = row.createCell(1, CellType.NUMERIC);
			cell.setCellValue(record.getParam2());
			// Param3 (C)
			cell = row.createCell(2, CellType.NUMERIC);
			cell.setCellValue(record.getParam3());
			cell = row.createCell(3, CellType.NUMERIC);
			cell.setCellValue(2);
		}

		// Data
		for (Record record : k3) {
			rownum++;
			row = sheet.createRow(rownum);

			// Param1 (A)
			cell = row.createCell(0, CellType.NUMERIC);
			cell.setCellValue(record.getParam1());
			// Param2 (B)
			cell = row.createCell(1, CellType.NUMERIC);
			cell.setCellValue(record.getParam2());
			// Param3 (C)
			cell = row.createCell(2, CellType.NUMERIC);
			cell.setCellValue(record.getParam3());
			cell = row.createCell(3, CellType.NUMERIC);
			cell.setCellValue(3);
		}

		rownum++;
		row = sheet.createRow(rownum);
		cell = row.createCell(0, CellType.STRING);
		cell.setCellValue("Point");
		rownum++;
		row = sheet.createRow(rownum);
		cell = row.createCell(0, CellType.STRING);
		cell.setCellValue("x");
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue("y");
		cell = row.createCell(2, CellType.STRING);
		cell.setCellValue("z");

		rownum++;
		row = sheet.createRow(rownum);
		cell = row.createCell(0, CellType.NUMERIC);
		cell.setCellValue(point1.getParam1());
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue(point1.getParam2());
		cell = row.createCell(2, CellType.STRING);
		cell.setCellValue(point1.getParam3());

		rownum++;
		row = sheet.createRow(rownum);
		cell = row.createCell(0, CellType.NUMERIC);
		cell.setCellValue(point2.getParam1());
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue(point2.getParam2());
		cell = row.createCell(2, CellType.STRING);
		cell.setCellValue(point2.getParam3());

		rownum++;
		row = sheet.createRow(rownum);
		cell = row.createCell(0, CellType.NUMERIC);
		cell.setCellValue(point3.getParam1());
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue(point3.getParam2());
		cell = row.createCell(2, CellType.STRING);
		cell.setCellValue(point3.getParam3());
	}

	private void save() {
		try {
			saveFileExcel("/excel.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
