package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMsgNoticeDeptMapper {
    /**
     * 批量删除
     * @param noticeDeptRemoveList
     * @return
     */
    int deleteByPrimaryKey(List<Long> noticeDeptRemoveList);

    /**
     * 保存
     * @param record
     * @return
     */
    int insertSelective(TabPbMsgNoticeDept record);

    /**
     * 查询list
     * @param record
     * @return
     */
    List<TabPbMsgNoticeDept> selectList(TabPbMsgNoticeDept record);

    /**
     * 签收
     * @param record
     * @return
     */
    int updateSigning(TabPbMsgNoticeDept record);

    /**
     * 根据主键id查询数据
     * @param id
     * @return
     */
    TabPbMsgNoticeDept findByDept(Long id);

    /**
     * 根据主表id查询明细
     * @param noticeId
     * @return
     */
    List<TabPbMsgNoticeDept> selectDeptList(Long noticeId);
}