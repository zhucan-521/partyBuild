package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import com.egovchina.partybuilding.partybuild.repository.TabPbHardshipMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.HardshipPartyMemberService;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesAndPaddingBaseField;

/**
 * desc: 困难党员-服务接口实现
 * Created by FanYanGen on 2019/4/22 17:24
 */
@Transactional(rollbackFor = Exception.class)
@Service("hardshipService")
public class HardshipPartyMemberServiceImpl implements HardshipPartyMemberService {

    @Autowired
    private TabPbHardshipMapper hardshipMapper;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Autowired
    private TabSysUserMapper sysUserMapper;

    @Override
    public PageInfo<HardshipPartyVO> findHardshipPartyVOWithConditions(HardshipQueryBean hardshipQueryBean, Page page) {
        PageHelper.startPage(page);
        Map<String, Object> conditions = new HashMap<>(5);
        conditions.put("hardshipType", hardshipQueryBean.getHardshipType());
        conditions.put("orgRange", hardshipQueryBean.getOrgRange());
        conditions.put("username", hardshipQueryBean.getUsername());
        conditions.put("delFlag", 0);
        Long rangeDeptId = hardshipQueryBean.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        conditions.put("rangeDeptId", rangeDeptId);
        return new PageInfo<>(hardshipMapper.selectWithConditions(conditions));
    }

    @Override
    public int insertHardshipPartyMember(HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        verification(hardshipPartyMemberDTO);
        TabPbHardship tabPbHardship = copyPropertiesAndPaddingBaseField(hardshipPartyMemberDTO, TabPbHardship.class, false, false);
        // 新增困难党员时 修改党员困难标识
        int result = hardshipMapper.insertSelective(tabPbHardship);
        if (0 < result) {
            SysUser user = new SysUser();
            byte poorNum = 1;
            user.setUserId(hardshipPartyMemberDTO.getUserId().intValue());
            user.setIsPoor(poorNum);
            result += sysUserMapper.updateByPrimaryKeySelective(user);
        }
        return result;
    }

    @Override
    public int deleteByHardshipId(Long hardshipId) {
        TabPbHardship hardship = new TabPbHardship();
        hardship.setDelFlag(CommonConstant.STATUS_DEL);
        hardship.setHardshipId(hardshipId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(hardship);
        int result = hardshipMapper.updateByPrimaryKeySelective(hardship);
        // 困难党员删除时 党员困难标识也随之被修改
        if (0 < result) {
            SysUser user = new SysUser();
            byte poorNum = 0;
            user.setUserId(hardshipMapper.selectByPrimaryKey(hardshipId).getUserId().intValue());
            user.setIsPoor(poorNum);
            result += sysUserMapper.updateByPrimaryKeySelective(user);
        }
        return result;
    }

    @Override
    public int updateHardshipPartyMember(HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        verification(hardshipPartyMemberDTO);
        TabPbHardship tabPbHardship = copyPropertiesAndPaddingBaseField(hardshipPartyMemberDTO, TabPbHardship.class, false, true);
        return hardshipMapper.updateByPrimaryKeySelective(tabPbHardship);
    }

    @Override
    public HardshipPartyVO findHardshipPartyVOByHardshipId(Long hardshipId) {
        return copyPropertiesAndPaddingBaseField(hardshipMapper.findByHardshipId(hardshipId), HardshipPartyVO.class, false, false);
    }

    @Override
    public HardshipPartyVO findHardshipPartyVOByUserId(Long userId) {
        return copyPropertiesAndPaddingBaseField(hardshipMapper.findByUserId(userId), HardshipPartyVO.class, false, false);
    }

    /**
     * desc: 数据校验提示
     *
     * @param hardshipPartyMemberDTO dto
     * @return void
     * @author FanYanGen
     * @date 2019/4/24 21:02
     **/
    private void verification(HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        Integer userId = hardshipPartyMemberDTO.getUserId().intValue();
        if (!deptMapper.isExist(hardshipPartyMemberDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!sysUserMapper.checkIsExistByUserId(userId)) {
            throw new BusinessDataCheckFailException("该用户不存在");
        }
    }

}
