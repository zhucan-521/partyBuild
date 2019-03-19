package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduPositionProject;

import java.util.List;

public interface TabPbEduPositionProjectMapper {
    int deleteByPrimaryKey(Long projectId);

    int insert(TabPbEduPositionProject record);

    int insertSelective(TabPbEduPositionProject record);

    TabPbEduPositionProject selectByPrimaryKey(Long projectId);

    int updateByPrimaryKeySelective(TabPbEduPositionProject record);

    int updateByPrimaryKey(TabPbEduPositionProject record);

    /**
     * 批量新增
     * @param eduPositionProjectList
     * @return
     */
    int batchInsert(List<TabPbEduPositionProject> eduPositionProjectList);

    /**
     * 根据基地id查询所有特色项目
     * @param positionId
     * @return
     */
    List<TabPbEduPositionProject> selectAllByPositionId(Long positionId);

    /**
     * 根据id批量逻辑删除特色项目
     * @param pendingRemoveProjectId
     * @return
     */
    int batchLogicDeleteById(List<Long> pendingRemoveProjectId);

    /**
     * 根据基地id批量逻辑删除
     * @param positionId
     * @return
     */
    int batchLogicDeleteByPositionId(Long positionId);
}