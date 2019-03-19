package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("党联席")
public class TabPbJointMeet {

    @ApiModelProperty(hidden = true)
    private static final String FORMAT = "yyyy-MM-dd";

    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @ApiModelProperty(value = "党建联席会主键",hidden = true)
    private Long jointMeetId;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "成立时间" )
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date foundedDate;

    @ApiModelProperty(value = "有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "排序码", hidden = true)
    @JsonIgnore
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value = "修改用户id", hidden = true)
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "版本", hidden = true)
    @JsonIgnore
    private Long version;


}