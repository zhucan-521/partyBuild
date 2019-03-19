package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "组织调整实体")
@TableName("tab_pb_orgnize_change")
@Data
@Accessors(chain = true)
public class TabPbOrgnizeChange {

    @ApiModelProperty(value = "变动Id")
    private Long changeId;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long deptId;

    @ApiModelProperty(value = "变动类型 码表值 ZZBD", required = true)
    @JsonSerialize(using = DictSerializer.class)
    private Long changeType;

    @ApiModelProperty(value = "原上级Id")
    private Long oldSuperiorId;

    @ApiModelProperty(value = "原上级Name")
    private String oldSuperiorName;

    @ApiModelProperty(value = "现上级Id")
    private Long nowSuperiorId;

    @ApiModelProperty(value = "现上级name")
    private String nowSuperiorName;

    @ApiModelProperty(value = "组织全称")
    private String orgnizeName;

    @ApiModelProperty(value = "组织简称")
    private String shortName;

    @ApiModelProperty(value = "组织编码")
    private String orgnizeCode;

    @ApiModelProperty(value = "变动日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date changeDate;

    @ApiModelProperty(value = "文号")
    private String fileNumber;

    @ApiModelProperty(value = "有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id", hidden = true)
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "版本", hidden = true)
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

}