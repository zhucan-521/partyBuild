package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.egovchina.partybuilding.common.entity.UserInfo;
import com.egovchina.partybuilding.common.entity.UserLoginDto;
import com.egovchina.partybuilding.common.enums.LoginType;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.JwtUtil;
import com.egovchina.partybuilding.partybuild.system.service.LoginService;
import com.egovchina.partybuilding.partybuild.system.service.SysAccountService;
import com.egovchina.partybuilding.partybuild.system.service.SysUserService;
import com.egovchina.partybuilding.partybuild.util.PasswordCodecUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysAccountService accountService;
    @Autowired
    private SysUserService userService;

    /**
     * 根据身份证号和密码登录
     *
     * @param loginType 登录类型,admin.后台，edu.教育平台，represent.党代表平台
     * @param idCardNo  身份证
     * @param password  密码
     * @return
     */
    @Override
    public String login(LoginType loginType, String idCardNo, String password) {
        String token = "";

        UserInfo userInfo = null;
        switch (loginType) {
            case admin:
                userInfo = accountService.findUserInfo("PWD", idCardNo);
                break;
            case edu:
                userInfo = userService.findUserInfo("PWD", idCardNo);   //党员
                if (userInfo != null && userInfo.getUserLogin() != null) {
                    userInfo.getUserLogin().setSysUserId(userInfo.getUserLogin().getUserId());
                    userInfo.getUserLogin().setUserId(0);
                }
                //党员可能不是后台用户，因此在 UserInfo 中党员可能不会有userId
                UserInfo accountUserInfo = accountService.findUserInfo("PWD", idCardNo); //后台用户
                if (userInfo != null && userInfo.getUserLogin() != null && accountUserInfo != null && accountUserInfo.getUserLogin() != null) {
                    userInfo.getUserLogin().setUserId(accountUserInfo.getUserLogin().getUserId());
                }
                break;
            case represent:
                break;
            default:
                throw new BusinessDataInvalidException("无效的登录方式");
        }

        if (!(userInfo != null && userInfo.getUserLogin() != null)) {
            throw new BusinessDataCheckFailException("请输入正确用户名");
        }
        UserLoginDto user = userInfo.getUserLogin();
        if (user != null && !StringUtils.isEmpty(user.getPassword())) {
            boolean flag = PasswordCodecUtil.matches(password, user.getPassword());
            if (flag) {
                user.setPassword(null);
                token = JwtUtil.generateToken(userInfo);
            } else {
                throw new BusinessDataCheckFailException("请输入正确的密码");
            }
        } else {
            throw new BusinessDataNotFoundException("未查询到用户信息");
        }
        return token;
    }
}