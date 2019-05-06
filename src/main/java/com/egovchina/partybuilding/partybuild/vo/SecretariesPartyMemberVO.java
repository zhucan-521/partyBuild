package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SecretariesPartyMemberVO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Long deptId;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "加入党组织时间")
    private Date joinOrgTime;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "固定号码")
    private String fixPhone;

    @TableField(exist = false)
    @ApiModelProperty(value = "入党所在支部名称")
    private String joinOrgName;

    @TableField(exist = false)
    @ApiModelProperty(value = "人员职务集合")
    private List<SecretariesPostivesVO> positives;


}
