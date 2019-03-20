package com.yizheng.partybuilding.dto;


import com.baomidou.mybatisplus.annotations.TableField;
import com.yizheng.partybuilding.entity.TabPbActivities;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbParticipant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author Jiang An
 **/
@Data
@ApiModel("组织生活Dto")
public class PartyOrganizationActivitiesDto extends TabPbActivities {

    @ApiModelProperty("附件集合")
    private List<TabPbAttachment> annexs;

    @ApiModelProperty("上级组织名称")
    private String superiorName;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("书记")
    private String orgnizeMaster;

    @ApiModelProperty("参会率")
    private Long attendanceRate;

    @ApiModelProperty("人员表")
    @NotNull(message = "人员不能空")
    private List<TabPbParticipant> tabPbParticipant;

    @ApiModelProperty("参加的人数")
    private Long functionAttendance;

    @ApiModelProperty("未参加的人数")
    private Long absenteeNumber;

    @ApiModelProperty("总人数")
    private Long headcount;

    @ApiModelProperty("活动所有参会人员姓名")
    private String joinNameString;

    @ApiModelProperty("活动所有未参会人员姓名")
    private String unjoinNameString;

    @ApiModelProperty("身份证")
    private String idCardNo;

    @ApiModelProperty(value = "图片集合")
    private List<TabPbAttachment> pictures;

    @ApiModelProperty(value = "文档集合")
    private List<TabPbAttachment> documents;

    @ApiModelProperty(value = "视频集合")
    private List<TabPbAttachment> videos;

    @ApiModelProperty(value = "结对组织ID")
    private Long pairOrgId;

    @ApiModelProperty(value = "结对共建组织名称")
    private String pairOrgName;

    //=========非数据库字段===============

    @TableField(exist = false)
    @ApiModelProperty(value = "文档")
    private Long doc;

    @TableField(exist = false)
    @ApiModelProperty(value = "照片")
    private Long photo;

    @TableField(exist = false)
    @ApiModelProperty(value = "视频")
    private Long video;

    @TableField(exist = false)
    @ApiModelProperty(value = "社区名称")
    private String name;

    //======================================

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private Long orgRange;

    @ApiModelProperty(value = "范围组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "主持人/组织人")
    private String moderatorName;

    @ApiModelProperty(value = "主持人Id")
    private Long moderatorId;

    @ApiModelProperty(value = "组织人数据ID")
    private Long moderatorDataId;

    @ApiModelProperty(value = "多个人员Id")
    private String userIds;

    @ApiModelProperty(value = "签到状态  2代表未签到，1代表已签到人,0代表全部的")
    private String signType;
}
