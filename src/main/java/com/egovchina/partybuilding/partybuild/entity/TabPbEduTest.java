package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "试卷实体")
@Data
public class TabPbEduTest {

    @ApiModelProperty(value = "试卷ID")
    private Integer id;
    @ApiModelProperty(value = "试卷类型-dict，与题目类型是一个字典")
    private Integer category;
    @ApiModelProperty(value = "试卷类型-Name 数据字典59116")
    private String categoryName;
    @ApiModelProperty(value = "试卷难度，1简单 2一般  3困难")
    private Integer difficulty;
    @ApiModelProperty(value = "试卷名称")
    private String title;
    @ApiModelProperty(value = "试卷总分")
    private Integer totalScore;
    @ApiModelProperty(value = "组卷方式，1选题组卷、2随机组卷")
    private Integer type;
    @ApiModelProperty(value = "考试时长")
    private Integer duration;

    @ApiModelProperty(value = "试卷及格分数")
    private Integer passScore;
    @ApiModelProperty(value = "试卷开考时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    @ApiModelProperty(value = "试卷结考时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    @ApiModelProperty(value = "试卷备注说明")
    private String remark;

    @ApiModelProperty(value = "发布状态，默认未发布")
    private Boolean publishFlag;
    @ApiModelProperty(value = "删除标识 0为正常  1为删除")
    private String delFlag;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    @JsonIgnore
    private Long createUserid;
    @ApiModelProperty(value = "创建人")
    private String createUsername;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Long updateUserid;
    @JsonIgnore
    private String updateUsername;

}