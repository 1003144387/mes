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

import com.crsri.mes.entity.ProduceComponentProcess;

public class ProduceComponentUtil {

	public static List<ProduceComponentProcess> getComponentProcess(File file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		// excel的解析步骤
		// 1、创建工作簿对象
		Workbook workbook = WorkbookFactory.create(file);
		// 2、获取sheel遍历
		Sheet partsSheet = workbook.getSheetAt(0);
		// 3、获取最后一行的行号（从0开始）row
		int lastRowNum = partsSheet.getLastRowNum();
		// 4、从第二行开始，进行单元格的遍历
		List<ProduceComponentProcess> componentProcesses = new ArrayList<>();
		for (int i = 1; i <= lastRowNum; i++) {
			Row row = partsSheet.getRow(i);
			Cell cell0 = row.getCell(0);
			String id = null;
			if (cell0 != null) {
				id = cell0.getStringCellValue();
			}
			Cell cell1 = row.getCell(1);
			String partsCodes = null;
			if (cell1 != null) {
				partsCodes = cell1.getStringCellValue();
			}
			Cell cell2 = row.getCell(2);
			String operator = null;
			if (cell2 != null) {
				operator = row.getCell(2).getStringCellValue();
			}
			Cell cell3 = row.getCell(3);
			String image = null;
			if (cell3 != null) {
				image =  row.getCell(3).getStringCellValue();;
			}
			Cell cell4 = row.getCell(4);
			String remark = null;
			if (cell4 != null) {
				remark = row.getCell(4).getStringCellValue();
			}
			Cell cell5 = row.getCell(5);
			Date createTime = null;
			if (cell5 != null) {
				createTime = row.getCell(5).getDateCellValue();
			}
			Cell cell6 = row.getCell(5);
			Date updateTime = null;
			if (cell6 != null) {
				updateTime = row.getCell(6).getDateCellValue();
			}
			ProduceComponentProcess process = new ProduceComponentProcess();
			process.setId(id);
			process.setPartsCode(partsCodes);
			process.setProduceOperator(operator);
			process.setProduceTime(createTime);
			process.setProduceImage(image);
			process.setProduceRemark(remark);
			componentProcesses.add(process);
		}
		return componentProcesses;
	}
}
