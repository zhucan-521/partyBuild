package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * desc: 党小组-数据传输对象
 * Created by FanYanGen on 2019/4/28 10:31
 */
@Data
@ApiModel("党小组-数据传输对象")
public class PartyGroupDTO {

    @ApiModelProperty(value = "党小组ID")
    private Long groupId;

    @ApiModelProperty(value = "党小组名称", required = true)
    @NotNull(message = "党小组名称不能为空")
    private String groupName;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "是否撤销")
    private Integer isRevoke;

    @ApiModelProperty(value = "撤销人")
    private String revokeName;

    @ApiModelProperty(value = "排序码")
    private Integer orderNum;

    @ApiModelProperty(value = "撤销时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date revokeTime;

    @ApiModelProperty(value = "建立时间 (如果不记得建立时间则默认为本支部成立时间)", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date buildTime;

    @ApiModelProperty(value = "说明")
    private String remarks;

    @ApiModelProperty(value = "党小组成员信息")
    @Valid
    private List<PartyGroupMemberInfoDTO> members;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
