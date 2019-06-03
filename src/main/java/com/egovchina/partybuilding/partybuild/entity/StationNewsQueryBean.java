package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/5/31 14:20
 * @description 党员获取在自己组织下的消息列表QueryBean
 */
@ApiModel("党员获取在自己组织下的消息列表QueryBean")
@Data
public class StationNewsQueryBean {

    @ApiModelProperty(value = "接收者组织id", required = true)
    @NotNull(message = "接收者组织id不能为空")
    private Long receiverOrgId;

    @ApiModelProperty(value = "发送时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendTime;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息类别")
    private Long type;

    @ApiModelProperty("组织范围  1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织）")
    private Long rangeDeptId;

    @ApiModelProperty("组织id")
    private Long orgRange;

}
