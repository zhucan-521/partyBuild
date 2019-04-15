package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.github.pagehelper.PageInfo;


public interface ExtendedInfoService {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKeyWithBLOBs(SysUser record);

    int updateByPrimaryKey(SysUser record);

    PageInfo<SysUser> selectPartyByIdCardNoOrUserName(SysUser sysUser, Page page);

    int updateByUserId(Long userId);

    Boolean updateByUserId(TabPbMemberReduceList reduce );

    Boolean restoreUser(Long userId);
}
