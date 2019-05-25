package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.PositivesDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositivesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
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

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Override
    public int insertPositives(PositivesDTO positivesDTO) {
        //检查时间
        checkTime(positivesDTO);
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
        checkTime(positivesDTO);
        TabPbPositives pbPositives = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(positivesDTO, TabPbPositives.class, true);
        pbPositives.setUserId(null);
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
    public void checkTime(PositivesDTO positivesDTO){
        //时间验证
        if(positivesDTO.getPositiveStart()!=null&&positivesDTO.getPositiveFinished()!=null){
            if(positivesDTO.getPositiveFinished().before(positivesDTO.getPositiveStart())){
                throw new BusinessDataCheckFailException("离职时间不能在任职时间之前");
            }
        }
        //验证用户id是否存在
        if(!tabSysUserMapper.checkIsExistByUserId(positivesDTO.getUserId())){
            throw new BusinessDataNotFoundException("该用户主id不存在");
        }
    }
}
