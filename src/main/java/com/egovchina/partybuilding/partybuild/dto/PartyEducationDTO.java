package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "学历信息实体")
@Data
public class PartyEducationDTO {
    @ApiModelProperty(value = "主键")
    private Long educationId;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "学历 dict XL")
    @NotNull(message = "学历不能为空")
    private Long level;

    @ApiModelProperty(value = "学位 dict ")
    private Long degree;

    @ApiModelProperty(value = "毕业院校")
    @NotNull(message = "毕业院校不能为空")
    private String graduatedSchool;

    @ApiModelProperty(value = "专业")
    private String spec;

    @ApiModelProperty(value = "入学时间 ", example = "2018-05-05")
    @NotNull(message = "入学时间不能为空")
    private Date admissionTime;

    @ApiModelProperty(value = "毕业时间 ", example = "2018-05-05")
    @NotNull(message = "毕业时间不能为空")
    private Date graduateTime;
}
