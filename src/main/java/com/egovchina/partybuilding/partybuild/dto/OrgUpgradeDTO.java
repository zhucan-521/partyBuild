package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
public class OrgUpgradeDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "升格组织id", required = true)
    @NotNull(message = "升格组织id不能为空")
    private Long deptId;

    @ApiModelProperty(value = "原党组织名称")
    private String deptName;

    @ApiModelProperty(value = "升格后的组织名称", required = true)
    @NotNull(message = "升格后的组织名称不能为空")
    private String upgradeDeptName;

    @ApiModelProperty(value = "升格后的组织简称", required = true)
    @NotNull(message = "升格后的组织简称不能为空")
    private String upgradeShortName;

    @ApiModelProperty(value = "下级支部字符集合，保存下级支部与单位信息")
    private String deptBranchs;

    @ApiModelProperty(value = "上级批准组织名称")
    private String approveOrgName;

    @ApiModelProperty(value = "变动日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "变动日期不能为空")
    private Date changeDate;

    @ApiModelProperty(value = "文号", required = true)
    @NotNull(message = "建立文号不能为空")
    private String fileNumber;

    @ApiModelProperty(value = "变动原因", required = true)
    @NotNull(message = "变动原因不能为空")
    private String changeReason;

    @ApiModelProperty(value = "附件集合", required = true)
    @NotNull(message = "附件不能为空")
    private List<TabPbAttachment> attachments;
}
