package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.partybuilding.entity.SysDeptUpgradeTemp;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 组织升格调整dto
 *
 * @author wuyunjie
 * Date 2019-01-27 12:02
 */
@ApiModel(value = "组织升格调整dto")
@Data
public class OrganizationUpgradeAndChangeDto extends SysDeptUpgradeTemp {

    @ApiModelProperty(value = "变动类型 码表值 ZZBD")
    @JsonSerialize(using = DictSerializer.class)
    private Long changeType;

    @ApiModelProperty(value = "变动日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date changeDate;

    @ApiModelProperty(value = "文号", required = true)
    private String fileNumber;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> tabPbAttachmentList;
}
