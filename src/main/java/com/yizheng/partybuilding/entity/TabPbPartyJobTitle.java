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

@ApiModel(value = "党员技术职务实体")
@Data
public class TabPbPartyJobTitle {
    @ApiModelProperty(value = "主键")
    private Long jobTitleId;

    @JsonIgnore
    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "技术职务 dict JSZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long post;

    @ApiModelProperty(value = "技术资格 dict JSZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long qualifications;

    @ApiModelProperty(value = "获得日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date getDate;

    @ApiModelProperty(value = "聘任起始日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date appointStartDate;

    @ApiModelProperty(value = "聘任终止日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date appointEndDate;

    /**
     * 删除标识 1已删除；0未删除；默认0
     */
    @JsonIgnore
    private Boolean delFlag;

    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id", hidden = true)
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名", hidden = true)
    private String createUsername;

    /**
     * 修改时间 yyyy-MM-dd HH:mm:ss
     */
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    /**
     * 修改人id
     */
    @ApiModelProperty(value = "修改人id", hidden = true)
    private Long updateUserid;

    /**
     * 修改人姓名
     */
    @ApiModelProperty(value = "修改人名字", hidden = true)
    private String updateUsername;
}