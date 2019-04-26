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
 * desc: 双述双评-数据传输对象
 * Created by FanYanGen on 2019/4/24 16:17
 */
@Data
@ApiModel("双述双评论-数据传输对象")
public class CommentaryDTO {

    @ApiModelProperty(value = "ID")
    private Long commentaryId;

    @ApiModelProperty(value = "组织主键", required = true)
    @NotNull(message = "党组织不能为空")
    private Long orgId;

    @ApiModelProperty(value = "评述年份 yyyy", required = true, dataType = "Long", example = "2019")
    @NotNull(message = "评述年份不能为空")
    private Long planYear;

    @ApiModelProperty(value = "上报日期 yyyy-MM-dd", required = true, dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "上报日期不能为空")
    private Date reportDate;

    @ApiModelProperty(value = "审核日期 yyyy-MM-dd", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "评述内容")
    private String commentaryContent;

    @ApiModelProperty(value = "审核情况 dict")
    private Long checkResult;

    @ApiModelProperty(value = "审核机构ID")
    private Long checkOrg;

    @ApiModelProperty(value = "审核机构名称")
    private String checkOrgName;

    @ApiModelProperty(value = "审核人ID")
    private Long checkUser;

    @ApiModelProperty(value = "审核人名称")
    private String checkUserName;

    @ApiModelProperty(value = "审核说明")
    private String checkDesc;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
