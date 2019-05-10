package com.egovchina.partybuilding.partybuild.feign;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.feign.fallback.LifeServiceFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description: 活动服务feignClient
 * @author: WuYunJie
 * @create: 2019-05-10 10:47
 **/
@FeignClient(value = "life", fallback = LifeServiceFallback.class)
public interface LifeServiceFeignClient {

    /**
     * 调用活动服务修改/新增联点领导接口
     *
     * @return: com.egovchina.partybuilding.common.util.ReturnEntity
     * @Author: WuYunJie
     * @Date: 2019/5/10
     */
    @ApiOperation(value = "活动修改/新增联点领导", notes = "活动修改/新增联点领导")
    @PutMapping("/v1/activities/leader")
    @ResponseBody
    ReturnEntity updateLianDianLeadership(@RequestParam("activitiesId") List<Long> activitiesId,
                                          @RequestParam("userId") Long userId,
                                          @RequestParam("realName") String realName);
}
