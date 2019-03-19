package com.yizheng.partybuilding.feign;

import com.yizheng.partybuilding.entity.TabPbNotice;
import com.yizheng.partybuilding.feign.fallback.MessageServiceControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "message-service" , fallback = MessageServiceControllerFallback.class)
public interface MessageServiceController {

    /**
     * 远程调用 partybuilding-service 的 ClientUserController 的 hello 方法
     * @return
     */
    @PostMapping(value = "/notice/add")
    int noticeAdd(TabPbNotice notice);

}
