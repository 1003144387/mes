package com.crsri.mes.util.export;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crsri.mes.dao.ProduceComponentProcessMapper;
import com.crsri.mes.dao.ProducePartsInspectionApproveMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.dao.ProduceProductProcessMapper;
import com.crsri.mes.dao.ProduceStockInApproveMapper;
import com.crsri.mes.dao.ProduceStockOutApproveMapper;
import com.crsri.mes.entity.ProduceComponentProcess;
import com.crsri.mes.entity.ProducePartsInspectionApprove;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.entity.ProduceProductProcess;
import com.crsri.mes.entity.ProduceStockInApprove;
import com.crsri.mes.entity.ProduceStockOutApprove;
import com.crsri.mes.util.imports.PartsInspectionApproveExportUtil;
import com.crsri.mes.util.imports.ProduceComponentUtil;
import com.crsri.mes.util.imports.ProducePartsImportUtil;
import com.crsri.mes.util.imports.ProducePartsProcessExportUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportUtilTest {

	@Resource
	private ProducePartsInspectionApproveMapper inspectionApproveMapper;
	
	@Resource
	private ProducePartsProcessMapper partsProcessMapper;
	
	@Resource
	private ProduceStockInApproveMapper stockInApproveMapper;
	
	@Resource
	private ProduceStockOutApproveMapper stockOutApproveMapper;
	
	@Resource
	private ProduceComponentProcessMapper produceComponentProcessMapper;
	
	@Resource
	private ProduceProductProcessMapper produceProductProcessMapper;
	
	/**
	 * 导入生产部件来料检验信息
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	@Test
	public void savePartsInspectionBatch() throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        File file = new File("d:/export/parts_inspection.xlsx");
        List<ProducePartsInspectionApprove> parts = PartsInspectionApproveExportUtil.partsInput(file);
        for (ProducePartsInspectionApprove producePartsInspectionApprove : parts) {
        	inspectionApproveMapper.insertSelectiveBackUp(producePartsInspectionApprove);
		}
        System.out.println("导入生产部件来料检验信息成功");
    }
	
	
	/**
	 * 导入生产部件生产流程信息
	 */
	@Test
	public void savePartsProcessBatch() {
		File file = new File("d:/export/parts_qualified.xlsx");
		try {
			List<ProducePartsProcess> producePartsProcess = ProducePartsProcessExportUtil.getProducePartsProcess(file);
			int rowCount = partsProcessMapper.insertSelectiveBatchImport(producePartsProcess);
			System.out.println("导入生产部件生产流程信息成功");
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存生产入库审批的记录
	 */
	@Test
	public void saveProduceStockInApprove() {
		File file = new File("d:/export/parts_stock_in.xlsx");
		try {
			List<ProduceStockInApprove> partsStockInList = ProducePartsImportUtil.getPartsStockInList(file);
			for (ProduceStockInApprove item : partsStockInList) {
				stockInApproveMapper.insertSelectiveBackUp(item);
			}
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新生产部件中关于入库审批的相关记录
	 */
	@Test
	public void updateProducePartsProcessStockIn() {
		File file = new File("d:/export/parts_stock.xlsx");
		try {
			List<ProducePartsProcess> producePartsProcess = ProducePartsProcessExportUtil.getProducePartsStock(file);
			for (ProducePartsProcess item : producePartsProcess) {
				partsProcessMapper.updateByPrimaryKeySelective(item);
			}
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存生产出库审批的记录
	 */
	@Test
	public void saveProduceStockOutApprove() {
		File file = new File("d:/export/stock_out.xlsx");
		try {
			List<ProduceStockOutApprove> partsStockOutList = ProducePartsImportUtil.getPartsStockOutList(file);
			for (ProduceStockOutApprove item : partsStockOutList) {
				stockOutApproveMapper.insert(item);
			}
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  更新生产部件中关于出库审批的相关记录
	 */
	@Test
	public void updateProducePartsProcessStockOut() {
		File file = new File("d:/export/parts_stock_out.xlsx");
		try {
			List<ProducePartsProcess> producePartsProcess = ProducePartsProcessExportUtil.getProducePartsStockOut(file);
			for (ProducePartsProcess item : producePartsProcess) {
//				System.out.println(item);
				partsProcessMapper.updateByPrimaryKeySelective(item);
			}
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  更新生产组件中关于组件装配的相关记录
	 */
	@Test
	public void updateProduceComponent() {
		File file = new File("d:/export/component_produce.xlsx");
		try {
			List<ProduceComponentProcess> componentProcesses = ProduceComponentUtil.getComponentProcess(file);
			for (ProduceComponentProcess produceComponentProcess : componentProcesses) {
				produceComponentProcessMapper.updateByPrimaryKeySelective(produceComponentProcess);
			}
			System.out.println(componentProcesses.size());
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新组件入库情况
	 */
	@Test
	public void updateComponentStockIn() {
		List<ProduceStockInApprove> stockInApproves = stockInApproveMapper.getStockInHistory(1,null,null,null,null);
		for (ProduceStockInApprove produceStockInApprove : stockInApproves) {
			String[] codes = produceStockInApprove.getCodes().split(",");
			for(String code:codes) {
				ProduceComponentProcess  process = new ProduceComponentProcess();
				process.setId(code);
				process.setStockInApproveId(produceStockInApprove.getId());
				process.setStockInApproveOperator(produceStockInApprove.getOperator());
				process.setStockInApproveResult(produceStockInApprove.getApproveResult());
				process.setStockInApproveStatus(produceStockInApprove.getApproveStatus());
				process.setStockPosition(produceStockInApprove.getPosition());
				process.setStockStatus(0);
				process.setStockInApproveStartTime(produceStockInApprove.getCreateTime());
				process.setStockInApproveStopTime(produceStockInApprove.getUpdateTime());
				produceComponentProcessMapper.updateByPrimaryKeySelective(process);
			}
			
		}
		System.out.println(stockInApproves.size());
	}
	
	/**
	 * 更新组件出库记录
	 */
	@Test
	public void updateComponentStockOut() {
		List<ProduceStockOutApprove> stockOutApproves = stockOutApproveMapper.getStockOutHistory(1,null,null,null,null);
		for (ProduceStockOutApprove produceStockOutApprove : stockOutApproves) {
			String[] codes = produceStockOutApprove.getCodes().split(",");
			for(String code:codes) {
				ProduceComponentProcess  process = new ProduceComponentProcess();
				process.setId(code);
				process.setStockOutApproveId(produceStockOutApprove.getId());
				process.setStockOutApproveOperator(produceStockOutApprove.getOperator());
				process.setStockOutApproveResult(produceStockOutApprove.getApproveResult());
				process.setStockOutApproveStatus(produceStockOutApprove.getApproveStatus());
				process.setStockOutApproveStartTime(produceStockOutApprove.getCreateTime());
				process.setStockStatus(1);
				process.setStockOutApproveStopTime(produceStockOutApprove.getUpdateTime());
				process.setStockOutApproveType(0);
				produceComponentProcessMapper.updateByPrimaryKeySelective(process);
			}
			
		}
		System.out.println(stockOutApproves.size());
	}
	/**
	 * 更新产品入库情况
	 */
	@Test
	public void updateProductStockIn() {
		List<ProduceStockInApprove> stockInApproves = stockInApproveMapper.getStockInHistory(2,null,null,null,null);
		for (ProduceStockInApprove produceStockInApprove : stockInApproves) {
			String[] codes = produceStockInApprove.getCodes().split(",");
			for(String code:codes) {
				ProduceProductProcess  process = new ProduceProductProcess();
				process.setId(code);
				process.setStockInApproveId(produceStockInApprove.getId());
				process.setStockInApproveOperator(produceStockInApprove.getOperator());
				process.setStockInApproveResult(produceStockInApprove.getApproveResult());
				process.setStockInApproveStatus(produceStockInApprove.getApproveStatus());
				process.setStockStatus(0);
				process.setStockPosition(produceStockInApprove.getPosition());
				process.setStockInApproveStartTime(produceStockInApprove.getCreateTime());
				process.setStockInApproveStopTime(produceStockInApprove.getUpdateTime());
				produceProductProcessMapper.updateByPrimaryKeySelective(process);
			}
			
		}
		System.out.println(stockInApproves.size());
	}
	
	/**
	 * 更新产品出库记录
	 */
	@Test
	public void updateProductStockOut() {
		List<ProduceStockOutApprove> stockOutApproves = stockOutApproveMapper.getStockOutHistory(2,null,null,null,null);
		for (ProduceStockOutApprove produceStockOutApprove : stockOutApproves) {
			String[] codes = produceStockOutApprove.getCodes().split(",");
			for(String code:codes) {
				ProduceProductProcess  process = new ProduceProductProcess();
				process.setId(code);
				process.setStockOutApproveId(produceStockOutApprove.getId());
				process.setStockOutApproveOperator(produceStockOutApprove.getOperator());
				process.setStockOutApproveResult(produceStockOutApprove.getApproveResult());
				process.setStockOutApproveStatus(produceStockOutApprove.getApproveStatus());
				process.setStockOutApproveStartTime(produceStockOutApprove.getCreateTime());
				process.setStockStatus(1);
				process.setStockOutApproveStopTime(produceStockOutApprove.getUpdateTime());
				process.setStockOutApproveType(0);
				produceProductProcessMapper.updateByPrimaryKeySelective(process);
			}
			
		}
		System.out.println(stockOutApproves.size());
	}
	
}
















