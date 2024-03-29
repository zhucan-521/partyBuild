package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class MsgUpInfoQueryBean {

    @ApiModelProperty(value = "主题、专题标签 码表ZTZT")
    private String titleLabel;

    @ApiModelProperty(value = "列表范围 0查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "上报日期(结束)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date upTimeOver;

    @ApiModelProperty(value = "上报日期（开始）")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date upTime;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private List<String> labels;

}
