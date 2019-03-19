package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel("直播实体")
@Data
public class TabPbEduLive {

    @ApiModelProperty(value = "直播id")
    private Long liveId;

    @ApiModelProperty(value = "区域-QY")
    @JsonSerialize(using = DictSerializer.class)
    private Long region;

    @ApiModelProperty(value = "排课计划-PKJH")
    @JsonSerialize(using = DictSerializer.class)
    private Long plan;

    @ApiModelProperty(value = "直播名称",required = true)
    private String name;

    @ApiModelProperty(value = "直播描述",required = true)
    private String description;

    @ApiModelProperty(value = "直播封面，直接保存文件服务id")
    private String cover;

    @ApiModelProperty(value = "直播状态-ZBZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long state;

    @ApiModelProperty(value = "第三方资源地址")
    private String resAddress;

    @ApiModelProperty(value = "课程开始时间", example = "yyyy-MM-dd HH:mm",required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "课程结束时间", example = "yyyy-MM-dd HH:mm",required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "直播地点")
    private String address;

    @ApiModelProperty(value = "直播老师")
    private String teacherId;

    @ApiModelProperty(value = "直播分类-ZBFL，可保存多个数据字典值，多个用逗号分隔")
    @JsonSerialize(using = DictSerializer.class)
    private String type;

    /**
     * 删除标记，默认0，删除为1
     */
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "是否置顶，默认false，置顶为true")
    private boolean isTop;

    /**
     * 排序
     */
    @JsonIgnore
    private Long orderNum;

    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
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

    @ApiModelProperty(value = "录播资源，直播完后，在直播管理中添加录制的视频资源")
    private Long recordId;

    @ApiModelProperty(value = "课件集合")
    private List<TabPbAttachment> listCourseware;
}