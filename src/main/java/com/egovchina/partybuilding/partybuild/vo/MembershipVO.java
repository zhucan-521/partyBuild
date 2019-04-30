package com.egovchina.partybuilding.partybuild.vo;

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
 * @author create by GuanYingxin on 2019/4/23 17:11
 * @description
 */
@ApiModel("党籍实体类VO")
@Accessors(chain = true)
@Data
public class MembershipVO {

    @ApiModelProperty(value = "党籍id")
    private Long membershipId;

    @ApiModelProperty(value = "党员id", required = true)
    private Long userId;

    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long identityType;

    @ApiModelProperty(value = "党籍处理 码表值 DJCL")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;

    @ApiModelProperty(value = "处理原因")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建人姓名")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String createUsername;
}
