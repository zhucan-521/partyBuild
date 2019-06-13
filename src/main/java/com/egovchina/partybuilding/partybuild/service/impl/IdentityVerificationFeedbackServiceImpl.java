package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.IdentityVerificationFeedbackDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbIdentityVerificationFeedback;
import com.egovchina.partybuilding.partybuild.repository.TabPbIdentityVerificationFeedbackMapper;
import com.egovchina.partybuilding.partybuild.service.IdentityVerificationFeedbackService;
import com.egovchina.partybuilding.partybuild.vo.IdentityVerificationFeedbackVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * 身份核查反馈service实现
 *
 * @author Zhang Fan
 **/
@Service("identityVerificationFeedbackService")
public class IdentityVerificationFeedbackServiceImpl implements IdentityVerificationFeedbackService {

    @Autowired
    private TabPbIdentityVerificationFeedbackMapper tabPbIdentityVerificationFeedbackMapper;

    @Override
    public int addIdentityVerificationFeedback(IdentityVerificationFeedbackDTO identityVerificationFeedbackDTO) {
        TabPbIdentityVerificationFeedback tabPbIdentityVerificationFeedback =
                generateTargetCopyPropertiesAndPaddingBaseField(identityVerificationFeedbackDTO,
                        TabPbIdentityVerificationFeedback.class, false);
        return tabPbIdentityVerificationFeedbackMapper.insertSelective(tabPbIdentityVerificationFeedback);
    }

    @Override
    public List<IdentityVerificationFeedbackVO> selectIdentityVerificationFeedbackVOByUserId(Long userId, Page page) {
        PageHelper.startPage(page);
        return tabPbIdentityVerificationFeedbackMapper.selectIdentityVerificationFeedbackVOByUserId(userId);
    }
}
