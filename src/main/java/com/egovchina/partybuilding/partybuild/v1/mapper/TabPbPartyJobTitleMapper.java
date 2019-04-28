package com.egovchina.partybuilding.partybuild.v1.mapper;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyJobTitle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tabPbPartyJobTitleMapper")
public interface TabPbPartyJobTitleMapper {
    int deleteByPrimaryKey(Long jobTitleId);

    int insert(TabPbPartyJobTitle record);

    int insertSelective(TabPbPartyJobTitle record);

    TabPbPartyJobTitle selectByPrimaryKey(Long jobTitleId);

    int updateByPrimaryKeySelective(TabPbPartyJobTitle record);

    int updateByPrimaryKey(TabPbPartyJobTitle record);

    /**
     * desc: 批量添加
     *
     * @param list TabPbPartyJobTitle集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:30
     **/
    int batchInsert(List<TabPbPartyJobTitle> list);

    /**
     * desc: 批量修改
     *
     * @param list TabPbPartyEducation集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:37
     **/
    int batchUpdate(List<TabPbPartyJobTitle> list);

}