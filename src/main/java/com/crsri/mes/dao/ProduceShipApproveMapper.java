package com.crsri.mes.dao;

import com.crsri.mes.entity.ProduceShipApprove;

public interface ProduceShipApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceShipApprove record);

    int insertSelective(ProduceShipApprove record);

    ProduceShipApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceShipApprove record);

    int updateByPrimaryKey(ProduceShipApprove record);
}