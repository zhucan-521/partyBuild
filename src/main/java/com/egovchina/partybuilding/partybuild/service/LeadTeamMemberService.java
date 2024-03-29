package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.CommunityPartTimeMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyMemberSecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.vo.CommunityPartTimeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberListVO;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.PartySecretarysVO;

import java.util.List;

/**
 * @Author GuanYingxin
 **/

public interface LeadTeamMemberService {

    /**
     * 根据id逻辑删除领导班子成员
     *
     * @param memberId 成员id
     * @return
     */
    int logicDeleteLeadTeamMemberById(Long memberId);

    /**
     * 根据班子ID查询班子成员列表
     *
     * @param leadTeamId 领导班子id
     * @param page       分页参数
     * @return
     */
    List<LeadTeamMemberListVO> selectLeadTeamMemberVOListByLeadTeamId(Long leadTeamId, Page page);

    /**
     * 根据id查询班子成员信息
     *
     * @param memberId id
     * @return
     */
    LeadTeamMemberVO selectLeadTeamMemberVOById(Long memberId);

    /**
     * 根据条件查询班子成员列表
     *
     * @param queryBean 查询实体
     * @param page      分页实体
     * @return
     */
    List<LeadTeamMemberVO> selectLeadTeamMemberVOListByCondition(LeadTeamMemberQueryBean queryBean, Page page);

    /**
     * 新增领导班子成员
     *
     * @param leadTeamMemberDTO 领导班子成员
     * @return
     */
    int insertLeadTeamMember(LeadTeamMemberDTO leadTeamMemberDTO);

    /**
     * 修改领导班子成员
     *
     * @param leadTeamMemberDTO 领导班子成员
     * @return
     */
    int updateLeadTeamMember(LeadTeamMemberDTO leadTeamMemberDTO);

    /**
     * 根据条件查询社区兼职委员VO列表
     *
     * @param queryBean 查询实体
     * @param page      分页对象
     * @return
     */
    List<CommunityPartTimeMemberVO> selectCommunityPartTimeMemberVOListByCondition(CommunityPartTimeMemberQueryBean queryBean, Page page);

    /**
     * 列表查询书记
     *
     * @param partyMemberSecretaryMemberQueryBean
     * @return
     */
    List<PartySecretarysVO> selectSecretaryList(PartyMemberSecretaryMemberQueryBean partyMemberSecretaryMemberQueryBean, Page page);

    /**
     * 获取领导班子id
     *
     * @param orgId 组织id
     * @return
     */
    Long getLeadTeamIdByCurrent(Long orgId);

}
