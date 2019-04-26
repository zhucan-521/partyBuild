package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 组织升格调整VO
 *
 * @author wuyunjie
 * Date 2019-01-27 12:02
 */
@ApiModel(value = "组织升格调整VO")
@Data
public class OrgUpgradeVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "升格组织id")
    private Long deptId;

    @ApiModelProperty(value = "原党组织名称")
    private String deptName;

    @ApiModelProperty(value = "升格后的组织名称")
    private String upgradeDeptName;

    @ApiModelProperty(value = "升格后的组织简称")
    private String upgradeShortName;

    @ApiModelProperty(value = "下级支部字符集合，保存下级支部与单位信息")
    private String deptBranchs;

    @ApiModelProperty(value = "上级批准组织名称")
    private String approveOrgName;

    @ApiModelProperty(value = "变动类型 码表值 ZZBD")
    @JsonSerialize(using = DictSerializer.class)
    private Long changeType;

    @ApiModelProperty(value = "变动日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date changeDate;

    @ApiModelProperty(value = "文号")
    private String fileNumber;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> attachments;
}
