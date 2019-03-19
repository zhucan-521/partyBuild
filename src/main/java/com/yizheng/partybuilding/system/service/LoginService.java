package com.yizheng.partybuilding.system.service;

import com.yizheng.commons.enums.LoginType;

public interface LoginService {

    /**
     * 登录
     * @param loginType 登录类型,admin.后台，edu.教育平台，represent.党代表平台
     * @param idCardNo
     * @param password
     * @return
     */
    String login(LoginType loginType , String idCardNo, String password);
}
