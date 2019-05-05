package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PositiveRegistMemberQueryBean {

    @ApiModelProperty(value = "党员姓名")
    private String userName;

    @ApiModelProperty(value = "报到日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registDate;

    @ApiModelProperty(value = "组织id")
    private Long rangeDeptId;

    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @ApiModelProperty(value = "撤销标识 1为已报到，2为已返回")
    private Byte revokeTag;

}
