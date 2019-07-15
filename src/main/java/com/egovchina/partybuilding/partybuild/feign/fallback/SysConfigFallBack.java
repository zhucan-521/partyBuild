package com.egovchina.partybuilding.partybuild.feign.fallback;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.feign.SysConfigFeignClient;
import org.springframework.stereotype.Component;

/**
 * desc: SysConfigFallBack FallBack
 * Created by FanYanGen on 2019-07-12 17:54
 */
@Component
public class SysConfigFallBack implements SysConfigFeignClient {

    @Override
    public ReturnEntity getConfigurationValue(Long id) {
        if (NEWS_ITEM_ID.equals(id)) {
            ReturnUtil.success("您所在的党支部发布了一条新的党务公开—{{title}}，请注意查看！");
        }
        return ReturnUtil.success();
    }

}
