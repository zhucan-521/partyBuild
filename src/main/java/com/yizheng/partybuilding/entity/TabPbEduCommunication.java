package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "学习交流模块")
@Data
@Accessors(chain = true)
public class TabPbEduCommunication implements Serializable {

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "类别：学习建议1、问卷调查2、心得体会3")
    private Long type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(hidden = true)
    private String delFlag;

    @ApiModelProperty(hidden = true)
    private Long orderNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Long createUserid;

    @ApiModelProperty(hidden = true)
    private String createUsername;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private Long updateUserid;

    @ApiModelProperty(hidden = true)
    private String updateUsername;

    @ApiModelProperty(value = "内容")
    private String content;

}