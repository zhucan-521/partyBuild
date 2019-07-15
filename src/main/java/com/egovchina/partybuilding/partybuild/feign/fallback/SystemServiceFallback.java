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

    /**
     * 动党员挂靠成功消息通知文案
     */
    public static final Long SUCCESSFLOW = 59717L;

    /**
     * 流动党员挂靠失败消息通知文案
     */
    public static final Long FALSEFLOW = 59718L;

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
        if (SUCCESSFLOW.equals(id)) {
            ReturnUtil.success("{{realname}}你好，你在{{orgName}}的流动党员挂靠申请审核通过了！恭喜你！！");
        } else if (FALSEFLOW.equals(id)) {
            ReturnUtil.success("{{realname}}你好，你在{{orgName}}的流动党员挂靠申请审核不通过，建议寻找对应组织负责人了解详细信息！");
        }
        return ReturnUtil.success();
    }

}
