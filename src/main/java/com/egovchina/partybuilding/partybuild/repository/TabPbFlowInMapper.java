package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TabPbFlowInMapper {

    int deleteByPrimaryKey(Long flowInId);

    int insert(TabPbFlowIn record);

    int insertSelective(TabPbFlowIn record);

    TabPbFlowIn selectByPrimaryKey(Long flowInId);

    int updateByPrimaryKeySelective(TabPbFlowIn record);

    int updateByPrimaryKey(TabPbFlowIn record);

    int updateByFlowOutIdKeySelective(TabPbFlowIn record);

    /**
     * 查询流入党员列表
     *
     * @param
     * @return
     */
    List<FlowInMemberVO> selectListByFlowInVo(FlowInMemberQueryBean flowInMemberQueryBean);

    /**
     * 流入党员详情查询
     *
     * @param flowInId
     * @return
     */
    FlowInMemberVO selectFlowInVoByFlowId(Long flowInId);

    /**
     * 根据流出外键查询流入主键
     *
     * @param flowInId
     * @return
     */
    Long getFlowOutIdByFlowInId(Long flowInId);

    /**
     * 用户表 流动状态设置结束流动 清空流入组织联系人和电话、流出组织联系人和电话
     *
     * @param userId
     * @return
     */
    int cancelSysUserFlowStaus(Long userId);
}