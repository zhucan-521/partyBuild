package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbEduSite;
import com.yizheng.partybuilding.entity.TabPbEduSiteActivity;
import com.yizheng.partybuilding.entity.TabPbEduSiteDevice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 远教站点dto实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "远教站点dto实体")
@Data
public class TabPbEduSiteDto extends TabPbEduSite {

    @ApiModelProperty(value = "站点活动集")
    private List<TabPbEduSiteActivity> activitys;

    @ApiModelProperty(value = "站点设备集")
    private List<TabPbEduSiteDevice> devices;

}
