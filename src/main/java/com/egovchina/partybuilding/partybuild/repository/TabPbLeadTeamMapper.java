package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.LeadTeamQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbLeadTeamMapper {

    /**
     * 新增班子
     *
     * @param tabPbLeadTeam
     * @return
     */
    int insertSelective(TabPbLeadTeam tabPbLeadTeam);

    /**
     * 根据主键修改选择的key（不为空）
     *
     * @param tabPbLeadTeam
     * @return
     */
    int updateByPrimaryKeySelective(TabPbLeadTeam tabPbLeadTeam);

    /**
     * 根据条件查询班子vo列表
     *
     * @param queryBean
     * @return
     */
    List<LeadTeamVO> selectLeadTeamVOByCondition(LeadTeamQueryBean queryBean);

    /**
     * 根据id查询班子详情
     *
     * @param leadTeamId id
     * @return
     */
    LeadTeamVO selectLeadTeamVOById(Long leadTeamId);

    /**
     * 根据班子id修正班子人数
     *
     * @param leadTeamId 班子id
     */
    void correctTheNumberOfTeamsAccordingToTheTeamId(Long leadTeamId);

    /**
     * 判断新增的领导班子在数据库中是否已经存在
     *
     * @param sessionYear
     * @param orgId
     * @return
     */
    List<TabPbLeadTeam> chechLeadTeamIsExist(Long sessionYear, Long orgId);
}