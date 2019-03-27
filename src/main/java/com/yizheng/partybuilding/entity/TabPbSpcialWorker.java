package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(value="党建专干")
@Data
@TableName(value="tab_pb_spcial_worker")
public class TabPbSpcialWorker {

    @ApiModelProperty(value="党建专干主键")
    private Long specialWorkerId;

    @ApiModelProperty(value="用户主键",hidden = true)
    private Long userId;

    @ApiModelProperty(value="组织id")
    private Long manageOrgId;

    @ApiModelProperty(value="接任时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date arriveTime;

    @ApiModelProperty(value="离任时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leftTime;

    @ApiModelProperty(value="有效标记",hidden = true)
    private String eblFlag;

    @ApiModelProperty(value = "删除标记,1为删除",hidden = true)
    private String delFlag;

    @ApiModelProperty(value="排序码",hidden = true)
    private Long orderNum;

    @ApiModelProperty(value="数据描述" ,hidden = true)
    private String description;

    @ApiModelProperty(value="创建时间" ,hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value="创建用户Id" ,hidden = true)
    private Long createUserid;

    @ApiModelProperty(value="修改时间" ,hidden = true)
    private String createUsername;

    @ApiModelProperty(value="修改用户Id" ,hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value="修改用户姓名" ,hidden = true)
    private Long updateUserid;

    @ApiModelProperty(value="数据描述" ,hidden = true)
    private String updateUsername;

    @ApiModelProperty(value="版本" ,hidden = true)
    private Long version;

    @ApiModelProperty(hidden = true)
    private String comment;

    }