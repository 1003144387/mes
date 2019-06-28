package com.crsri.mes.vo;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

/**
 * 
 * 生产组件装配的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponentProduceVO {

	private String id;

    private String componentId;

    private String componentName;

    private String partsCode;

    private String produceOperator;

    private Date produceTime;
    
    private String produceImage;

    private String produceRemark;


}
