package com.egovchina.partybuilding.partybuild.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.egovchina.partybuilding.common.util.RedisKeyConstant.COMMON_RED_LIST_USER_IDS;

@Repository
public interface SysRedListMapper {

    /**
     * desc: 获取所有用户Id集合
     *
     * @return userId 集合
     * @auther FanYanGen
     * @date 2019-06-17 15:02
     */
    @Cacheable(value = COMMON_RED_LIST_USER_IDS)
    List<Long> getAllUserId();

}