package com.egovchina.partybuilding.partybuild.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/4/22 17:44
 * @description
 */
@ApiModel("更新党籍DTO")
@Accessors(chain = true)
@Data
public class RegistryDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户Id不允许为空")
    private Integer userId;

    @ApiModelProperty(value = "出党时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "出党时间不允许为空")
    private Date reduceTime;

    @ApiModelProperty(value = "出党方式 3出党、4停止党籍、5死亡、6其他")
    @NotNull(message = "出党方式不允许为空")
    private Long outType;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    @NotNull(message = "组织id不允许为空")
    private Integer deptId;

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不允许为空")
    private String username;
}
