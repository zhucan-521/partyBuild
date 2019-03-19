package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.ClientUser;

import java.util.List;

public interface ClientUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClientUser record);

    int insertSelective(ClientUser record);

    ClientUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientUser record);

    int updateByPrimaryKey(ClientUser record);

    //根据username查询用户信息
    ClientUser findByUsername(String username);

    //翻页获取用户信息
    List<ClientUser> getPageList();

    //获取所有用户信息
    List<ClientUser> listAll();
}