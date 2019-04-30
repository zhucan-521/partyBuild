package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@ApiModel("党员标记集合DTO")
@Data
@Accessors(chain = true)
public class UserTagDTO {

    @ApiModelProperty(value = "党员标记id", hidden = true)
    private Long usertagId;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "用户标签字典Id 码表值 USERTAG")
    @NotNull(message = "标签不能为空")
    private List<Long> tagTypes;
}
