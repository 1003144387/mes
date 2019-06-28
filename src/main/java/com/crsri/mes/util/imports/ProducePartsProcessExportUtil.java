package com.crsri.mes.util.imports;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.crsri.mes.entity.ProducePartsProcess;

/**
 * 生产部件数据迁移的工具类
 * 
 * @author 2011102394
 *
 */
public class ProducePartsProcessExportUtil {

	public static List<ProducePartsProcess> getProducePartsProcess(File file)
			throws IOException, InvalidFormatException {
		// excel的解析步骤
		// 1、创建工作簿对象
		Workbook workbook = WorkbookFactory.create(file);
		// 2、获取sheel遍历
		Sheet partsSheet = workbook.getSheetAt(0);
		// 3、获取最后一行的行号（从0开始）row
		int lastRowNum = partsSheet.getLastRowNum();
		// 4、从第二行开始，进行单元格的遍历
		List<ProducePartsProcess> parts = new ArrayList<>();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = partsSheet.getRow(i);
			Cell cell0 = row.getCell(0);
			String id = null;
			if (cell0 != null) {
				id = cell0.getStringCellValue();
			}
			Cell cell1 = row.getCell(1);
			String partsId = null;
			if (cell1 != null) {
				partsId = cell1.getStringCellValue();
			}
			Cell cell2 = row.getCell(2);
			String partsName = null;
			if (cell2 != null) {
				partsName = row.getCell(2).getStringCellValue();
			}
			Cell cell3 = row.getCell(3);
			Integer specification = null;
			if (cell3 != null) {
				specification = (int) (row.getCell(3).getNumericCellValue());
			}
			Cell cell4 = row.getCell(4);
			String inspection_id = null;
			if (cell4 != null) {
				inspection_id = row.getCell(4).getStringCellValue();
			}
			Cell cell5 = row.getCell(5);
			Date update_time = null;
			if (cell5 != null) {
				update_time = row.getCell(5).getDateCellValue();
			}
			Cell cell6 = row.getCell(6);
			Integer status = null;
			if (cell6 != null) {
				status = (int) (row.getCell(6).getNumericCellValue());
			}
			Cell cell7 = row.getCell(7);
			String operator = null;
			if (cell7 != null) {
				operator = row.getCell(7).getStringCellValue();
			}
			Cell cell8 = row.getCell(8);
			Date operation_time = null;
			if (cell8 != null) {
				operation_time = row.getCell(8).getDateCellValue();
			}
			ProducePartsProcess process = new ProducePartsProcess();
			process.setId(id);
			process.setPartsId(partsId);
			process.setPartsName(partsName);
			process.setSpecification(specification);
			process.setInspectionStatus(status);
			process.setInspectionStartTime(operation_time);
			process.setInspectionOperator(operator);
			process.setInspectionApproveId(inspection_id);
			process.setInspectionStopTime(update_time);
			process.setInspectionApproveStatus(1);
			process.setInspectionApproveResult(1);
			process.setCreateTime(operation_time);
			process.setUpdateTime(update_time);
			parts.add(process);
		}
		return parts;

	}

	public static List<ProducePartsProcess> getProducePartsStock(File file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		// excel的解析步骤
		// 1、创建工作簿对象
		Workbook workbook = WorkbookFactory.create(file);
		// 2、获取sheel遍历
		Sheet partsSheet = workbook.getSheetAt(0);
		// 3、获取最后一行的行号（从0开始）row
		int lastRowNum = partsSheet.getLastRowNum();
		// 4、从第二行开始，进行单元格的遍历
		List<ProducePartsProcess> parts = new ArrayList<>();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = partsSheet.getRow(i);
			Cell cell0 = row.getCell(0);
			String id = null;
			if (cell0 != null) {
				id = cell0.getStringCellValue();
			}
			Cell cell1 = row.getCell(1);
			String processId = null;
			if (cell1 != null) {
				processId = cell1.getStringCellValue();
			}
			Cell cell2 = row.getCell(2);
			String operator = null;
			if (cell2 != null) {
				operator = row.getCell(2).getStringCellValue();
			}
			Cell cell3 = row.getCell(3);
			Integer stock_status = null;
			if (cell3 != null) {
				stock_status = (int) (row.getCell(3).getNumericCellValue());
			}
			Cell cell4 = row.getCell(4);
			Date inTime = null;
			if (cell4 != null) {
				inTime = row.getCell(4).getDateCellValue();
			}
			Cell cell5 = row.getCell(5);
			String position = null;
			if (cell5 != null) {
				position = row.getCell(5).getStringCellValue();
			}
			Cell cell6 = row.getCell(6);
			Integer result = null;
			if (cell6 != null) {
				result = (int) (row.getCell(6).getNumericCellValue());
			}
			Cell cell7 = row.getCell(7);
			Date createTime = null;
			if (cell7 != null) {
				createTime = row.getCell(7).getDateCellValue();
			}
			ProducePartsProcess process = new ProducePartsProcess();
			process.setId(id);
			process.setStockInApproveId(processId);
			process.setStockInApproveResult(result);
			process.setStockInApproveStatus(1);
			process.setStockInOperator(operator);
			process.setStockInStartTime(createTime);
			process.setStockInStopTime(inTime);
			process.setStockStatus(stock_status);
			process.setStockPosition(position);
			parts.add(process);
		}
		return parts;
	}

	public static List<ProducePartsProcess> getProducePartsStockOut(File file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// excel的解析步骤
				// 1、创建工作簿对象
				Workbook workbook = WorkbookFactory.create(file);
				// 2、获取sheel遍历
				Sheet partsSheet = workbook.getSheetAt(0);
				// 3、获取最后一行的行号（从0开始）row
				int lastRowNum = partsSheet.getLastRowNum();
				// 4、从第二行开始，进行单元格的遍历
				List<ProducePartsProcess> parts = new ArrayList<>();
				for (int i = 0; i <= lastRowNum; i++) {
					Row row = partsSheet.getRow(i);
					Cell cell0 = row.getCell(0);
					String id = null;
					if (cell0 != null) {
						id = cell0.getStringCellValue();
					}
					Cell cell1 = row.getCell(1);
					String processId = null;
					if (cell1 != null) {
						processId = cell1.getStringCellValue();
					}
					Cell cell2 = row.getCell(2);
					Date outTime = null;
					if (cell2 != null) {
						outTime = row.getCell(2).getDateCellValue();
					}
					Cell cell3 = row.getCell(3);
					Integer result = null;
					if (cell3 != null) {
						result = (int) (row.getCell(3).getNumericCellValue());
					}
					Cell cell4 = row.getCell(4);
					String operator = null;
					if (cell4 != null) {
						operator = row.getCell(4).getStringCellValue();
					}
					Cell cell5 = row.getCell(5);
					Date createTime = null;
					if (cell5 != null) {
						createTime = row.getCell(5).getDateCellValue();
					}
					ProducePartsProcess process = new ProducePartsProcess();
					process.setId(id);
					process.setStockOutApproveId(processId);
					process.setStockOutApproveResult(result);
					process.setStockOutApproveStatus(1);
					process.setStockOutOperator(operator);
					process.setStockOutStartTime(createTime);
					process.setStockOutStopTime(outTime);
					parts.add(process);
				}
				return parts;
	}
}
