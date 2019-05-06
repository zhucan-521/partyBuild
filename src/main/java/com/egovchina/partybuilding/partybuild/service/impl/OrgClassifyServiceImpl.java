package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgClassifyMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.OrgClassifyService;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * desc: 分类定等-服务接口实现
 * Created by FanYanGen on 2019/4/22 15:58
 */
@Transactional(rollbackFor = Exception.class)
@Service("tabPbOrgClassifyService")
public class OrgClassifyServiceImpl implements OrgClassifyService {

    @Autowired
    private TabPbOrgClassifyMapper orgClassifyMapper;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Override
    public PageInfo<OrgClassifyVO> findOrgClassifyVOWithConditions(Page page, OrgRange orgRange, String orgLevel) {
        PageHelper.startPage(page);
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("orgLevel", orgLevel);
        conditions.put("delFlag", "0");
        return new PageInfo<>(orgClassifyMapper.selectWithConditions(conditions));
    }

    @Override
    public int deleteOrgClassify(Long orgClassifyId) {
        TabPbOrgClassify orgClassify = new TabPbOrgClassify();
        orgClassify.setDelFlag(CommonConstant.STATUS_DEL);
        orgClassify.setOrgClassifyId(orgClassifyId);
        TabPbOrgClassify tabPbOrgClassify = generateTargetCopyPropertiesAndPaddingBaseField(orgClassify, TabPbOrgClassify.class, false);
        return orgClassifyMapper.updateByPrimaryKeySelective(tabPbOrgClassify);
    }

    @Override
    public int insertOrgClassify(OrgClassifyDTO orgClassifyDTO) {
        verification(orgClassifyDTO);
        TabPbOrgClassify tabPbOrgClassify = generateTargetCopyPropertiesAndPaddingBaseField(orgClassifyDTO, TabPbOrgClassify.class, false);
        return orgClassifyMapper.insertSelective(tabPbOrgClassify);
    }

    @Override
    public int updateOrgClassify(OrgClassifyDTO orgClassifyDTO) {
        verification(orgClassifyDTO);
        TabPbOrgClassify tabPbOrgClassify = generateTargetCopyPropertiesAndPaddingBaseField(orgClassifyDTO, TabPbOrgClassify.class, true);
        return orgClassifyMapper.updateByPrimaryKeySelective(tabPbOrgClassify);
    }

    @Override
    public OrgClassifyVO findOrgClassifyVOByOrgClassifyId(Long orgClassifyId) {
        return generateTargetCopyPropertiesAndPaddingBaseField(orgClassifyMapper.selectByPrimaryKey(orgClassifyId), OrgClassifyVO.class, false);
    }

    /**
     * desc: 数据校验提示
     *
     * @param orgClassifyDTO dto
     * @author FanYanGen
     * @date 2019/4/25 9:54
     **/
    private void verification(OrgClassifyDTO orgClassifyDTO) {
        if (!deptMapper.checkIsExistByOrgId(orgClassifyDTO.getDeptId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
    }

}


