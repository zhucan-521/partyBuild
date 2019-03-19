package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.partybuilding.entity.ClientUser;

import java.util.List;

public interface IClientUserService {

    ClientUser findByUsername(String username);

    /**
     * 翻页获取用户列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<ClientUser> getPageList(int pageSize , int pageNum);

    /**
     * 获取所有用户信息
     * @return
     */
    List<ClientUser> listAll();

    /**
     * 更新
     * @param clientUser
     * @return
     */
    int update(ClientUser clientUser);
}
