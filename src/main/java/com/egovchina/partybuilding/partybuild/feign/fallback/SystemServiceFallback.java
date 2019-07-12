package com.egovchina.partybuilding.partybuild.feign.fallback;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.feign.SystemServiceFeignClient;
import org.springframework.stereotype.Component;

/**
 * 系统服务降级
 *
 * @author Zhang Fan
 **/
@Component
public class SystemServiceFallback implements SystemServiceFeignClient {

    @Override
    public ReturnEntity insertUserRole(Long roleId, Long userId) {
        return ReturnUtil.fail();
    }

    @Override
    public ReturnEntity<Boolean> checkUserHasSpecifiedRole(Long roleId, Long userId) {
        return ReturnUtil.fail();
    }

    @Override
    public ReturnEntity getMessageContent(Long id) {
        return null;
    }

}
