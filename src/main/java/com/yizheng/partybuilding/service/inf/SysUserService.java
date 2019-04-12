package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.dto.SysUserDto;

/**
 * desc: 党员信息-业务逻辑层接口
 * Created by FanYanGen on 2019/4/12 09:10
 */
public interface SysUserService {

    /**
     * desc: 保存用户信息
     *
     * @param sysUser 用户数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/12 14:40
     **/
    int saveSysUserInfo(SysUserDto sysUser);

    /**
     * desc: 修改用户信息
     *
     * @param sysUser 用户数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/12 14:40
     **/
    int updateSysUserInfo(SysUserDto sysUser);

}
