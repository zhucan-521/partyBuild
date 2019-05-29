package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.DoubleCommentaryDTO;
import com.egovchina.partybuilding.partybuild.dto.DoubleCommentaryUpdateDTO;
import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.DoubleCommentaryVerifyDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbDoubleCommentary;
import com.egovchina.partybuilding.partybuild.repository.TabPbDoubleCommentaryMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.DoubleCommentaryService;
import com.egovchina.partybuilding.partybuild.vo.CommentaryDetailsVO;
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
    private TabSysDeptMapper tabSysDeptMapper;

    @Override
    public int insertCommentary(DoubleCommentaryDTO doubleCommentaryDTO) {
        verificationInsert(doubleCommentaryDTO);
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(doubleCommentaryDTO, TabPbDoubleCommentary.class, false);
        return tabPbDoubleCommentaryMapper.insertSelective(tabPbDoubleCommentary);
    }

    @Override
    public int updateCommentary(DoubleCommentaryUpdateDTO doubleCommentaryUpdateDTO) {
        verificationUpdate(doubleCommentaryUpdateDTO);
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(doubleCommentaryUpdateDTO, TabPbDoubleCommentary.class, true);
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
    }

    @Override
    public CommentaryDetailsVO findCommentaryVOByCommentaryId(Long commentaryId) {
        verificationQuery(commentaryId);
        return tabPbDoubleCommentaryMapper.getCommentDetailByCommentId(commentaryId);
    }

    @Override
    public PageInfo<CommentaryVO> findCommentaryVOListWithConditions(CommentaryQueryBean commentaryQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(tabPbDoubleCommentaryMapper.selectWithConditions(commentaryQueryBean));
    }

    @Override
    public int deleteCommentary(Long commentaryId) {
        TabPbDoubleCommentary commentary = new TabPbDoubleCommentary().setCommentaryId(commentaryId).setDelFlag(CommonConstant.STATUS_DEL);
        TabPbDoubleCommentary tabPbDoubleCommentary = generateTargetCopyPropertiesAndPaddingBaseField(commentary, TabPbDoubleCommentary.class, true);
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
    }

    @Override
    public int verifyCommentary(DoubleCommentaryVerifyDTO doubleCommentaryVerifyDTO) {
        CommentaryVO commentaryVO = tabPbDoubleCommentaryMapper.selectByPrimaryKey(doubleCommentaryVerifyDTO.getCommentaryId());
        if (commentaryVO == null) {
            throw new BusinessDataNotFoundException("双述双评数据不存在");
        }
        TabPbDoubleCommentary commentary = new TabPbDoubleCommentary();
        commentary.setCommentaryId(commentaryVO.getCommentaryId());
        commentary.setCheckOrg(doubleCommentaryVerifyDTO.getOrgId());
        commentary.setCheckUser(UserContextHolder.getUserId());
        commentary.setCheckResult(doubleCommentaryVerifyDTO.getCheckResult());
        commentary.setCheckDesc(doubleCommentaryVerifyDTO.getCheckDesc());
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
        Long orgId = doubleCommentaryDTO.getOrgId();
        String planYear = doubleCommentaryDTO.getPlanYear();
        if (!tabSysDeptMapper.checkIsExistByOrgId(orgId)) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (tabPbDoubleCommentaryMapper.checkIsExistByPlanYear(orgId, null, planYear)) {
            throw new BusinessDataCheckFailException(String.format("该书记%s年度双述双评总结已存在", planYear));
        }
    }

    private void verificationUpdate(DoubleCommentaryUpdateDTO doubleCommentaryUpdateDTO) {
        Long orgId = doubleCommentaryUpdateDTO.getOrgId();
        Long commentaryId = doubleCommentaryUpdateDTO.getCommentaryId();
        String planYear = doubleCommentaryUpdateDTO.getPlanYear();
        if (!tabSysDeptMapper.checkIsExistByOrgId(doubleCommentaryUpdateDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!tabPbDoubleCommentaryMapper.checkIsExistByCommentId(doubleCommentaryUpdateDTO.getCommentaryId())) {
            throw new BusinessDataCheckFailException("该数据不存在无法修改");
        }
        if (tabPbDoubleCommentaryMapper.checkIsExistByPlanYear(orgId, commentaryId, planYear)) {
            throw new BusinessDataCheckFailException(String.format("该书记%s年度双述双评总结已存在", planYear));
        }
    }

    private void verificationQuery(Long commentaryId) {
        if (!tabPbDoubleCommentaryMapper.checkIsExistByCommentId(commentaryId)) {
            throw new BusinessDataCheckFailException("该数据不存在");
        }
    }

}
