package com.egovchina.partybuilding.partybuild.v1.dto;

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
@ApiModel("更新党籍")
@Accessors(chain = true)
@Data
public class MembershipDTO {

    @ApiModelProperty("用户ID")
    @NotNull
    private Integer userId;

    @ApiModelProperty(value = "出党时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date reduceTime;

    @ApiModelProperty(value = "出党方式 3出党、4停止党籍、5死亡、6其他")
    @NotNull
    private Long outType;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    @NotNull
    private Integer deptId;

    @ApiModelProperty(value = "身份证号")
    @NotNull
    private String username;
}
