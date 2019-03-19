package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 组织阵地dto实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "组织阵地实体")
@Data
public class TabDeptPositionDto {

    @ApiModelProperty(value = "组织ID", required = true)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date foundedDate;

    @ApiModelProperty(value = "图片附件数量")
    private Integer imgAnnexNum;

    @ApiModelProperty(value = "布置照片")
    private List<TabPbAttachment> arrangePhotos;
}
