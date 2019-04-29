package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/4/29 9:30
 * @description
 */
@ApiModel("党籍实体类DTO")
@Accessors(chain = true)
@Data
public class MembershipDTO {

    @ApiModelProperty(value = "党籍id", hidden = true)
    private Long membershipId;

    @ApiModelProperty(value = "党员id", required = true)
    private Long userId;

    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    private Long identityType;

    @ApiModelProperty(value = "党籍处理 码表值 DJCL")
    private Long type;

    @ApiModelProperty(value = "处理原因")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建人姓名")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createUsername;
}
