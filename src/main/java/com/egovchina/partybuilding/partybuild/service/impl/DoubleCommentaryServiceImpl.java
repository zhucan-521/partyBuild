package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.CommentaryDTO;
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
import java.util.HashMap;
import java.util.Map;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesAndPaddingBaseField;

/**
 * desc: 双述双评-服务接口实现
 * Created by FanYanGen on 2019/4/24 16:17
 */
@Transactional(rollbackFor = Exception.class)
@Service("tabPbDoubleCommentaryService")
public class DoubleCommentaryServiceImpl implements DoubleCommentaryService {

    @Autowired
    private TabPbDoubleCommentaryMapper doubleCommentaryMapper;

    @Autowired
    private ITabPbAttachmentService attachmentService;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Override
    public int deleteByPrimaryKey(Long commentaryId) {
        return doubleCommentaryMapper.deleteByPrimaryKey(commentaryId);
    }

    @Override
    public int insert(TabPbDoubleCommentary record) {
        return doubleCommentaryMapper.insert(record);
    }

    @Override
    public int insertSelective(TabPbDoubleCommentary record) {
        return doubleCommentaryMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(TabPbDoubleCommentary record) {
        return doubleCommentaryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(TabPbDoubleCommentary record) {
        return doubleCommentaryMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(TabPbDoubleCommentary record) {
        return doubleCommentaryMapper.updateByPrimaryKey(record);
    }

    @Override
    public int insertCommentary(CommentaryDTO commentaryDTO) {
        Long planYear = commentaryDTO.getPlanYear();
        Long orgId = commentaryDTO.getOrgId();
        Map<String, Object> conditions = new HashMap<>(3);
        conditions.put("planYear", planYear);
        conditions.put("orgId", orgId);
        conditions.put("delFlag", "0");
        verificationInsert(commentaryDTO, conditions);
        TabPbDoubleCommentary tabPbDoubleCommentary = copyPropertiesAndPaddingBaseField(commentaryDTO, TabPbDoubleCommentary.class, false, false);
        int result = doubleCommentaryMapper.insertSelective(tabPbDoubleCommentary);
        if (result > 0) {
            result += attachmentService.intelligentOperation(commentaryDTO.getAttachments(), commentaryDTO.getCommentaryId(), AttachmentType.DOUBLE_COMMENTARY);
        }
        return result;
    }

    @Override
    public int updateCommentary(CommentaryDTO commentaryDTO) {
        verificationUpdate(commentaryDTO);
        TabPbDoubleCommentary tabPbDoubleCommentary = copyPropertiesAndPaddingBaseField(commentaryDTO, TabPbDoubleCommentary.class, false, true);
        int retVal = doubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
        if (0 < retVal) {
            retVal += attachmentService.intelligentOperation(commentaryDTO.getAttachments(), commentaryDTO.getCommentaryId(), AttachmentType.DOUBLE_COMMENTARY);
        }
        return retVal;
    }

    @Override
    public CommentaryVO findCommentaryVOByCommentaryId(Long commentaryId) {
        return doubleCommentaryMapper.selectByPrimaryKey(commentaryId);
    }

    @Override
    public PageInfo<CommentaryVO> findCommentaryVOWithConditions(Page page, CommentaryQueryBean commentaryQueryBean, OrgRange orgRange) {
        PageHelper.startPage(page);
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("planYear", commentaryQueryBean.getPlanYear());
        conditions.put("reportStartDate", commentaryQueryBean.getReportStartDate());
        conditions.put("reportEndDate", commentaryQueryBean.getReportEndDate());
        conditions.put("delFlag", "0");
        return new PageInfo<>(doubleCommentaryMapper.selectWithConditions(conditions));
    }

    @Override
    public int deleteCommentary(Long commentaryId) {
        TabPbDoubleCommentary commentary = new TabPbDoubleCommentary();
        commentary.setCommentaryId(commentaryId);
        commentary.setDelFlag(CommonConstant.STATUS_DEL);
        TabPbDoubleCommentary tabPbDoubleCommentary = copyPropertiesAndPaddingBaseField(commentary, TabPbDoubleCommentary.class, false, true);
        return doubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
    }

    @Override
    public int verifyCommentary(DoubleCommentaryQueryBean doubleCommentaryQueryBean) {
        CommentaryVO commentaryVO = doubleCommentaryMapper.selectByPrimaryKey(doubleCommentaryQueryBean.getCommentaryId());
        if (null == commentaryVO) {
            throw new BusinessDataNotFoundException("双述双评数据不存在");
        }
        TabPbDoubleCommentary commentary = new TabPbDoubleCommentary();
        commentary.setCommentaryId(commentaryVO.getCommentaryId());
        commentary.setCheckOrg(UserContextHolder.getOrgId());
        commentary.setCheckUser(UserContextHolder.getUserIdLong());
        commentary.setCheckResult(doubleCommentaryQueryBean.getCheckResult());
        commentary.setCheckDesc(doubleCommentaryQueryBean.getCheckDesc());
        commentary.setCheckDate(new Date());
        TabPbDoubleCommentary tabPbDoubleCommentary = copyPropertiesAndPaddingBaseField(commentary, TabPbDoubleCommentary.class, false, true);
        return doubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
    }

    /**
     * desc: 数据校验提示
     *
     * @param commentaryDTO dto
     * @return void
     * @author FanYanGen
     * @date 2019/4/24 21:02
     **/
    private void verificationInsert(CommentaryDTO commentaryDTO, Map map) {
        if (!deptMapper.isExist(commentaryDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (0 < doubleCommentaryMapper.selectWithConditions(map).size()) {
            throw new BusinessDataCheckFailException(String.format("该组织%s年度双述双评总结已存在", commentaryDTO.getPlanYear()));
        }
    }

    private void verificationUpdate(CommentaryDTO commentaryDTO) {
        if (!deptMapper.isExist(commentaryDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!doubleCommentaryMapper.isExist(commentaryDTO.getCommentaryId())) {
            throw new BusinessDataCheckFailException("该数据不存在 无法修改");
        }

    }

}
