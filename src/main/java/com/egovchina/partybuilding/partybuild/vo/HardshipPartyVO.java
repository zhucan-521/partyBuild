package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 困难党员-视图对象
 * Created by FanYanGen on 2019/4/22 21:43
 */
@Data
@ApiModel("困难党员-视图对象")
public class HardshipPartyVO {

    @ApiModelProperty("困难程度")
    @JsonSerialize(using = DictSerializer.class)
    private Long difficultyLevel;

    @ApiModelProperty("困难记录ID")
    private Long hardshipId;

    @ApiModelProperty("组织主键")
    private Long orgId;

    @ApiModelProperty("人员Id")
    private Long userId;

    @ApiModelProperty("生活困难类型")
    @JsonSerialize(using = DictSerializer.class)
    private String hardshipType;

    @ApiModelProperty("是否享受诚征最低生活保障费")
    private Long ifAllowances;

    @ApiModelProperty("是否享受优抚对象抚恤补助")
    private Long ifPension;

    @ApiModelProperty("建国前没有工作老党员")
    private Byte ifOldMember;

    @ApiModelProperty("数据描述")
    private String description;

    @ApiModelProperty("健康状况")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @ApiModelProperty("生活困难补充情况")
    private String implementation;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("组织名称")
    private String orgName;

}
