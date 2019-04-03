package com.yizheng.partybuilding.system.service;

import java.util.List;
import java.util.Map;

/**
 * 系统令牌service接口
 *
 * @Author Zhang Fan
 **/
public interface SysTokenService {

    /**
     * 获取所有token
     *
     * @return
     */
    List<Map<String, Object>> selectAllToken();

    /**
     * 根据身份证吊销token
     *
     * @param idCard 身份证
     * @return
     */
    int deleteTokenByIdCard(String idCard);
}
