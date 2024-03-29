package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import com.egovchina.partybuilding.partybuild.repository.TabPbHardshipMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.HardshipPartyMemberService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.util.UserTagType;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.*;

/**
 * desc: 困难党员-服务接口实现
 * Created by FanYanGen on 2019/4/22 17:24
 */
@Transactional(rollbackFor = Exception.class)
@Service("hardshipPartyMemberService")
public class HardshipPartyMemberServiceImpl implements HardshipPartyMemberService {

    @Autowired
    private TabPbHardshipMapper tabPbHardshipMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private UserTagService userTagService;

    @Override
    public PageInfo<HardshipPartyVO> findHardshipPartyVOWithConditions(HardshipQueryBean hardshipQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(tabPbHardshipMapper.selectWithConditions(hardshipQueryBean));
    }

    @Override
    public int insertHardshipPartyMember(HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        verification(hardshipPartyMemberDTO);
        TabPbHardship tabPbHardship = generateTargetCopyPropertiesAndPaddingBaseField(hardshipPartyMemberDTO, TabPbHardship.class, false);
        int result = tabPbHardshipMapper.insertSelective(tabPbHardship);
        return result;
    }

    @Override
    public int deleteByHardshipId(Long hardshipId) {
        TabPbHardship hardship = new TabPbHardship().setDelFlag(CommonConstant.STATUS_DEL).setHardshipId(hardshipId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(hardship);
        int result = tabPbHardshipMapper.updateByPrimaryKeySelective(hardship);
        return result;
    }

    @Override
    public int updateHardshipPartyMember(HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        verification(hardshipPartyMemberDTO);
        TabPbHardship tabPbHardship = generateTargetCopyPropertiesAndPaddingBaseField(hardshipPartyMemberDTO, TabPbHardship.class, true);
        return tabPbHardshipMapper.updateByPrimaryKeySelective(tabPbHardship);
    }

    @Override
    public HardshipPartyVO findHardshipPartyVOByHardshipId(Long hardshipId) {
        return generateTargetAndCopyProperties(tabPbHardshipMapper.findByHardshipId(hardshipId), HardshipPartyVO.class);
    }

    @Override
    public List<HardshipPartyVO> findHardshipPartyVOByUserId(Long userId) {
        return generateTargetListAndCopyProperties(tabPbHardshipMapper.findByUserId(userId), HardshipPartyVO.class);
    }

    /**
     * desc: 数据校验提示
     *
     * @param hardshipPartyMemberDTO dto
     * @author FanYanGen
     * @date 2019/4/24 21:02
     **/
    private void verification(HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(hardshipPartyMemberDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!tabSysUserMapper.checkIsExistByUserId(hardshipPartyMemberDTO.getUserId())) {
            throw new BusinessDataCheckFailException("该用户不存在");
        }
    }

}
