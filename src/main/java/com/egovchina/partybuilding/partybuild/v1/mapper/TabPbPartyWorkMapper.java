package com.egovchina.partybuilding.partybuild.v1.mapper;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyWork;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tabPbPartyWorkMapper")
public interface TabPbPartyWorkMapper {
    int deleteByPrimaryKey(Long workId);

    int insert(TabPbPartyWork record);

    int insertSelective(TabPbPartyWork record);

    TabPbPartyWork selectByPrimaryKey(Long workId);

    int updateByPrimaryKeySelective(TabPbPartyWork record);

    int updateByPrimaryKey(TabPbPartyWork record);

    /**
     * desc: 批量添加
     *
     * @param list TabPbPartyWork集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:31
     **/
    int batchInsert(List<TabPbPartyWork> list);

    /**
     * desc: 批量修改
     *
     * @param list TabPbPartyEducation集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:40
     **/
    int batchUpdate(List<TabPbPartyWork> list);

}