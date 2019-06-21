package com.egovchina.partybuilding.partybuild.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import static com.egovchina.partybuilding.common.util.RedisKeyConstant.COMMON_DESENSITIZATION_FIELD;

/**
 * Description: 系统配置Mapper类
 *
 * @author hufan
 * @date 2019/05/06 10:55:31
 */
@Repository
public interface SysConfigurerMapper {

    /**
     * 查询配置值根据配置项id
     *
     * @param itemId
     * @return
     */
    @Cacheable(value = COMMON_DESENSITIZATION_FIELD, key = "#itemId")
    String selectConfigValueByItemId(Long itemId);

}
