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

import com.crsri.mes.entity.ProduceStockInApprove;
import com.crsri.mes.entity.ProduceStockOutApprove;

public class ProducePartsImportUtil {

	public static List<ProduceStockInApprove> getPartsStockInList(File file)
			throws IOException, InvalidFormatException {
		// excel的解析步骤
		// 1、创建工作簿对象
		Workbook workbook = WorkbookFactory.create(file);
		// 2、获取sheel遍历
		Sheet partsSheet = workbook.getSheetAt(0);
		// 3、获取最后一行的行号（从0开始）row
		int lastRowNum = partsSheet.getLastRowNum();
		// 4、从第二行开始，进行单元格的遍历
		List<ProduceStockInApprove> approves = new ArrayList<>();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = partsSheet.getRow(i);
			Cell cell0 = row.getCell(0);
			String id = null;
			if (cell0 != null) {
				id = cell0.getStringCellValue();
			}
			Cell cell1 = row.getCell(1);
			Integer goodsType = null;
			if (cell1 != null) {
				goodsType = (int) (row.getCell(1).getNumericCellValue());
			}
			Cell cell2 = row.getCell(2);
			String partsName = null;
			if (cell2 != null) {
				partsName = row.getCell(2).getStringCellValue();
			}
			Cell cell3 = row.getCell(3);
			String partsId = null;
			if (cell3 != null) {
				partsId = cell3.getStringCellValue();
			}
			Cell cell4 = row.getCell(4);
			String codes = null;
			if (cell4 != null) {
				codes = cell4.getStringCellValue();
			}
			Cell cell5 = row.getCell(5);
			Integer number = null;
			if (cell5 != null) {
				number = (int) (row.getCell(5).getNumericCellValue());
			}
			Cell cell6 = row.getCell(6);
			String position = null;
			if (cell6 != null) {
				position = cell6.getStringCellValue();
			}
			Cell cell7 = row.getCell(7);
			String image = null;
			if (cell7 != null) {
				image = cell7.getStringCellValue();
			}
			Cell cell8 = row.getCell(8);
			String remark = null;
			if (cell8 != null) {
				remark = cell8.getStringCellValue();
			}
			Cell cell9 = row.getCell(9);
			String operator = null;
			if (cell9 != null) {
				operator = cell9.getStringCellValue();
			}
			Cell cell10 = row.getCell(10);
			Integer instance_type = null;
			if (cell10 != null) {
				instance_type = (int) (row.getCell(10).getNumericCellValue());
			}
			Cell cell11 = row.getCell(11);
			Integer instance_result = null;
			if (cell11 != null) {
				instance_result = (int) (row.getCell(11).getNumericCellValue());
			}
			Cell cell12 = row.getCell(12);
			Date cerate_time = null;
			if (cell12 != null) {
				cerate_time = row.getCell(12).getDateCellValue();
			}
			Cell cell13 = row.getCell(13);
			Date update_time = null;
			if (cell13 != null) {
				update_time = row.getCell(13).getDateCellValue();
			}
			ProduceStockInApprove approve = new ProduceStockInApprove();

			approve.setApproveResult(instance_result);
			approve.setApproveStatus(instance_type);
			approve.setCreateTime(cerate_time);
			approve.setImage(image);
			approve.setNumber(number);
			approve.setOperator(operator);
			approve.setPosition(position);
			approve.setRemark(remark);
			approve.setUpdateTime(update_time);
			approve.setId(id);
			approve.setGoodsType(goodsType);
			approve.setTypeName(partsName);
			approve.setCodes(codes);
			approves.add(approve);
		}
		return approves;

	}

	/**
	 * 导入出库记录
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public static List<ProduceStockOutApprove> getPartsStockOutList(File file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// excel的解析步骤
		// 1、创建工作簿对象
		Workbook workbook = WorkbookFactory.create(file);
		// 2、获取sheel遍历
		Sheet partsSheet = workbook.getSheetAt(0);
		// 3、获取最后一行的行号（从0开始）row
		int lastRowNum = partsSheet.getLastRowNum();
		// 4、从第二行开始，进行单元格的遍历
		List<ProduceStockOutApprove> approves = new ArrayList<>();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = partsSheet.getRow(i);
			Cell cell0 = row.getCell(0);
			String id = null;
			if (cell0 != null) {
				id = cell0.getStringCellValue();
			}
			Cell cell1 = row.getCell(1);
			Integer goodsType = null;
			if (cell1 != null) {
				goodsType = (int) (row.getCell(1).getNumericCellValue());
			}
			Cell cell2 = row.getCell(2);
			String partsName = null;
			if (cell2 != null) {
				partsName = row.getCell(2).getStringCellValue();
			}
			Cell cell4 = row.getCell(4);
			String codes = null;
			if (cell4 != null) {
				codes = cell4.getStringCellValue();
			}
			Cell cell5 = row.getCell(5);
			Integer number = null;
			if (cell5 != null) {
				number = (int) (row.getCell(5).getNumericCellValue());
			}
			Cell cell6 = row.getCell(6);
			String image = null;
			if (cell6 != null) {
				image = cell6.getStringCellValue();
			}
			Cell cell7 = row.getCell(7);
			String remark = null;
			if (cell7 != null) {
				remark = cell7.getStringCellValue();
			}
			Cell cell8 = row.getCell(8);
			String operator = null;
			if (cell8 != null) {
				operator = cell8.getStringCellValue();
			}
			Cell cell9 = row.getCell(9);
			String receivePersion = null;
			if (cell9 != null) {
				receivePersion = cell9.getStringCellValue();
			}
			Cell cell10 = row.getCell(10);
			String receiveAddress = null;
			if (cell10 != null) {
				receiveAddress = row.getCell(10).getStringCellValue();
			}
			Cell cell11 = row.getCell(11);
			Integer instanceStatus = null;
			if (cell11 != null) {
				instanceStatus = (int) (row.getCell(11).getNumericCellValue());
			}
			Cell cell12 = row.getCell(12);
			Integer instanceResult = null;
			if (cell12 != null) {
				instanceResult = (int) (row.getCell(12).getNumericCellValue());
			}
			Cell cell13 = row.getCell(13);
			Date cerate_time = null;
			if (cell13 != null) {
				cerate_time = row.getCell(13).getDateCellValue();
			}
			Cell cell14 = row.getCell(14);
			Date update_time = null;
			if (cell14 != null) {
				update_time = row.getCell(14).getDateCellValue();
			}
			ProduceStockOutApprove approve = new ProduceStockOutApprove();

			approve.setApproveResult(instanceResult);
			approve.setApproveStatus(instanceStatus);
			approve.setCreateTime(cerate_time);
			approve.setImage(image);
			approve.setNumber(number);
			approve.setOperator(operator);
			approve.setRemark(remark);
			approve.setUpdateTime(update_time);
			approve.setId(id);
			approve.setGoodsType(goodsType);
			approve.setTypeName(partsName);
			approve.setCodes(codes);
			approve.setReceivePeople(receivePersion);
			approve.setReceiveAddress(receiveAddress);
			approves.add(approve);
		}
		return approves;
	}
}
