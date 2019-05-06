package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserTagVO {
    @ApiModelProperty(value = "党员标记id")
    private Long usertagId;


    private String value;

    private String label;

}
