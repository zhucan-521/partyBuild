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
 * 组织变动VO
 *
 * @author wuyunjie
 * Date 2019-04-22 21:46
 */
@Data
@ApiModel("组织变动VO")
public class OrgChangeVO {
    @ApiModelProperty(value = "变动Id")
    private Long changeId;

    @ApiModelProperty(value = "组织主键")
    private Long deptId;

    @ApiModelProperty(value = "变动类型 码表值 ZZBD")
    @JsonSerialize(using = DictSerializer.class)
    private Long changeType;

    @ApiModelProperty(value = "原上级Id")
    private Long oldSuperiorId;

    @ApiModelProperty(value = "原上级Name")
    private String oldSuperiorName;

    @ApiModelProperty(value = "现上级Id")
    private Long nowSuperiorId;

    @ApiModelProperty(value = "现上级name")
    private String nowSuperiorName;

    @ApiModelProperty(value = "组织全称")
    private String orgnizeName;

    @ApiModelProperty(value = "组织简称")
    private String shortName;

    @ApiModelProperty(value = "组织编码")
    private String orgnizeCode;

    @ApiModelProperty(value = "变动日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date changeDate;

    @ApiModelProperty(value = "文号")
    private String fileNumber;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

    @ApiModelProperty(value = "创建人")
    private String createUsername;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> attachments;
}
