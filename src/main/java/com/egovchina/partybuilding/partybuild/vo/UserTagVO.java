package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserTagVO {
    @ApiModelProperty(value = "党员标记id")
    private Long usertagId;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "字典标签")
    private String label;

    @ApiModelProperty(value = "字典id")
    private Integer id;

}
