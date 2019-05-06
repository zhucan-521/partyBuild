package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.FlowInMemberDTO;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import com.github.pagehelper.PageInfo;

public interface FlowInService {


    /**
     * 列表查询
     *
     * @param
     * @param page
     * @return
     */
    PageInfo<FlowInMemberVO> getFlowInMemberList(FlowInMemberQueryBean flowInMemberQueryBean, Page page);

    /**
     * 根据流入id删除
     *
     * @param flowInId
     * @return
     */
    int deleteFlowInMember(Long flowInId);

    /**
     * 录入流入党员
     *
     * @param
     * @return
     */
    int acceptFlowInMember(FlowInMemberDTO flowInMemberDto);

    /**
     * 修改
     *
     * @param
     * @return
     */
    int updateFlowInDto(FlowInMemberDTO flowInMemberDto);

    /**
     * 返回登记
     *
     * @param
     * @return
     */
    int returnFlowInMember(FlowInMemberDTO flowInMemberDto);


    /**
     * 单个详情查询
     *
     * @param flowInId
     * @return
     */
    FlowInMemberVO getFlowInMeberVoById(Long flowInId);


}
