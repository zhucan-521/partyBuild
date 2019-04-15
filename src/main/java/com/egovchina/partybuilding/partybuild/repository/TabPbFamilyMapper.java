package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TabPbFamilyMapper extends BaseMapper<TabPbFamily> {
    int deleteByPrimaryKey(TabPbFamily family);

    int insertSelective(TabPbFamily record);

    TabPbFamily selectByPrimaryKey(Long relationId);

    int updateByPrimaryKeySelective(TabPbFamily record);

    int updateByPrimaryKey(TabPbFamily record);

    List<TabPbFamily> selectListPrimary(Long partyMemberId);

    TabPbFamily findById(Long relationId);

    /**
     * 逻辑删除党员的家庭成员
     * @param userId
     * @return
     */
    int tombstoneUser(Long userId);
}