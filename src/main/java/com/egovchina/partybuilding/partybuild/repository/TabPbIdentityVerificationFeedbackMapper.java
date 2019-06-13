package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbIdentityVerificationFeedback;
import com.egovchina.partybuilding.partybuild.vo.IdentityVerificationFeedbackVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbIdentityVerificationFeedbackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbIdentityVerificationFeedback record);

    int insertSelective(TabPbIdentityVerificationFeedback record);

    TabPbIdentityVerificationFeedback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbIdentityVerificationFeedback record);

    int updateByPrimaryKey(TabPbIdentityVerificationFeedback record);

    /**
     * 根据党员查询身份核查反馈VO列表
     *
     * @param userId 党员id
     * @return
     */
    List<IdentityVerificationFeedbackVO> selectIdentityVerificationFeedbackVOByUserId(Long userId);

    /**
     * 根据id查询身份核查反馈VO详情
     *
     * @param id 数据id
     * @return
     */
    IdentityVerificationFeedbackVO selectIdentityVerificationFeedbackVOById(Long id);
}