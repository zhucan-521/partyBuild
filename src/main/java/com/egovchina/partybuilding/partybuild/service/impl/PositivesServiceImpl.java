package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.PositivesDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositivesMapper;
import com.egovchina.partybuilding.partybuild.service.PositivesService;
import com.egovchina.partybuilding.partybuild.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.vo.PositivesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class PositivesServiceImpl implements PositivesService {

    @Autowired
    TabPbPositivesMapper tabPbPositivesMapper;

    @Override
    public int insertPositives(PositivesDTO positivesDTO) {
        TabPbPositives pbPositives = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(positivesDTO, TabPbPositives.class, false);
        return tabPbPositivesMapper.insertSelective(pbPositives);
    }

    @Override
    public int deleteById(Integer id) {
        TabPbPositives positives = tabPbPositivesMapper.selectByPrimaryKey(id);
        if (positives == null) {
            throw new BusinessDataNotFoundException("职务数据不存在");
        }
        positives.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(positives);
        return tabPbPositivesMapper.updateByPrimaryKeySelective(positives);
    }

    @Override
    public int updateById(PositivesDTO positivesDTO) {
        check(positivesDTO.getPositiveId());
        TabPbPositives pbPositives = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(positivesDTO, TabPbPositives.class, true);
        return tabPbPositivesMapper.updateByPrimaryKeySelective(pbPositives);
    }


    @Override
    public PositivesVO selectPositiveVOById(Integer id) {
        return tabPbPositivesMapper.selectByIdToPositivesVO(id);
    }

    @Override
    public List<PositivesVO> selectPositives(Long userId, String positiveType) {
        return tabPbPositivesMapper.selectByIdToAllPositivesVO(userId, positiveType);
    }

    public void check(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new BusinessDataNotFoundException("该职务主键不能为空");
        }
    }
}
