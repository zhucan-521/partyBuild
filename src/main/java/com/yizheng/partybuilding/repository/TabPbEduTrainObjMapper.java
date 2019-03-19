package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduTrainObj;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTrainObjMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByTrainId(Long trainId);

    int insert(TabPbEduTrainObj record);

    int insertSelective(TabPbEduTrainObj record);

    TabPbEduTrainObj selectByPrimaryKey(Long id);

    List<TabPbEduTrainObj> selectList(TabPbEduTrainObj trainObj);

    int updateByPrimaryKeySelective(TabPbEduTrainObj record);

    int updateByPrimaryKey(TabPbEduTrainObj record);

    void insertByBatch(List<TabPbEduTrainObj> attachmentTables);

    /**
     * 重置培训所有人员ademin字段为 否
     * @param trainId
     */
    void setAdeminFalse(Long trainId);
}