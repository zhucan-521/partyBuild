package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterCancelDTO;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterDTO;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegisterQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositiveRegistMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
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

import java.util.List;
import java.util.Optional;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author YangYingXiang on 2018/11/29
 */
@Service("positiveRegistService")
public class PositiveRegisterServiceImpl implements PositiveRegisterService {

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private TabPbPositiveRegistMapper tabPbPositiveRegistMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Transactional
    @Override
    public int insertPositiveRegister(PositiveRegisterDTO positiveRegisterDTO) {

        boolean exist = Optional.ofNullable(tabPbPositiveRegistMapper.checkPartyMemberIsExistRegister(positiveRegisterDTO.getUserId()))
                .orElse(false);
        if (exist) {
            throw new BusinessDataCheckFailException("该党员已报道");
        }

        TabPbPositiveRegist tabPbPositiveRegist = generateTargetCopyPropertiesAndPaddingBaseField(
                positiveRegisterDTO, TabPbPositiveRegist.class, false);

        checkRegisterPartyMemberAndRegisterOrganizationEffectivenessAndPaddingData(tabPbPositiveRegist);
        //已报到
        tabPbPositiveRegist.setRevokeTag(Byte.valueOf("1"));

        int judgment = tabPbPositiveRegistMapper.insert(tabPbPositiveRegist);
        if (judgment > 0) {
            Long userId = tabPbPositiveRegist.getUserId();
            //添加标签
            userTagService.addUserTag(userId, UserTagType.REPORT);
            //同步党员表字段
            tabPbPositiveRegistMapper.synchronizePartyMemberTableReportColumns(userId, tabPbPositiveRegist.getDeptId());
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
        tabPbPositiveRegist.setRevokeTag(Byte.valueOf("2"));
        int judgment = tabPbPositiveRegistMapper.updateById(cancelEntity);
        if (judgment > 0) {
            Long userId = tabPbPositiveRegist.getUserId();
            //删除标签
            userTagService.delete(userId, UserTagType.REPORT);
            //同步党员表
            tabPbPositiveRegistMapper.synchronizePartyMemberTableReportColumns(userId, -1L);
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
            //同步党员表
            tabPbPositiveRegistMapper.synchronizePartyMemberTableReportColumns(userId, -1L);
        }
        return judgment;
    }

    /**
     * 检查报到党员和报到组织有效性和填充数据
     *
     * @param tabPbPositiveRegist 报到信息实体
     */
    private void checkRegisterPartyMemberAndRegisterOrganizationEffectivenessAndPaddingData(TabPbPositiveRegist tabPbPositiveRegist) {
        TabPbPositiveRegist generated = tabPbPositiveRegistMapper.aggregateGeneratePartyMemberRegisterData(tabPbPositiveRegist.getUserId());
        if (generated == null) {
            throw new BusinessDataNotFoundException("在职党员数据不存在");
        }
        BeanUtil.copyPropertiesIgnoreNull(generated, tabPbPositiveRegist);
        SysDept sysDept = tabSysDeptMapper.selectAloneByPrimaryKey(tabPbPositiveRegist.getDeptId());
        if (sysDept == null) {
            throw new BusinessDataNotFoundException("报到党组织数据不存在");
        }
        if (!sysDept.ifBranch()) {
            throw new BusinessDataCheckFailException("报到党组织非支部");
        }
    }

}
