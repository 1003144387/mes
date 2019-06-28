package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.RecordReply;
import com.crsri.mes.vo.RecordReplyVO;

public interface RecordReplyMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(RecordReply record);

	int insertSelective(RecordReply record);

	RecordReply selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RecordReply record);

	int updateByPrimaryKey(RecordReply record);

	/**
	 * 根据主题id获取回复记录列表
	 * 
	 * @param topicId
	 * @return
	 */
	List<RecordReply> queryByTopicId(@Param("topicId")Integer topicId);

	/**
	 * 根据parentId获取记录列表
	 * @param parentId
	 * @return
	 */
	List<RecordReplyVO> queryByParentId(@Param("parentId")Integer parentId);

	/**
	 * 根据主题id删除回复
	 * @param id
	 * @return
	 */
	int deleteByTopicId(@Param("topicId")Integer topicId);
}