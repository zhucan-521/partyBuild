package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "题库实体")
@Data
public class TabPbEduSubject {

    @ApiModelProperty(value = "题库ID")
    private Integer id;
    @ApiModelProperty(value = "题目类别Id 数据字典59116 例如 语文数学之类的")
    private Integer category;
    @ApiModelProperty(value = "题目类别Name 数据字典59116")
    private String categoryName;

    @ApiModelProperty(value = "题目标题")
    private String title;
    @ApiModelProperty(value = "试题类型 单选1  多选2  简答3(现在只有单选)")
    private Integer type;
    @ApiModelProperty(value = "试题难度 (1简单 2一般  3困难)")
    private Integer difficulty;
    @ApiModelProperty(value = "题目内容，选择题(多个用|隔开)")
    private String selects;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "答案解析")
    private String answerAnalysis;
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