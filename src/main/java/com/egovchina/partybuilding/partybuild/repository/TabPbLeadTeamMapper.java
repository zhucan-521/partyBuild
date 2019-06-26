package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.LeadTeamQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamVO;
import org.apache.ibatis.annotations.Param;
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
    Boolean chechLeadTeamIsExist(Long sessionYear, Long orgId);

    /**
     * 如果当前组织传入的领导班子为当届，那么就将当前组织以前的领导班子设置为往届
     *
     * @param orgId 组织id
     * @param leadTeamId 领导班子id
     * @return
     */
    int setPreviousLeadTeamForThePast(@Param("orgId") Long orgId, @Param("leadTeamId") Long leadTeamId);

    /**
     * 获取领导班子id
     *
     * @param orgId 组织id
     * @return
     */
    Long selectLeadTeamIdByOrgId(Long orgId);

}