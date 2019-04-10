package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbMemberReduceList;
import com.yizheng.partybuilding.system.entity.SysUser;


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
