package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.DoubleCommentaryDTO;
import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.DoubleCommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDoubleCommentary;
import com.egovchina.partybuilding.partybuild.repository.TabPbDoubleCommentaryMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.DoubleCommentaryService;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.vo.CommentaryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * desc: 双述双评-服务接口实现
 * Created by FanYanGen on 2019/4/24 16:17
 */
@Transactional(rollbackFor = Exception.class)
@Service("commentaryService")
public class DoubleCommentaryServiceImpl implements DoubleCommentaryService {

    @Autowired
    private TabPbDoubleCommentaryMapper tabPbDoubleCommentaryMapper;

    @Autowired
    private ITabPbAttachmentService tabPbAttachmentService;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Override
    public int insertCommentary(DoubleCommentaryDTO doubleCommentaryDTO) {
        verificationInsert(doubleCommentaryDTO);
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(doubleCommentaryDTO, TabPbDoubleCommentary.class, false);
        int result = tabPbDoubleCommentaryMapper.insertSelective(tabPbDoubleCommentary);
        if (result > 0) {
            result += updatingFiles(doubleCommentaryDTO, tabPbDoubleCommentary.getCommentaryId());
        }
        return result;
    }

    @Override
    public int updateCommentary(DoubleCommentaryDTO doubleCommentaryDTO) {
        verificationUpdate(doubleCommentaryDTO);
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(doubleCommentaryDTO, TabPbDoubleCommentary.class, true);
        int result = tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
        if (result > 0) {
            result += updatingFiles(doubleCommentaryDTO, doubleCommentaryDTO.getCommentaryId());
        }
        return result;
    }

    @Override
    public CommentaryVO findCommentaryVOByCommentaryId(Long commentaryId) {
        return tabPbDoubleCommentaryMapper.selectByPrimaryKey(commentaryId);
    }

    @Override
    public PageInfo<CommentaryVO> findCommentaryVOWithConditions(CommentaryQueryBean commentaryQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(tabPbDoubleCommentaryMapper.selectWithConditions(commentaryQueryBean));
    }

    @Override
    public int deleteCommentary(Long commentaryId) {
        TabPbDoubleCommentary commentary = new TabPbDoubleCommentary();
        commentary.setCommentaryId(commentaryId);
        commentary.setDelFlag(CommonConstant.STATUS_DEL);
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(commentary, TabPbDoubleCommentary.class, true);
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
    }

    @Override
    public int verifyCommentary(DoubleCommentaryQueryBean doubleCommentaryQueryBean) {
        CommentaryVO commentaryVO = tabPbDoubleCommentaryMapper.selectByPrimaryKey(doubleCommentaryQueryBean.getCommentaryId());
        if (commentaryVO == null) {
            throw new BusinessDataNotFoundException("双述双评数据不存在");
        }
        TabPbDoubleCommentary commentary = new TabPbDoubleCommentary();
        commentary.setCommentaryId(commentaryVO.getCommentaryId());
        commentary.setCheckOrg(UserContextHolder.getOrgId());
        commentary.setCheckUser(UserContextHolder.getUserId());
        commentary.setCheckResult(doubleCommentaryQueryBean.getCheckResult());
        commentary.setCheckDesc(doubleCommentaryQueryBean.getCheckDesc());
        commentary.setCheckDate(new Date());
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(commentary, TabPbDoubleCommentary.class, true);
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
    }

    /**
     * desc: 数据校验提示
     *
     * @param doubleCommentaryDTO dto
     * @return void
     * @author FanYanGen
     * @date 2019/4/24 21:02
     **/
    private void verificationInsert(DoubleCommentaryDTO doubleCommentaryDTO) {
        final String planYear = doubleCommentaryDTO.getPlanYear();
        if (!tabSysDeptMapper.checkIsExistByOrgId(doubleCommentaryDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (tabPbDoubleCommentaryMapper.checkIsExistByPlanYear(planYear)) {
            throw new BusinessDataCheckFailException(String.format("该组织%s年度双述双评总结已存在", planYear));
        }
    }

    private void verificationUpdate(DoubleCommentaryDTO doubleCommentaryDTO) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(doubleCommentaryDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!tabPbDoubleCommentaryMapper.checkIsExistByCommentId(doubleCommentaryDTO.getCommentaryId())) {
            throw new BusinessDataCheckFailException("该数据不存在无法修改");
        }
    }

    /**
     * desc: 维护附件上传
     *
     * @param doubleCommentaryDTO dto
     * @param commentaryId        主键id
     * @auther FanYanGen
     * @date 2019-05-14 10:14
     */
    private int updatingFiles(DoubleCommentaryDTO doubleCommentaryDTO, Long commentaryId) {
        return tabPbAttachmentService.intelligentOperation(doubleCommentaryDTO.getAttachments(), commentaryId, AttachmentType.DOUBLE_COMMENTARY);
    }

}
