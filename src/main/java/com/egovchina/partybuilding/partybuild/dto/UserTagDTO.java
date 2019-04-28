package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/22 20:40
 * @description
 */
@ApiModel("党员标记DTO")
@Data
@Accessors(chain = true)
public class UserTagDTO {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "用户标签字典Id 码表值 USERTAG")
    @NotNull(message = "标签不能为空")
    private List<Long> tagTypes;

//    @ApiModelProperty(value = "创建时间", hidden = true)
//    private Date createTime;
//
//    @ApiModelProperty(value = "创建者id", hidden = true)
//    private Long createUserid;
//
//    @ApiModelProperty(value = "创建者名称", hidden = true)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private String createUsername;
//
//    @ApiModelProperty(value = "更新时间", hidden = true)
//    private Date updateTime;
//
//    @ApiModelProperty(value = "更新用户id", hidden = true)
//    private Long updateUserid;
//
//    @ApiModelProperty(value = "更新用户名称", hidden = true)
//    private String updateUsername;
}
