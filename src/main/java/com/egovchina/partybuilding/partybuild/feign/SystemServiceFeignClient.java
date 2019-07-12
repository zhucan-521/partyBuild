package com.egovchina.partybuilding.partybuild.feign;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.feign.fallback.SystemServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统服务FeignClient
 *
 * @author Zhang Fan
 **/
@FeignClient(value = "system", fallback = SystemServiceFallback.class)
public interface SystemServiceFeignClient {

    /**
     * 新增用户角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     * @return
     */
    @PostMapping("/v1/roles/{roleId}/users/{userId}")
    @ResponseBody
    ReturnEntity insertUserRole(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId);

    /**
     * 检查用户具有指定的角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     * @return
     */
    @GetMapping("/v1/roles/{roleId}/users/{userId}/exists")
    @ResponseBody
    ReturnEntity<Boolean> checkUserHasSpecifiedRole(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId);

    @ResponseBody
    @GetMapping("/v1/configurations/{id}/get-value")
    ReturnEntity getMessageContent(@PathVariable("id") Long id);

}
