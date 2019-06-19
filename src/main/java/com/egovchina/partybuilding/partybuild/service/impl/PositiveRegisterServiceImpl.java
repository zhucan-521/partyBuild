package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterCancelDTO;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterDTO;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegisterQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.repository.TabPbAdministrativeDivisionMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositiveRegistMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PositiveRegisterService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.util.UserTagType;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegisterVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author YangYingXiang on 2018/11/29
 */
@Service("positiveRegistService")
public class PositiveRegisterServiceImpl implements PositiveRegisterService {

    /**
     * 已报道
     */
    private static final byte REPORTED = 1;
    /**
     * 撤销
     */
    private static final byte CANCELED = 2;
    /**
     * 审核通过
     */
    private static final String PASS = "2";

    @Autowired
    private UserTagService userTagService;
    @Autowired
    private TabPbPositiveRegistMapper tabPbPositiveRegistMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;
    @Autowired
    private TabPbAdministrativeDivisionMapper tabPbAdministrativeDivisionMapper;

    @Transactional
    @Override
    public int insertPositiveRegister(PositiveRegisterDTO positiveRegisterDTO) {
        TabPbPositiveRegist tabPbPositiveRegist = generateTargetCopyPropertiesAndPaddingBaseField(
                positiveRegisterDTO, TabPbPositiveRegist.class, false);

        checkRegisterPartyMemberAndRegisterOrganizationEffectivenessAndPaddingData(tabPbPositiveRegist);

        int judgment = tabPbPositiveRegistMapper.insert(tabPbPositiveRegist);
        if (judgment > 0) {
            Long userId = tabPbPositiveRegist.getUserId();
            //添加标签
            userTagService.addUserTag(userId, UserTagType.REPORT);
            //维护附件
            iTabPbAttachmentService.intelligentOperation(positiveRegisterDTO.getAttachments(), tabPbPositiveRegist.getPositiveRegistId(), AttachmentType.POSITIVE);
        }
        return judgment;
    }

    @Override
    public List<PositiveRegisterVO> selectPositiveRegisterVOListByCondition(PositiveRegisterQueryBean positiveRegisterQueryBean,
                                                                            Page page) {
        PageHelper.startPage(page);
        return tabPbPositiveRegistMapper.selectPositiveRegisterMemberVOListByCondition(positiveRegisterQueryBean);
    }

    @Override
    public PositiveRegisterVO getPositiveRegisterVOById(Long positiveRegistId) {
        return tabPbPositiveRegistMapper.selectPositiveRegisterVOById(positiveRegistId);
    }

    @Override
    public int cancelPositiveRegister(PositiveRegisterCancelDTO positiveRegisterCancelDTO) {
        TabPbPositiveRegist tabPbPositiveRegist = tabPbPositiveRegistMapper.selectById(positiveRegisterCancelDTO.getPositiveRegistId());
        if (tabPbPositiveRegist == null) {
            throw new BusinessDataNotFoundException("党员报到数据不存在");
        }
        if (positiveRegisterCancelDTO.getRevokeDate().before(tabPbPositiveRegist.getRegistDate())) {
            throw new BusinessDataCheckFailException("报到撤销时间不能小于报到时间");
        }
        TabPbPositiveRegist cancelEntity = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(positiveRegisterCancelDTO, TabPbPositiveRegist.class, true);
        tabPbPositiveRegist.setRevokeTag(CANCELED);
        int judgment = tabPbPositiveRegistMapper.updateById(cancelEntity);
        if (judgment > 0) {
            Long userId = tabPbPositiveRegist.getUserId();
            //删除标签
            userTagService.delete(userId, UserTagType.REPORT);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int logicDeletePositiveRegisterById(Long positiveRegistId) {
        TabPbPositiveRegist tabPbPositiveRegist = tabPbPositiveRegistMapper.selectPositiveRegisterById(positiveRegistId);
        if (tabPbPositiveRegist == null) {
            throw new BusinessDataNotFoundException("党员报到数据不存在");
        }
        TabPbPositiveRegist delete = new TabPbPositiveRegist();
        delete.setPositiveRegistId(positiveRegistId);
        delete.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(delete);
        int judgment = tabPbPositiveRegistMapper.updateById(delete);
        if (judgment > 0) {
            Long userId = tabPbPositiveRegist.getUserId();
            //删除标签
            userTagService.delete(userId, UserTagType.REPORT);
        }
        return judgment;
    }

    /**
     * 检查报到党员和报到组织有效性和填充数据
     *
     * @param tabPbPositiveRegist 报到信息实体
     */
    private void checkRegisterPartyMemberAndRegisterOrganizationEffectivenessAndPaddingData(TabPbPositiveRegist tabPbPositiveRegist) {
        boolean reported = tabPbPositiveRegistMapper.checkPartyMemberToReportedInTheCommunity(tabPbPositiveRegist.getUserId(), tabPbPositiveRegist.getCommunityId());
        if (reported) {
            throw new BusinessDataCheckFailException("该党员已在该社区报到");
        }
        TabPbPositiveRegist generated = tabPbPositiveRegistMapper.aggregateGeneratePartyMemberRegisterData(tabPbPositiveRegist.getUserId());
        if (generated == null) {
            throw new BusinessDataNotFoundException("在职党员数据不存在");
        }
        BeanUtil.copyPropertiesIgnoreNull(generated, tabPbPositiveRegist);
        boolean exist = tabPbAdministrativeDivisionMapper.checkCommunityIsExist(tabPbPositiveRegist.getCommunityId());
        if (!exist) {
            throw new BusinessDataNotFoundException("报到社区不存在");
        }
        //已报到
        tabPbPositiveRegist.setRevokeTag(REPORTED);
        //审核人
        tabPbPositiveRegist.setProcessMan(UserContextHolder.getUserName());
        //审核时间
        tabPbPositiveRegist.setProcessTime(new Date());
        //审核结果-通过
        tabPbPositiveRegist.setProcessResult(PASS);
    }

}
