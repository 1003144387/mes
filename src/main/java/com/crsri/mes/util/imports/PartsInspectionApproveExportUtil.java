package com.crsri.mes.util.imports;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.crsri.mes.entity.ProducePartsInspectionApprove;

/**
 * 部件来料检验数据导入的工具类
 * 
 * @author 2011102394
 *
 */
public class PartsInspectionApproveExportUtil {

	public static List<ProducePartsInspectionApprove> partsInput(File file) throws IOException, InvalidFormatException {
		// excel的解析步骤
		// 1、创建工作簿对象
		Workbook workbook = WorkbookFactory.create(file);
		// 2、获取sheel遍历
		Sheet partsSheet = workbook.getSheetAt(0);
		// 3、获取最后一行的行号（从0开始）row
		int lastRowNum = partsSheet.getLastRowNum();
		// 4、从第二行开始，进行单元格的遍历
		List<ProducePartsInspectionApprove> parts = new ArrayList<>();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = partsSheet.getRow(i);
			Cell cell0 = row.getCell(0);
			String id = null;
			if (cell0 != null) {
				id = cell0.getStringCellValue();
			}
			Cell cell1 = row.getCell(1);
			String codes = null;
			if (cell1 != null) {
				codes = cell1.getStringCellValue();
			}
			Cell cell2 = row.getCell(2);
			String partsName = null;
			if (cell2 != null) {
				partsName = row.getCell(2).getStringCellValue();
			}
			Cell cell3 = row.getCell(3);
			String partsId = null;
			if (cell3 != null) {
				partsId = row.getCell(3).getStringCellValue();
			}
			Cell cell4 = row.getCell(4);
			Integer number = null;
			if (cell4 != null) {
				number = (int) (row.getCell(4).getNumericCellValue());
			}
			Cell cell5 = row.getCell(5);
			Integer status = null;
			if (cell5 != null) {
				status = (int) (row.getCell(5).getNumericCellValue());
			}
			Cell cell6 = row.getCell(6);
			Integer process_instance_type = null;
			if (cell6 != null) {
				process_instance_type = (int) (row.getCell(6).getNumericCellValue());
			}
			Cell cell7 = row.getCell(7);
			Integer process_instance_result = null;
			if (cell7 != null) {
				process_instance_result = (int) (row.getCell(7).getNumericCellValue());
			}
			Cell cell8 = row.getCell(8);
			String image = null;
			if (cell8 != null) {
				image = row.getCell(8).getStringCellValue();
			}
			Cell cell9 = row.getCell(9);
			String operator = null;
			if (cell9 != null) {
				operator = row.getCell(9).getStringCellValue();
			}
			Cell cell10 = row.getCell(10);
			String remark = null;
			if (cell10 != null) {
				remark = row.getCell(10).getStringCellValue();
			}
			Cell cell11 = row.getCell(11);
			Date operation_time = null;
			if (cell11 != null) {
				operation_time = row.getCell(11).getDateCellValue();
			}
			Cell cell12 = row.getCell(12);
			Date update_time = null;
			if (cell12 != null) {
				update_time = row.getCell(12).getDateCellValue();
			}
			Cell cell13 = row.getCell(13);
			Integer specification = null;
			if (cell13 != null) {
				specification = (int) (row.getCell(13).getNumericCellValue());
			}

			ProducePartsInspectionApprove approve = new ProducePartsInspectionApprove();
			approve.setId(id);
			approve.setPartsId(partsId);
			approve.setPartsName(partsName);
			approve.setApproveResult(process_instance_result);
			approve.setApproveStatus(process_instance_type);
			approve.setPartsCodes(codes);
			approve.setNumber(number);
			approve.setSpecification(specification);
			approve.setPartsStatus(status);
			approve.setSpecification(specification);
			approve.setImage(image);
			approve.setOperator(operator);
			approve.setRemark(remark);
			approve.setCreateTime(operation_time);
			approve.setUpdateTime(update_time);
			System.out.println(approve);
			parts.add(approve);

		}
		return parts;
	}
}
