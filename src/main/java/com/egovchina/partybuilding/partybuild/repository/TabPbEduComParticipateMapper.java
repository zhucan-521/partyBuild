package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduComParticipate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduComParticipateMapper extends BaseMapper<TabPbEduComParticipate> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TabPbEduComParticipate record);

    /**
     * 查询list数据
     * @param record
     * @return
     */
    List<TabPbEduComParticipate> selectData(TabPbEduComParticipate record);

    int updateByPrimaryKeySelective(TabPbEduComParticipate record);

    int updateByPrimaryKey(TabPbEduComParticipate record);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int flagDelete(Long id);

    /**
     * 改变审核状态
     * @param record
     * @return
     */
    int changeState(TabPbEduComParticipate record);
}