package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "学历返回实体")
@Data
public class PartyEducationVO {
    @ApiModelProperty(value = "主键")
    private Long educationId;

    @JsonIgnore
    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "学历 dict WHCD")
    @JsonSerialize(using = DictSerializer.class)
    private Long level;

    private String levelName;
    @ApiModelProperty(value = "学位 dict XW")
    @JsonSerialize(using = DictSerializer.class)
    private Long degree;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedSchool;

    @ApiModelProperty(value = "专业")
    private String spec;
}
