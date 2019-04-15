package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduTrainDynamic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTrainDynamicMapper {
    int deleteByPrimaryKey(Long dynamicId);

    int insert(TabPbEduTrainDynamic record);

    int insertSelective(TabPbEduTrainDynamic record);

    TabPbEduTrainDynamic selectByPrimaryKey(Long dynamicId);

    int updateByPrimaryKeySelective(TabPbEduTrainDynamic record);

    int updateByPrimaryKey(TabPbEduTrainDynamic record);

    List<TabPbEduTrainDynamic> selectiveList(TabPbEduTrainDynamic dynamic);
}