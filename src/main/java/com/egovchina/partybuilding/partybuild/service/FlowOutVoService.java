package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.FlowOutMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.FlowOutMemberDTO;
import com.egovchina.partybuilding.partybuild.vo.FlowOutMemberVO;
import com.github.pagehelper.PageInfo;

public interface FlowOutVoService {

    /**
     * 流出党员列表条件查询
     * @param dto
     * @return
     */
    PageInfo<FlowOutMemberVO> getFlowOutVoList(FlowOutMemberQueryBean dto, Page page);


    /**
     * 登记流出党员信息
     * @param dto
     * @return 返回流出党员主键
     */
    int addFlowOutMemberDTO(FlowOutMemberDTO dto);


    /**
     * 修改流出党员
     * @param dto
     * @return
     */
    int updateFlowOutMember(FlowOutMemberDTO dto);


    /**
     * 单个查询详情
     * @param id
     * @return
     */
    FlowOutMemberVO getFlowOutMember(Long id);

    /**
     * 删除根据id
     * @param id
     * @return
     */
    int delete(Long id);


}
