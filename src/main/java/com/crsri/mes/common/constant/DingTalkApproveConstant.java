package com.crsri.mes.common.constant;

/**
 * 钉钉审批相关的常量
 * 
 * @author 2011102394
 *
 */
public class DingTalkApproveConstant {

	public static final String PROCESS_INSTANCE_URL = "https://eco.taobao.com/router/rest";

	/**
	 * 审批实例变化的类型
	 * 
	 * @author 2011102394
	 *
	 */
	public interface processInstanceType {
		/**
		 * 审批实例开始的标识
		 */
		Integer START_VALUE = 0;
		/**
		 * 审批实例结束的标识
		 */
		Integer FINISHE_VALUE = 1;
		/**
		 * 审批实例撤销的标识
		 */
		Integer TERMINATE_VALUE = 2;

		/**
		 * 审批开始
		 */
		String START = "start";
		/**
		 * 审批结束
		 */
		String FINISHE = "finish";
		/**
		 * 审批撤销
		 */
		String TERMINATE = "terminate";
	}

	/**
	 * 审批实例的状态
	 * 
	 * @author 2011102394
	 *
	 */
	public interface processInstanceResult {
		Integer DEFAULT_VALUE = 0;
		String AGREE = "agree";
		String REFUSE = "refuse";
		Integer AGREE_VALUE = 1;
		Integer REFUSE_VALUE = 2;
	}

	/***
	 * 审批事件回调类型
	 * 
	 * @author 2011102394
	 *
	 */
	public interface callBackEventType {

		/**
		 * 审批任务的开始，结束，转交
		 */
		String BPMS_TASK_CHANGE = "bpms_task_change";

		/**
		 * 审批实例的开始，结束
		 */
		String BPMS_INSTANCE_CHANGE = "bpms_instance_change";

	}

	/**
	 * 审批的状态
	 * 
	 * @author 2011102394
	 *
	 */
	public interface approveStatus {

		/**
		 * 审批正在进行
		 */
		Integer APPROVE_RUNNING = 0;

		/**
		 * 审批已结束
		 */
		Integer APPROVE_FINISHED = 1;
	}

	/**
	 * 审批的结果
	 * 
	 * @author 2011102394
	 *
	 */
	public interface approveResult {

		/**
		 * 进行中的审批
		 */
		Integer PROCEEDING = 0;
		/**
		 * 审批通过
		 */
		Integer AGREE = 1;
		/**
		 * 审批拒绝
		 */
		Integer REFUSE = 2;

	}

//	/**
//	 * 审批流的编号（测试）
//	 * 
//	 * @author 2011102394
//	 *
//	 */
//	public interface processCode {
//		/**
//		 * 来料检验审批的processCode值
//		 */
//		String PRODUCE_PARTS_INSPECTION_CODE = "PROC-EFYJYA3W-3JYXZXOBPAPDT1J702U12-WJJVG3LJ-D";
//		/**
//		 * 物品入库审批的processCode值
//		 */
//		String GOODS_STOCK_IN_PROCESS_CODE = "PROC-0KYJJ30V-QSBYEJ2PR1JSO2KXEXTD3-M38UGOLJ-Q";
//		/**
//		 * 物品出库审批的processCode值
//		 */
//		String GOODS_STOCK_OUT_PROCESS_CODE = "PROC-JFYJSJGV-Z3EYBHSHQ0XEV36PMYH12-BC1LWPLJ-H";
//		/**
//		 * 部件三防审批的processCode值
//		 */
//		String PRODUCE_PARTS_DEFEND_PROCESS_CODE = "PROC-5FYJPUKV-K2EYUCNQRXB9H0XDRJW13-M9VTDQLJ-Q";
//		/**
//		 * 部件软件灌装及校准的processCode值
//		 */
//		String PRODUCE_PARTS_SOFT_INSTALL_PROCESS_CODE = "PROC-0KYJ80KV-LCFYQ2XUOP8762BYH0VI2-68DSKVLJ-O";
//		/**
//		 * 部件功能检验审批的processCode值
//		 */
//		String PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_CODE = "PROC-0KYJ80KV-Z5JYK67ZPVMLR3BYWMP13-C87G3XLJ-2";
//		/**
//		 * 生产组件装配审批的processCode值
//		 */
//		String PRODUCE_COMPONENT_PRODUCE_PROCESS_CODE = "PROC-FFYJAWEV-6O7YRI2FQ3O832GGWHOK2-AA5HWFLJ-4";
//		/**
//		 * 生产组件检验审批的processCode值
//		 */
//		String PRODUCE_COMPONENT_INSPECTION_PROCESS_CODE = "PROC-FFYJ2ZIV-R4KYUVYBT72GM7M6RBWT1-1VQZGXLJ-8";
//		/**
//		 * 生产产品装配审批的processCode值
//		 */
//		String PRODUCE_PRODUCT_PRODUCE_PROCESS_CODE = "PROC-7KYJMCPV-6ALYGHWWRF60D2FB0VAD3-AR1J70MJ-T";
//		/**
//		 * 生产产品检验审批的processCode值
//		 */
//		String PRODUCE_PRODUCT_INSPECTION_PROCESS_CODE = "PROC-JFYJ09RV-4AMYNVX6N2VMJC9TUUOL1-WH9TD0MJ-1";
//
//	}

	/**
	 * 审批流的编号（生产环境）
	 * 
	 * @author 2011102394
	 *
	 */
	public interface processCode {
		/**
		 * 来料检验审批的processCode值
		 */
		String PRODUCE_PARTS_INSPECTION_CODE = "PROC-JFYJ2UPV-U7YYXEY1NHCNOCWZUZSO1-1I5Q5HMJ-3";
		/**
		 * 物品入库审批的processCode值
		 */
		String GOODS_STOCK_IN_PROCESS_CODE = "PROC-ZJYJZRHV-B6YYCBXHT5D9IZAPMN5U1-65BB7HMJ-3";
		/**
		 * 物品出库审批的processCode值
		 */
		String GOODS_STOCK_OUT_PROCESS_CODE = "PROC-7KYJ42PV-L6YYFR4JUVG9ITYD5MAT1-HLE97HMJ-6";
		/**
		 * 部件三防审批的processCode值
		 */
		String PRODUCE_PARTS_DEFEND_PROCESS_CODE = "PROC-JFYJ09RV-I7YYGLAAQMM6V2TZ3M0Z1-2A9Z5HMJ-2";
		/**
		 * 部件软件灌装及校准的processCode值
		 */
		String PRODUCE_PARTS_SOFT_INSTALL_PROCESS_CODE = "PROC-ZJYJZRHV-B6YYGH6GTL0QE2ACXJ4H2-VZ9X5HMJ-5";
		/**
		 * 部件功能检验审批的processCode值
		 */
		String PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_CODE = "PROC-FFYJGNXU-1GUY1BJGOJP0G13TJV6Z2-SE2V5HMJ-Y";
		/**
		 * 生产组件装配审批的processCode值
		 */
		String PRODUCE_COMPONENT_PRODUCE_PROCESS_CODE = "PROC-FFYJ5P8V-T6UY7H3CNYX5927G2HSQ1-S0ZS5HMJ-S";
		/**
		 * 生产组件检验审批的processCode值
		 */
		String PRODUCE_COMPONENT_INSPECTION_PROCESS_CODE = "PROC-ZJYJ9SFV-K7YY9IAGN56JAA1I6FKT1-P87D7HMJ-7";
		/**
		 * 生产产品装配审批的processCode值
		 */
		String PRODUCE_PRODUCT_PRODUCE_PROCESS_CODE = "PROC-SIYJCRSV-A6UYKUIENUARRU8XGICU1-4FXE7HMJ-8";
		/**
		 * 生产产品检验审批的processCode值
		 */
		String PRODUCE_PRODUCT_INSPECTION_PROCESS_CODE = "PROC-5FYJ9A6W-I2VYNWQUMRVEX74Q2EBP1-BHLG7HMJ-E";
		/**
		 * 生产产品发货的processCode值
		 */
		String PRODUCE_PRODUCT_SHIP_PROCESS_CODE = "PROC-JFYJFWPV-0DL2ZNIQ56X3RAWRC6D02-U59NQZRJ-B5";
		

	}
//	/**
//	 * 各项审批的抄送人列表
//	 * 
//	 * @author 2011102394
//	 *
//	 */
//	public interface ccList {
//		/**
//		 * 生产部件 来料检验抄送列表
//		 */
//		String PRODUCE_PARTS_INSPECTION_CC_LIST = "manager6122";
//		/**
//		 * 物品入库审批的抄送人列表
//		 */
//		String GOODS_STOCK_IN_APPROVE_CC_LIST = "manager6122";
//		/**
//		 * 物品出库审批的抄送人列表
//		 */
//		String GOODS_STOCK_OUT_APPROVE_CC_LIST = "manager6122";
//		/**
//		 * 生产部件三防审批的抄送人列表
//		 */
//		String PRODUCE_PARTS_DEFEND_CC_LIST = "manager6122";
//		/**
//		 * 生产部件软件灌装和设备校准审批的抄送人列表
//		 */
//		String PRODUCE_PARTS_SOFT_INSTALL_CC_LIST = "manager6122";
//		/**
//		 * 生产部件功能检测审批抄送人列表
//		 */
//		String PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_CC_LIST = "manager6122";
//		/**
//		 * 生产组件装配审批的抄送人列表
//		 */
//		String PRODUCE_COMPONENT_PRODUCE_CC_LIST = "manager6122";
//		/**
//		 * 生产组件检验审批的抄送人列表
//		 */
//		String PRODUCE_COMPONENT_INSPECTION_CC_LIST = "manager6122";
//		/**
//		 * 生产产品装配审批的抄送人列表
//		 */
//		String PRODUCE_PRODUCT_PRODUCE_CC_LIST = "manager6122";
//		/**
//		 * 生产产品检验的抄送人列表
//		 */
//		String PRODUCE_PRODUCT_INSPECTION_CC_LIST = "manager6122";
//	}
//
//	/**
//	 * 各项审批的审批人列表
//	 * 
//	 * @author 2011102394
//	 *
//	 */
//	public interface approveList {
//		/**
//		 * 生产部件来料检验审批人列表
//		 */
//		String PRODUCE_PARTS_INSPECTION_APPROVES = "manager6122";
//		/**
//		 * 物品入库审批的审批人列表
//		 */
//		String GOODS_STOCK_IN_APPROVES = "manager6122";
//		/**
//		 * 物品出库审批的审批人
//		 */
//		String GOODS_STOCK_OUT_APPROVES = "manager6122";
//		/**
//		 * 部件三防审批的审批人
//		 */
//		String PRODUCE_PARTS_DEFEND_PROCESS_APPROVES = "manager6122";
//		/**
//		 * 部件软件灌装及校准审批审批人
//		 */
//		String PRODUCE_PARTS_SOFT_INSTALL_PROCESS_APPROVES = "manager6122";
//		/**
//		 * 生产部件功能检测审批审批人
//		 */
//		String PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_APPROVES = "manager6122";
//		/**
//		 * 生产组件装配审批的审批人
//		 */
//		String PRODUCE_COMPONENT_PRODUCE_APPEROVES = "manager6122";
//		/**
//		 * 生产组件检验审批的审批人列表
//		 */
//		String PRODUCE_COMPONENT_INSPECTION_APPROVES = "manager6122";
//		/**
//		 * 生产产品装配审批的审批人列表
//		 */
//		String PRODUCE_PRODUCT_PRODUCE_PROCESS_APPROVES = "manager6122";
//		/**
//		 * 生产 产品检验审批的审批人列表
//		 */
//		String PRODUCE_PRODUCT_INSPECTION_PROCESS_APPROVES = "manager6122";
//	}
}
