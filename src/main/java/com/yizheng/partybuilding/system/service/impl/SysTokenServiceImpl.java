package com.yizheng.partybuilding.system.service.impl;

import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.util.JwtUtil;
import com.yizheng.commons.util.RedisKeyConstant;
import com.yizheng.partybuilding.system.service.SysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统令牌service实现
 *
 * @Author Zhang Fan
 **/
@Service
public class SysTokenServiceImpl implements SysTokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Map<String, Object>> selectAllToken() {
        Set<String> tokenKeySet = redisTemplate.keys(RedisKeyConstant.MATCH_USERTOKEN);
        if (CollectionUtil.isEmpty(tokenKeySet)) {
            return Collections.emptyList();
        }
        List<String> tokens = redisTemplate.opsForValue().multiGet(tokenKeySet);
        if (CollectionUtil.isEmpty(tokens)) {
            return Collections.emptyList();
        }
        return tokens.stream().map(token -> {
            Map<String, Object> map = JwtUtil.validateToken(token);
            assert map != null;
            map.put("token", token);
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public int deleteTokenByIdCard(String idCard) {
        Boolean deleted = redisTemplate.delete(String.format(RedisKeyConstant.KEY_USERTOKEN, idCard));
        return deleted == null || !deleted ? 0 : 1;
    }
}
