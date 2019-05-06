package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyEducation;
import com.egovchina.partybuilding.partybuild.vo.PartyEducationVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tabPbPartyEducationMapper")
public interface TabPbPartyEducationMapper {
    int deleteByPrimaryKey(Long educationId);

    int insert(TabPbPartyEducation record);

    int insertSelective(TabPbPartyEducation record);

    TabPbPartyEducation selectByPrimaryKey(Long educationId);

    int updateByPrimaryKeySelective(TabPbPartyEducation record);

    int updateByPrimaryKey(TabPbPartyEducation record);

    /**
     * desc: 根据党员id查询该党员的学历信息
     *
     * @param userId 党员ID
     * @return list
     * @author FanYanGen
     * @date 2019/4/11 14:25
     **/
    List<PartyEducationVO> findAllByUserId(Long userId);

    /**
     * desc: 批量添加
     *
     * @param list TabPbPartyEducation集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:28
     **/
    int batchInsert(List<TabPbPartyEducation> list);

    /**
     * desc: 批量修改
     *
     * @param list TabPbPartyEducation集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:35
     **/
    int batchUpdate(List<TabPbPartyEducation> list);

}