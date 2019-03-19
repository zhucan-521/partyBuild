package com.yizheng.partybuilding.feign.fallback;

import com.yizheng.partybuilding.entity.TabPbNotice;
import com.yizheng.partybuilding.feign.MessageServiceController;
import org.springframework.stereotype.Component;

/**
 * 所有FeignClient接口必须有熔断降低处理
 * Hystrix 降级处理，防止因异常或网络情况导致服务雪崩
 */
@Component
public class MessageServiceControllerFallback implements MessageServiceController {

    @Override
    public int noticeAdd(TabPbNotice notice) {
        return -1;
    }
}
