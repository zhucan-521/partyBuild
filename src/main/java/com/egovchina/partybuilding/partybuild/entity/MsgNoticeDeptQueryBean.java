package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "接收下达文件通知党组织")
public class MsgNoticeDeptQueryBean {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "接收党组织id",required = true)
    private Long deptId;


    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private Long orgRange;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    private Date stateTime;

    @ApiModelProperty(value = "下达通知id")
    private Long noticeId;

}