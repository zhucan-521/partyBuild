package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.FamilyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;
import com.egovchina.partybuilding.partybuild.repository.TabPbFamilyMapper;
import com.egovchina.partybuilding.partybuild.service.FamilyService;
import com.egovchina.partybuilding.partybuild.vo.FamilyMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhucan on 2018/11/26
 */
@Service
@Transactional(rollbackFor = BusinessException.class)
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private TabPbFamilyMapper tabPbFamilyMapper;

    /**
     * 根据用户id获取他的家庭成员
     * @param partyMemberId
     * @return
     */
    @Override
    public List<FamilyMemberVO> selectFamilyMemberList(Long partyMemberId) {
        return tabPbFamilyMapper.getFamilyMemberVoByUserId(partyMemberId);
    }

    /**
     * 根据主键ID查询单条记录
     * @param relationId
     * @return
     */
    @Override
    public FamilyMemberVO selectFamilyMemberById(Long relationId) {
        TabPbFamily tabPbFamily = tabPbFamilyMapper.findById(relationId);
        FamilyMemberVO familyMemberVO =new FamilyMemberVO();
        BeanUtil.copyPropertiesIgnoreNull(tabPbFamily, familyMemberVO);
        return familyMemberVO;
    }

    /**
     * 根据主键ID删除单条记录
     * @param relationId
     */
    @Override
    public int deleteFamilyMemberByPrimaryKey(Long relationId) {
        if(null== tabPbFamilyMapper.findById(relationId)){
            throw new BusinessDataCheckFailException("不存在这个人");
        }
        TabPbFamily tabPbFamily = new TabPbFamily();
        tabPbFamily.setRelationId(relationId);
        tabPbFamily.setDelFlag("1");
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbFamily);
        return tabPbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
    }

    /**
     * 保存家庭成员
     * @param
     */
    @Override
    public int addFamilyDTO(FamilyMemberDTO familyMemberDTO) {
        if(familyMemberDTO.getUserId()==null){
            throw new BusinessDataCheckFailException("userId不能为空");
        }
        TabPbFamily tabPbFamily=new TabPbFamily();
        BeanUtil.copyPropertiesIgnoreNull(familyMemberDTO,tabPbFamily);
        return tabPbFamilyMapper.insertSelective(tabPbFamily);
    }

    /**
     * 修改家庭成员
     * @param familyMemberDTO
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(FamilyMemberDTO familyMemberDTO) {
        TabPbFamily tabPbFamily=new TabPbFamily();
        BeanUtil.copyPropertiesIgnoreNull(familyMemberDTO, tabPbFamily);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFamily);
        return  tabPbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
    }
}
