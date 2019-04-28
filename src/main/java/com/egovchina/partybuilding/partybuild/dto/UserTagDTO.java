package com.egovchina.partybuilding.partybuild.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("标签DTO实体")
@Data
public class UserTagDTO {

    @ApiModelProperty(value = "字典id")
    @TableField(exist = false)
    private Integer id;

    private Long usertagId;

    @NotNull(message = "标签用户主键不能为空!")
    private Long userId;

    @NotNull(message = "标签类型不能为空!")
    @ApiModelProperty(value = "用户标签字典Id")
    private Long tagType;
}
