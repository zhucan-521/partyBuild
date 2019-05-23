package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * desc: 双述双评详情-视图对象
 * Created by FanYanGen on 2019-05-22 22:40
 */
@Data
@ApiModel("双述双评详情-视图对象")
public class CommentaryDetailsVO {

    @ApiModelProperty(value = "主键ID")
    private Long commentaryId;

    @ApiModelProperty(value = "组织主键ID")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织简称")
    private String orgShortName;

    @ApiModelProperty(value = "评述年份")
    private String planYear;

    @ApiModelProperty(value = "上报日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportDate;

    @ApiModelProperty(value = "审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    @ApiModelProperty(value = "审核情况")
    @JsonSerialize(using = DictSerializer.class)
    private Long checkResult;

    @ApiModelProperty(value = "审核机构ID")
    private Long checkOrg;

    @ApiModelProperty(value = "审核机构名称")
    private String checkOrgName;

    @ApiModelProperty(value = "审核人ID")
    private Long checkUser;

    @ApiModelProperty(value = "审核人名称")
    private String checkUserName;

    @ApiModelProperty(value = "审核说明")
    private String checkDesc;

}
