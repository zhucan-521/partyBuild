package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbActivities;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbParticipant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/03
 */
@Data
@ApiModel(value = "社区活动信息dto实体")
public class TabPbActivitiesDto extends TabPbActivities {


    @ApiModelProperty(value = "人员信息List")
    private List<TabPbParticipant> participantList;

    @ApiModelProperty(value = "附件list")
    private List<TabPbAttachment> attachmentList;

    @ApiModelProperty(value = "原所在组织")
    private String name;

    @ApiModelProperty(value = "报到组织")
    private String reportOrgName;

    @ApiModelProperty(value = "文档")
    private Long doc;

    @ApiModelProperty(value = "照片")
    private Long photo;

    @ApiModelProperty(value = "视频")
    private Long video;

    @ApiModelProperty(value = "组织id")
    private Long rangeDeptId;

    @ApiModelProperty(value = "范围")
    private String orgRange;

    @ApiModelProperty(value = "党员姓名")
    private String username;

    @ApiModelProperty(value = "是否直属组织")
    private Long deptState;

}
