package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@ApiModel(value = "系统任务配置实体")
@Data
public class SysTaskConfigurer {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "任务ID dict", required = true)
    @NotNull(message = "任务ID不能为空")
    @JsonSerialize(using = DictSerializer.class)
    private String taskId;

    @ApiModelProperty(value = "履行组织ID", required = true)
    @NotNull(message = "履行组织ID不能为空")
    private String fulfillId;

    @ApiModelProperty(value = "履行组织名称")
    private String fulfillName;

    @ApiModelProperty(value = "预期次数")
    private Long expected = 1L;

    @ApiModelProperty(value = "周期 dict RWZQ", required = true)
    @NotNull(message = "周期不能为空")
    @JsonSerialize(using = DictSerializer.class)
    private Long cycle;

    @ApiModelProperty(value = "年度 yyyy", required = true)
    @NotNull(message = "年度不能为空")
    @Pattern(regexp = "\\d{4}", message = "年度格式不正确")
    private String year;

    /**
     * 创建用户id
     */
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建用户姓名")
    private String createUsername;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改用户id
     */
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    private String updateUsername;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @JsonIgnore
    private Byte delFlag;

}