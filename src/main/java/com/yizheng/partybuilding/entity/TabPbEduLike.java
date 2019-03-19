package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value = "点赞表")
public class TabPbEduLike {

    @ApiModelProperty(value = "点赞主键")
    private Long likeId;

    @ApiModelProperty(value = "业务类型" ,example = "1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训")
    private String likeType;

    @ApiModelProperty(value = "业务id")
    private Long busId;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "点赞时间")
    private Date createTime;

    private String delFlag;

}