package com.yizheng.partybuilding.service.impl;

import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.BeanUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.SysUserDto;
import com.yizheng.partybuilding.entity.TabPbPartyEducation;
import com.yizheng.partybuilding.entity.TabPbPartyJobTitle;
import com.yizheng.partybuilding.entity.TabPbPartyWork;
import com.yizheng.partybuilding.repository.TabPbPartyEducationMapper;
import com.yizheng.partybuilding.repository.TabPbPartyJobTitleMapper;
import com.yizheng.partybuilding.repository.TabPbPartyWorkMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.*;
import com.yizheng.partybuilding.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * desc: 党员信息-业务逻辑层接口实现
 * Created by FanYanGen on 2019/4/12 09:15
 */
@Transactional(rollbackFor = Exception.class)
@Service("userService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbPartyEducationMapper tabPbPartyEducationMapper;

    @Autowired
    private TabPbPartyJobTitleMapper tabPbPartyJobTitleMapper;

    @Autowired
    private TabPbPartyWorkMapper tabPbPartyWorkMapper;


    @Override
    @PaddingBaseField(recursive = true)
    public int saveSysUserInfo(SysUserDto sysUser) {
        SysUser user = sysUser.getSysUser();
        if (null != tabSysUserMapper.selectUserByIdCardNo(user.getIdCardNo())) {
            throw new BusinessDataCheckFailException("此人已经是党员！");
        }
        user.setIdentityType(59423L);
        tabSysUserMapper.insertSelective(user);
        SysUserDto sysUserDto = parseSysUserDto(sysUser);
        int addEducationRes = tabPbPartyEducationMapper.batchInsert(sysUserDto.getEducationList());
        int addJobTitleRes = tabPbPartyJobTitleMapper.batchInsert(sysUserDto.getJobTitleList());
        int addWorkRes = tabPbPartyWorkMapper.batchInsert(sysUser.getWorkList());
        if (0 < user.getUserId() && 0 < addEducationRes && 0 < addJobTitleRes && 0 < addWorkRes) {
            return 1;
        }
        return 0;
    }

    @Override
    @PaddingBaseField(recursive = true)
    public int updateSysUserInfo(SysUserDto sysUser) {
        if (isEmpty(sysUser.getSysUser().getUserId())) {
            throw new BusinessDataIncompleteException("人员id不存在");
        }
        if (isEmpty(sysUser.getSysUser().getAncestorPlace())) {
            throw new BusinessDataIncompleteException("籍贯不能为空");
        }
        SysUserDto sysUserDto = parseSysUserDto(sysUser);
        int updateUserRes = tabSysUserMapper.updateByPrimaryKeySelective(sysUser.getSysUser());
        int updateEducationRes = tabPbPartyEducationMapper.batchUpdate(sysUser.getEducationList());
        int updateJobTitleRes = tabPbPartyJobTitleMapper.batchUpdate(sysUserDto.getJobTitleList());
        int updateWorkRes = tabPbPartyWorkMapper.batchUpdate(sysUser.getWorkList());
        if (0 < updateUserRes && 0 < updateEducationRes && 0 < updateJobTitleRes && 0 < updateWorkRes) {
            return 1;
        }
        return 0;
    }

    private SysUserDto parseSysUserDto(SysUserDto sysUser) {
        SysUserDto sysUserDto = new SysUserDto();
        long currentUserId = tabSysUserMapper.selectByPrimaryKey(sysUser.getSysUser().getUserId().longValue()).getUserId();
        List<TabPbPartyEducation> educationList = sysUser.getEducationList();
        List<TabPbPartyJobTitle> jobTitleList = sysUser.getJobTitleList();
        List<TabPbPartyWork> workList = sysUser.getWorkList();

        educationList.forEach(eudcation -> {
            eudcation.setUserId(currentUserId);
        });

        jobTitleList.forEach(job -> {
            job.setUserId(currentUserId);
        });

        workList.forEach(work -> {
            work.setUserId(currentUserId);
        });

        sysUserDto.setSysUser(sysUser.getSysUser());
        sysUserDto.setEducationList(educationList);
        sysUserDto.setJobTitleList(jobTitleList);
        sysUserDto.setWorkList(workList);
        return sysUserDto;
    }

}
