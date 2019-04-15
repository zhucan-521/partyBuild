package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "民主评议审核实体")
public class TabPbActivitiesAssessVerify {

    @ApiModelProperty(value = "审核ID")
    private Long id;

    @ApiModelProperty(value = "评议ID")
    @NotNull(message = "评议ID不能为空")
    private Long assessId;

    @ApiModelProperty(value = "审核组织ID")
    private Long deptId;

    @ApiModelProperty(value = "审核组织名称")
    private String deptName;

    @ApiModelProperty(value = "审核人ID")
    private Long createUserid;

    @ApiModelProperty(value = "审核人姓名")
    private String createUsername;

    @ApiModelProperty(value = "审核结果 dict SHJG")
    @NotNull(message = "审核结果不能为空")
    @JsonSerialize(using = DictSerializer.class)
    private Long content;

    @ApiModelProperty(value = "审核说明")
    private String remark;

    @ApiModelProperty(value = "审核日期", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 有效标记
     */
    private String eblFlag;

}