package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("处分返回实体对象")
@Data
public class PunishmentVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "punishment_id", type = IdType.AUTO)
    private Long punishmentId;

    @ApiModelProperty(value = "处分日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date punishDate;

    @ApiModelProperty(value = "处分名称", required = true)
    @JsonSerialize(using = DictSerializer.class)
    private String punishName;

    @ApiModelProperty(value = "是否移交司法机关：1 是;0 否(字符串只能有一个长度哟)", required = true,example = "0")
    private String judiciaryFlag;

    @ApiModelProperty(value = "处分机构", required = true)
    private String punishOrg;

    @ApiModelProperty(value = "处分机构Id", required = true)
    private Long punishOrgId;

    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @ApiModelProperty(value = "备注：处分说明")
    private String comment;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;
}
