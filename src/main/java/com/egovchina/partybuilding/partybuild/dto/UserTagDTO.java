package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/4/22 20:40
 * @description
 */
@ApiModel("党员标记")
@Data
@Accessors(chain = true)
public class UserTagDTO {

    @ApiModelProperty
    private Long usertagId;

    @ApiModelProperty("用户ID")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "用户标签字典Id 码表值 USERTAG")
    @NotNull
    private Long tagType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建者id")
    private Long createUserid;

    @ApiModelProperty("创建者名称")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createUsername;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新用户id")
    private Long updateUserid;

    @ApiModelProperty("更新用户名称")
    private String updateUsername;
}
