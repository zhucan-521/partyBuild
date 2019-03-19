package com.yizheng.partybuilding.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yizheng.partybuilding.entity.TabPbFamily;
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
}