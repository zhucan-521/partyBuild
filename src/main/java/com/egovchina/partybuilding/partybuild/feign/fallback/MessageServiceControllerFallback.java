package com.egovchina.partybuilding.partybuild.feign.fallback;

import com.egovchina.partybuilding.partybuild.entity.TabPbNotice;
import com.egovchina.partybuilding.partybuild.feign.MessageServiceController;
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
