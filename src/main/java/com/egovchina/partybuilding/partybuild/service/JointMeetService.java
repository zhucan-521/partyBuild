package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.dto.JointMeetDTO;
import com.egovchina.partybuilding.partybuild.dto.JointMeetOrgDTO;
import com.egovchina.partybuilding.partybuild.entity.JointMeetOrgQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.egovchina.partybuilding.partybuild.vo.JointMeetOrgVO;
import com.egovchina.partybuilding.partybuild.vo.JointMeetVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/29
 */
public interface JointMeetService {

    /**
     * 通过orgId添加成员组织, 如果联席会组织已存在则将成员附加
     * 否则将创建联席会组织, 然后将成员附加
     *
     * @param jointMeetDto 联席会组织信息及成员信息, 如果没有可不传。 联席会组织成员列表为必传
     * @return 返回联席会主表信息
     */
    Long addJointMeet(JointMeetDTO jointMeetDto);

    /**
     * 通过党建联席会主键添加成员
     *
     * @param jointMeetOrgs 党建联席会主键id, 成员列表
     * @return 添加是否成功的消息
     */
    ReturnEntity addJointMeetOrgList(List<JointMeetOrgDTO> jointMeetOrgs);

    /**
     * 通过主键逻辑删除, 同时删除关联表数据
     *
     * @param jointMeet 主键
     * @return 删除结果
     */
    void deleteJointMeet(TabPbJointMeet jointMeet);

    /**
     * 通过主键删除明细表数据
     *
     * @param jointMeetOrg
     * @return
     */
    void deleteJointMeetOrg(TabPbJointMeetOrg jointMeetOrg);

    /**
     * 更新主表数据
     *
     * @param meet
     * @return
     */
    int updateJointMeet(JointMeetDTO meet);

    /**
     * 更新关联表数据
     *
     * @param jointMeetOrgDTO
     * @return
     */
    int updateJointMeetOrg(JointMeetOrgDTO jointMeetOrgDTO);

    /**
     * 查询主表数据, 以列表的形式返回, 并分页
     *
     * @param meet
     * @return
     */
    PageInfo<JointMeetVO> getJointMeetList(TabPbJointMeet meet, Page page);

    /**
     * 查询关联表数据, 以列表的形式返回
     *
     * @param jointMeetQueryBean
     * @return
     */
    PageInfo<JointMeetOrgVO> getJointMeetOrgList(JointMeetOrgQueryBean jointMeetQueryBean, Page page);
}
