package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * desc: 分类定等-视图对象
 * Created by FanYanGen on 2019/4/24 21:58
 */
@Data
@ApiModel("分类定等-视图对象")
public class OrgClassifyVO {

    @ApiModelProperty(value = "主键ID")
    private Long orgClassifyId;

    @ApiModelProperty(value = "组织ID")
    private Long deptId;

    @ApiModelProperty(value = "定等级别")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgLevel;

    @ApiModelProperty(value = "定等日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date levelDate;

    @ApiModelProperty(value = "定等描述（标题）")
    private String description;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "创建时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;


}
