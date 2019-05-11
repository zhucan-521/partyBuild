package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("书记信息显示")
@Data
public class SecretaryInfoVO {

    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "出生地")
    private String bornPlace;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "工作时间")
    private Date workDate;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "党内职务")
    private List<PositivesVO> positivesVOs;

    @ApiModelProperty(value = "家庭成员")
    private List<FamilyMemberVO> familys;

    @ApiModelProperty(value = "书记奖励")
    private List<RewardsVO> rewardsVOs;

    @ApiModelProperty(value = "书记惩罚")
    private List<PunishmentVO> punishmentVOs;

}
