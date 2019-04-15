package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduCommunication;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduCommunicationMapper extends BaseMapper<TabPbEduCommunication> {
    int deleteByPrimaryKey(Long id);

    int insertAll(TabPbEduCommunication record);

    int insertSelective(TabPbEduCommunication record);

    /**
     * 根据类型查询list的
     * @param record
     * @return
     */
    List<TabPbEduCommunication> selectKey(TabPbEduCommunication record);

    int updateByPrimaryKeySelective(TabPbEduCommunication record);

    int updateByPrimaryKeyWithBLOBs(TabPbEduCommunication record);

    int updateByPrimaryKey(TabPbEduCommunication record);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int flagDelete(TabPbEduCommunication id);
}