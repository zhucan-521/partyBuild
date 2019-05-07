package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.entity.ClassifyQueryBean;
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

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetAndCopyProperties;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * desc: 分类定等-服务接口实现
 * Created by FanYanGen on 2019/4/22 15:58
 */
@Transactional(rollbackFor = Exception.class)
@Service("orgClassifyService")
public class OrgClassifyServiceImpl implements OrgClassifyService {

    @Autowired
    private TabPbOrgClassifyMapper tabPbOrgClassifyMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Override
    public PageInfo<OrgClassifyVO> findOrgClassifyVOWithConditions(ClassifyQueryBean classifyQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(tabPbOrgClassifyMapper.selectWithConditions(classifyQueryBean));
    }

    @Override
    public int deleteOrgClassify(Long orgClassifyId) {
        TabPbOrgClassify orgClassify = new TabPbOrgClassify();
        orgClassify.setDelFlag(CommonConstant.STATUS_DEL);
        orgClassify.setOrgClassifyId(orgClassifyId);
        TabPbOrgClassify tabPbOrgClassify = generateTargetCopyPropertiesAndPaddingBaseField(orgClassify, TabPbOrgClassify.class, true);
        return tabPbOrgClassifyMapper.updateByPrimaryKeySelective(tabPbOrgClassify);
    }

    @Override
    public int insertOrgClassify(OrgClassifyDTO orgClassifyDTO) {
        verification(orgClassifyDTO);
        TabPbOrgClassify tabPbOrgClassify = generateTargetCopyPropertiesAndPaddingBaseField(orgClassifyDTO, TabPbOrgClassify.class, false);
        return tabPbOrgClassifyMapper.insertSelective(tabPbOrgClassify);
    }

    @Override
    public int updateOrgClassify(OrgClassifyDTO orgClassifyDTO) {
        verification(orgClassifyDTO);
        TabPbOrgClassify tabPbOrgClassify = generateTargetCopyPropertiesAndPaddingBaseField(orgClassifyDTO, TabPbOrgClassify.class, true);
        return tabPbOrgClassifyMapper.updateByPrimaryKeySelective(tabPbOrgClassify);
    }

    @Override
    public OrgClassifyVO findOrgClassifyVOByOrgClassifyId(Long orgClassifyId) {
        return generateTargetAndCopyProperties(tabPbOrgClassifyMapper.selectByPrimaryKey(orgClassifyId), OrgClassifyVO.class);
    }

    /**
     * desc: 数据校验提示
     *
     * @param orgClassifyDTO dto
     * @author FanYanGen
     * @date 2019/4/25 9:54
     **/
    private void verification(OrgClassifyDTO orgClassifyDTO) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(orgClassifyDTO.getDeptId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
    }

}


