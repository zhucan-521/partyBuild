package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     * @param params
     * @return
     */
    List<TabPbFlowInDto> selectListByFlowInDto(Map<String, Object> params);


    /**
     * 查询流入党员列表
     * @param
     * @return
     */
    List<FlowInMemberVO> selectListByFlowInVo(FlowInMemberQueryBean flowInMemberQueryBean);

    /**
     * 流入党员详情查询
     * @param flowInId
     * @return
     */
    TabPbFlowInDto selectFlowInByFlowId(Long flowInId);



    /**
     * 流入党员详情查询
     * @param flowInId
     * @return
     */
    FlowInMemberVO selectFlowInVoByFlowId(Long flowInId);


}