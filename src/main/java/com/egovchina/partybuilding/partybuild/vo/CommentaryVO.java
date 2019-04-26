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
 * desc: 双述双评-视图对象
 * Created by FanYanGen on 2019/4/24 16:19
 */
@Data
@ApiModel("双述双评-视图对象")
public class CommentaryVO {

    @ApiModelProperty(value = "ID")
    private Long commentaryId;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "评述年份")
    private Long planYear;

    @ApiModelProperty(value = "上报日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportDate;

    @ApiModelProperty(value = "审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "评述内容")
    private String commentaryContent;

    @ApiModelProperty(value = "审核情况")
    @JsonSerialize(using = DictSerializer.class)
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

    @ApiModelProperty(value = "组织简称")
    private String orgShortName;

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}