package com.yizheng.partybuilding.service.inf;


public interface SysUserLikesSerivce {

    /**
     * 党员兴趣爱好添加
     * @param likes
     * @return
     */
    int insertSysUserLikes(String likes,Long userId);

    /**
     * 判断党员兴趣爱好是否选择
     * @return 选择返回他的兴趣爱好  未选择返回字符串0
     */
    String checkedSysUserLikes();

}
