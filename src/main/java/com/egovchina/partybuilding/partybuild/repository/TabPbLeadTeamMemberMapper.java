package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.CommunityPartTimeMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyMemberSecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;
import com.egovchina.partybuilding.partybuild.vo.CommunityPartTimeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberListVO;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.PartySecretarysVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbLeadTeamMemberMapper {

    /**
     * 新增班子成员
     *
     * @param tabPbLeadTeamMember
     * @return
     */
    int insertSelective(TabPbLeadTeamMember tabPbLeadTeamMember);

    /**
     * 根据成员id查询成员信息
     *
     * @param memberId
     * @return
     */
    TabPbLeadTeamMember selectByPrimaryKey(Long memberId);

    /**
     * 根据主键修改班子成员信息
     *
     * @param tabPbLeadTeamMember
     * @return
     */
    int updateByPrimaryKeySelective(TabPbLeadTeamMember tabPbLeadTeamMember);

    /**
     * 根据班子id查询班子成员列表
     *
     * @param leadTeamId 班子id
     * @return
     */
    List<LeadTeamMemberListVO> selectTeamMemberVOListByTeamId(Long leadTeamId);

    /**
     * 根据id查询班子成员详情
     *
     * @param memberId id
     * @return
     */
    LeadTeamMemberVO selectLeadTeamMemberVOById(Long memberId);

    /**
     * 根据条件查询班子成员列表
     *
     * @param queryBean 查询实体
     * @return
     */
    List<LeadTeamMemberVO> selectLeadTeamMemberVOListByCondition(LeadTeamMemberQueryBean queryBean);

    /**
     * 根据班子id逻辑删除班子成员
     *
     * @param teamMember 班子id
     * @return
     */
    int logicDeleteByLeadTeamId(TabPbLeadTeamMember teamMember);

    /**
     * 成员已经存在在指定班子中
     *
     * @param leadTeamId 班子id
     * @param userId     用户id
     * @return
     */
    Boolean memberAlreadyExistsInTheTeam(Long leadTeamId, Long userId);

    /**
     * 根据条件查询社区兼职委员VO列表
     *
     * @param queryBean 查询实体
     * @return
     */
    List<CommunityPartTimeMemberVO> selectCommunityPartTimeMemberVOListByCondition(CommunityPartTimeMemberQueryBean queryBean);

    /**
     * 书记列表查询
     *
     * @param partyMemberSecretaryMemberQueryBean
     * @return
     */
    List<PartySecretarysVO> selectSecretaryVOList(PartyMemberSecretaryMemberQueryBean partyMemberSecretaryMemberQueryBean);

}