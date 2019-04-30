package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 困难党员-数据传输对象
 * Created by FanYanGen on 2019/4/22 17:05
 */
@Data
@ApiModel("困难党员-数据传输对象")
public class HardshipPartyMemberDTO {

    @ApiModelProperty("困难程度")
    @NotNull(message = "困难程度不能为空")
    private Long difficultyLevel;

    @ApiModelProperty("困难记录ID")
    private Long hardshipId;

    @ApiModelProperty("组织主键")
    @NotNull(message = "组织主键不能为空")
    private Long orgId;

    @ApiModelProperty("人员Id")
    @NotNull(message = "人员ID不能为空")
    private Long userId;

    @ApiModelProperty("生活困难类型 dict KNLX")
    @NotNull(message = "生活困难类型不能为空")
    private String hardshipType;

    @ApiModelProperty("是否享受城镇最低生活保障费")
    private Long ifAllowances;

    @ApiModelProperty("是否享受优抚对象抚恤补助")
    private Long ifPension;

    @ApiModelProperty("建国前没有工作老党员")
    private Byte ifOldMember;

    @ApiModelProperty("数据描述")
    private String description;

    @ApiModelProperty("健康状况")
    private Long health;

    @ApiModelProperty("生活困难补充情况")
    private String implementation;

}
