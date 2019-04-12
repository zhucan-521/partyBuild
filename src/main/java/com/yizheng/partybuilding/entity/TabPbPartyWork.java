package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value = "党员工作信息实体")
@Data
public class TabPbPartyWork {
    @ApiModelProperty(value = "主键")
    private Long workId;

    @JsonIgnore
    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "工作单位")
    private String unit;

    @ApiModelProperty(value = "岗位")
    private String post;

    @ApiModelProperty(value = "一线情况 dict YXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long frontLine;

    @ApiModelProperty(value = "新阶层类型 dict NEWCLASS")
    @JsonSerialize(using = DictSerializer.class)
    private Long stratum;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 删除标识 1已删除；0未删除；默认0
     */
    @JsonIgnore
    private Boolean delFlag;

    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建人id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改人id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改人姓名
     */
    @JsonIgnore
    private String updateUsername;
}