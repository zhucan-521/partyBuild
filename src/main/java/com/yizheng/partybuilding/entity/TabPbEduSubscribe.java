package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "预约实体")
@Data
public class TabPbEduSubscribe {

    @ApiModelProperty(value = "主键")
    private Long subscribeId;

    @ApiModelProperty(value = "业务类型 1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训")
    private String likeType;

    @ApiModelProperty(value = "业务id")
    private Long busId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "扩展信息")
    private String extendInfo;


    @ApiModelProperty(value = "预约时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "预约人姓名")
    private String subscribeName;

    @ApiModelProperty(value = "预约人联系电话")
    private String cellPhone;

    @ApiModelProperty(value = "预约主题")
    private String title;

    @ApiModelProperty(value = "预约事由")
    private String remark;

    @JsonIgnore
    private String delFlag;



}