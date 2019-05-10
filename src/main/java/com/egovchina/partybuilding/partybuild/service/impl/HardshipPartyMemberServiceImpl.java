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

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetAndCopyProperties;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

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
        /**
         * 新增困难党员时 添加党员困难标识,并在党员表修改是否困难党员的字段
         **/
        int result = tabPbHardshipMapper.insertSelective(tabPbHardship);
        if (result > 0) {
            result += userTagService.addUserTag(tabPbHardship.getUserId(), UserTagType.DIFFICULT);
            result += tabSysUserMapper.updateUserIsPoorByHardshipId(1, tabPbHardship.getHardshipId());
        }
        return result;
    }

    @Override
    public int deleteByHardshipId(Long hardshipId) {
        TabPbHardship hardship = new TabPbHardship();
        hardship.setDelFlag(CommonConstant.STATUS_DEL);
        hardship.setHardshipId(hardshipId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(hardship);
        int result = tabPbHardshipMapper.updateByPrimaryKeySelective(hardship);
        /**
         * 困难党员删除时 移除党员困难标识,并在党员表修改是否困难党员的字段
         **/
        if (result > 0) {
            result += userTagService.delete(tabPbHardshipMapper.selectByPrimaryKey(hardshipId).getUserId(), UserTagType.DIFFICULT);
            result += tabSysUserMapper.updateUserIsPoorByHardshipId(0, hardshipId);
        }
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
    public HardshipPartyVO findHardshipPartyVOByUserId(Long userId) {
        return generateTargetAndCopyProperties(tabPbHardshipMapper.findByUserId(userId), HardshipPartyVO.class);
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
