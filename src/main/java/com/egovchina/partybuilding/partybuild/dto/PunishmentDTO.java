package com.egovchina.partybuilding.partybuild.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(value = "处分DTO")
@Data
public class PunishmentDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "punishment_id", type = IdType.AUTO)
    private Long punishmentId;

    @ApiModelProperty(value = "处分日期",example ="yyyy-MM-dd")
    @NotNull(message = "处分日期不能为空!")
    private Date punishDate;

    @ApiModelProperty(value = "处分名称")
    @NotNull(message = "处分名称不能为空!")
    private String punishName;

    @ApiModelProperty(value = "是否移交司法机关：1 是;0 否(字符串只能有一个长度哟)", required = true,example = "0")
    @NotNull(message = "是否移交司法机关不能为空! 1 是;0 否(字符串只能有一个长度哟)")
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
