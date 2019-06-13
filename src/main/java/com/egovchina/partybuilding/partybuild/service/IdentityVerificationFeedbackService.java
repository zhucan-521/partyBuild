package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.IdentityVerificationFeedbackDTO;
import com.egovchina.partybuilding.partybuild.vo.IdentityVerificationFeedbackVO;

import java.util.List;

/**
 * 身份核查反馈service接口
 *
 * @author Zhang Fan
 **/
public interface IdentityVerificationFeedbackService {

    /**
     * 添加身份核查反馈
     *
     * @param identityVerificationFeedbackDTO 身份核查反馈dto
     * @return
     */
    int addIdentityVerificationFeedback(IdentityVerificationFeedbackDTO identityVerificationFeedbackDTO);

    /**
     * 根据党员查询身份核查反馈VO列表
     *
     * @param userId 党员id
     * @param page   分页实体
     * @return
     */
    List<IdentityVerificationFeedbackVO> selectIdentityVerificationFeedbackVOByUserId(Long userId, Page page);

    /**
     * 根据id查询身份核查反馈VO详情
     *
     * @param id 数据id
     * @return
     */
    IdentityVerificationFeedbackVO selectIdentityVerificationFeedbackVOById(Long id);
}
