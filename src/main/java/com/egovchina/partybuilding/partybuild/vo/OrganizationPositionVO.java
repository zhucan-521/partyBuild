package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 组织阵地VO实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "组织阵地VO实体")
@Data
public class OrganizationPositionVO {

    @ApiModelProperty(value = "组织ID")
    private Integer deptId;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "办公面积")
    private Long officeSize;

    @ApiModelProperty(value = "位置")
    private String address;

    @ApiModelProperty(value = "经纬度")
    private String position;

    @ApiModelProperty(value = "建立日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date foundedDate;

    @ApiModelProperty(value = "图片附件数量")
    private Integer imgAnnexNum;

    @ApiModelProperty(value = "布置照片")
    private List<TabPbAttachment> attachments;
}
