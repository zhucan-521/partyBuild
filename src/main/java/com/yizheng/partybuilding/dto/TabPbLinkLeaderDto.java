package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbActivities;
import com.yizheng.partybuilding.entity.TabPbLinkLeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:
 * 联点信息dto
 *
 * @outhor wuyunjie
 * @create 2018-12-17 16:00
 */
@Data
@ApiModel("组织联点领导对象DTO")
public class TabPbLinkLeaderDto extends TabPbLinkLeader {

    @ApiModelProperty(value = "活动对象集合 增加活动需传活动id")
    private List<TabPbActivities> activitiesList;

    @ApiModelProperty(value = "连接领导姓名")
    private String realName;

    @ApiModelProperty(value = "组织名称")
    private String name;
}
