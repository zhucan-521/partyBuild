package com.egovchina.partybuilding.partybuild.feign;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.feign.fallback.SysConfigFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * desc: 系统配置 Feign客户端
 * Created by FanYanGen on 2019-07-12 17:51
 */
@FeignClient(name = "system", fallback = SysConfigFallBack.class)
public interface SysConfigFeignClient {

    /**
     * desc: 根据itemId获取
     *
     * @param id itemId
     * @return content
     * @auther FanYanGen
     * @date 2019-07-12 18:21
     */
    @ResponseBody
    @GetMapping("/v1/configurations/{id}/get-value")
    ReturnEntity getConfigurationValue(@PathVariable Long id);

}
