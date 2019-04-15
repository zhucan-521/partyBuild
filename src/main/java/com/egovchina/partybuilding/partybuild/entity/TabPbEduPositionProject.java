package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "特色项目实体")
@Data
public class TabPbEduPositionProject {
    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "基地ID")
    private Long positionId;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "时长 dict SC")
    @JsonSerialize(using = DictSerializer.class)
    private String duration;

    @ApiModelProperty(value = "项目简介")
    private String introduction;

    @ApiModelProperty(value = "项目封面(文件服务id)")
    private String cover;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

}