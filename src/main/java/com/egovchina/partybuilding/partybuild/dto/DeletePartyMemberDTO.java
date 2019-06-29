package com.egovchina.partybuilding.partybuild.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "删除党员实体")
@Accessors(chain = true)
@Data
public class DeletePartyMemberDTO {

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "减少方式：出党3；停止党籍4；死亡5；其他6", required = true)
    @NotNull(message = "减少方式不能为空")
    private Long outType;

    @ApiModelProperty(value = "离开党组织日期 yyyy-MM-dd", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reduceTime = new Date();

    @ApiModelProperty(value = "出党方式 dict")
    private Long quitType;

    @JsonIgnore
    private Boolean whetherThisClass = true;

    @JsonIgnore
    private Long abroadId;
}
