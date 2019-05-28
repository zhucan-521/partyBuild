package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 组织变动DTO
 *
 * @author wuyunjie
 * Date 2019-04-22 21:23
 */
@Data
@ApiModel(value = "组织变动DTO")
public class OrgChangeDTO {
    @ApiModelProperty(value = "变动Id")
    private Long changeId;

    @ApiModelProperty(value = "角色所属组织主键", required = true)
    @NotNull(message = "角色所属组织主键不能为空")
    private Long orgId;

    @ApiModelProperty(value = "组织主键", required = true)
    @NotNull(message = "组织主键不能为空")
    private Long deptId;

    @ApiModelProperty(value = "变动类型 码表值 ZZBD", required = true)
    @NotNull(message = "变动类型不能为空")
    private Long changeType;

    @ApiModelProperty(value = "原上级组织Id")
    private Long oldSuperiorId;

    @ApiModelProperty(value = "现上级组织Id", required = true)
    @NotNull(message = "现上级组织Id不能为空")
    private Long nowSuperiorId;

    @ApiModelProperty(value = "组织全称", required = true)
    @NotBlank(message = "组织全称不能为空")
    private String orgnizeName;

    @ApiModelProperty(value = "组织简称", required = true)
    @NotBlank(message = "组织简称不能为空")
    private String shortName;

    @ApiModelProperty(value = "组织编码")
    private String orgnizeCode;

    @ApiModelProperty(value = "变动日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "变动日期不能为空")
    private Date changeDate;

    @ApiModelProperty(value = "文号")
    private String fileNumber;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> attachments;
}
