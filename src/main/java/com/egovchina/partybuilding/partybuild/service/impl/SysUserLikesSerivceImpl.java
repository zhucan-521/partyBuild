package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.SysUserLikesSerivce;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserLikesSerivceImpl implements SysUserLikesSerivce {

    @Autowired
    TabSysUserMapper tabSysUserMapper;


    /**
     * 给党员添加兴趣爱好
     * @param likes
     * @return
     */
    @Override
    public int insertSysUserLikes(String likes,Long userId) {
        int i=likes.length();
        String b=likes.substring(likes.length()-1);
        if(",".equals(b)){
            likes=likes.substring(0,likes.length()-1);
        }
        SysUser sysUser=tabSysUserMapper.selectByPrimaryKey(userId);
        sysUser.setLikes(likes);
        return tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


    /**
     * 判断党员兴趣爱好是否选择
     * @return 选择返回他的兴趣爱好  未选择返回字符串0
     */
    @Override
    public String checkedSysUserLikes() {
        Long userId= UserContextHolder.getUserId().longValue();
        return tabSysUserMapper.checkedSysUserLikes(userId);
    }


}
