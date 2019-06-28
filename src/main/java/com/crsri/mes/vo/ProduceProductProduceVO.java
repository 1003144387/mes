package com.crsri.mes.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生产产品装配的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceProductProduceVO {

	private String id;

    private String productId;

    private String productName;

    private String partsCode;

    private String componentCode;

    private String produceOperator;
    
    private String produceImage;

    private String produceRemark;
    

}
