package com.yizheng.partybuilding.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.domain.Page;

import com.yizheng.partybuilding.dto.TabSpecialWorkerResultDto;
import com.yizheng.partybuilding.entity.TabPbSpcialWorker;

import com.yizheng.partybuilding.repository.TabPbSpcialWorkerMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.TabSpecialWorkerService;

import com.yizheng.partybuilding.system.entity.SysUserRole;
import com.yizheng.partybuilding.system.mapper.SysUserRoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TabSpecialWorkerServiceImpl implements TabSpecialWorkerService {

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    TabPbSpcialWorkerMapper tabPbSpcialWorkerMapper;

    /**
     * 专干新增
     * roleId为44
     */
    @PaddingBaseField
    @Override
    public int insert(TabSpecialWorkerResultDto tabSpecialWorkerResultDto) {
        Long userId = tabSysUserMapper.selectUserByIdCardNo(tabSpecialWorkerResultDto.getIdCardNo()).getUserId().longValue();
        tabSpecialWorkerResultDto.setUserId(userId);
        //设置专干角色为44，并且插入sys_user_role
        SysUserRole role = tabPbSpcialWorkerMapper.selectSysUserRoleByUserId(userId);
        if (null == role) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId.intValue());
            sysUserRole.setRoleId(44);
            sysUserRoleMapper.insert(sysUserRole);
        }
        //插入专干表
        return  tabPbSpcialWorkerMapper.insertSelective(tabSpecialWorkerResultDto);
    }

    /**
     * 删除
     *
     * @param specialWorkerId
     */
    @PaddingBaseField
    @Override
    public int deleteBySpecialWorkerId(Long specialWorkerId) {
        TabPbSpcialWorker worker = tabPbSpcialWorkerMapper.selectByPrimaryKey(specialWorkerId);
        worker.setDelFlag("1");
        worker.setEblFlag("0");
        int retVal = tabPbSpcialWorkerMapper.updateByPrimaryKeySelective(worker);
        //删除user_role表里面的专干信息
        if (retVal > 0) {
            tabPbSpcialWorkerMapper.deleteTabSpecialWorkerByUserId(worker.getUserId());
        }
        return retVal;
    }

    /**
     * 查询出所有的专干党员信息
     *
     * @return
     */
    @Override
    public PageInfo<TabSpecialWorkerResultDto> selectAllTabSpecialWorkerDto(Page page, TabSpecialWorkerResultDto dto) {
        PageHelper.startPage(page);
        List<TabSpecialWorkerResultDto> list = tabPbSpcialWorkerMapper.selectAllTabSpecialWorkerDto(dto);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @PaddingBaseField(updateOnly = true)
    @Override
    public int updateBySpecialWorkerId(TabSpecialWorkerResultDto dto) {
        return tabPbSpcialWorkerMapper.updateTabSpecialWorkerResultDto(dto);
    }


}
