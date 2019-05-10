package com.egovchina.partybuilding.partybuild.feign.fallback;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.feign.LifeServiceFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 活动降级服务
 * @author: WuYunJie
 * @create: 2019-05-10 10:50
 **/
@Component
public class LifeServiceFallback implements LifeServiceFeignClient {

    @Override
    public ReturnEntity updateLianDianLeadership(List<Long> activitiesId, Long userId, String realName) {
        return ReturnUtil.fail();
    }
}
