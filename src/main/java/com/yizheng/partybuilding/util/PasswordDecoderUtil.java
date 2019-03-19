package com.yizheng.partybuilding.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码解密帮助类
 */
public class PasswordDecoderUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 密码解密
     * @param rawPassword    前端用户输入的密码
     * @param encodePassword 数据库中的密码
     * @return
     */
    public static boolean matches(CharSequence rawPassword , String encodePassword){
        return ENCODER.matches(rawPassword , encodePassword);
    }
}
