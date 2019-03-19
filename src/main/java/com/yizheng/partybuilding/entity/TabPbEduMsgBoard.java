package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@ApiModel(value = "留言板")
@Data
public class TabPbEduMsgBoard {


    @ApiModelProperty(hidden = true)
    private static final String format = "yyyy-MM-dd";

    @ApiModelProperty(value = "留言业务id")
    private Long busId;

    @ApiModelProperty("留言板id")
    private Long msgId;
    @ApiModelProperty("留言党员id")
    private Long userId;
    @ApiModelProperty("1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训")
    private String msgType;
   @ApiModelProperty("标题")
    private String title;

   @ApiModelProperty("内容")
    private String content;

   @ApiModelProperty("创建时间")
   @DateTimeFormat(pattern = format)
   @JsonFormat(pattern = format)
    private Date createTime;



}