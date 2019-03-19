package com.yizheng.partybuilding.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.RedisKeyConstant;
import com.yizheng.partybuilding.entity.ClientUser;
import com.yizheng.partybuilding.repository.ClientUserMapper;
import com.yizheng.partybuilding.service.inf.IClientUserService;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUserServiceImpl implements IClientUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ClientUserMapper userMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 通过username获取用户信息，先从redis中取，再从数据库取
     *
     * @param username
     * @return
     */
    @Override
    public ClientUser findByUsername(String username) {
        ClientUser user = userMapper.findByUsername(username);
        /*boolean flag = redisTemplate.opsForHash().hasKey(RedisKeyConstant.User_Details, username);
        if (flag) {
            user = JSON.parseObject(redisTemplate.opsForHash().get(RedisKeyConstant.User_Details, username).toString(), ClientUser.class);
        } else {
            user = userMapper.findByUsername(username);
            redisTemplate.opsForHash().put(RedisKeyConstant.User_Details, username, JSON.toJSONString(user));
        }*/
        return user;
    }

    /**
     * 翻页获取用户列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<ClientUser> getPageList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);//翻页
        List<ClientUser> users = userMapper.getPageList();
        PageInfo<ClientUser> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public int update(ClientUser clientUser) {
        return userMapper.updateByPrimaryKeySelective(clientUser);
    }

    @Override
    public List<ClientUser> listAll() {
        return userMapper.listAll();
    }
}
