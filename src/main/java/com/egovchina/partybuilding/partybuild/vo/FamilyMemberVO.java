package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhucan
 */
@ApiModel(value = "家庭成员对象",description = "家庭成员实体类")
@Data
public class FamilyMemberVO implements Serializable {

    @ApiModelProperty(value = "家庭成员主键ID",required = true)
    private Long relationId;

    @ApiModelProperty(value = "人员主键ID")
    private Long userId;

    @ApiModelProperty(value = "称谓 码表值CW")
    @JsonSerialize(using = DictSerializer.class)
    private Long relation;

    @ApiModelProperty(value = "家庭成员主键",hidden = true)
    private Long relationUserId;

    @TableField(exist = false)
    @ApiModelProperty(value = "姓名")
    private String username;


    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;


    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;


    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;


    @ApiModelProperty(value = "政治面貌 码表值 ZZMM")
    @JsonSerialize(using = DictSerializer.class)
    private Long policyFace;


    @ApiModelProperty(value = "手机号码")
    private String phone;


    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "职务级别 码表值 HZZW")
    private Long positive;


    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;


    @ApiModelProperty(value = "是否出国 1否，2是")
    private String whetherAbroad;


    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = " 地区名称 码表值 CGCJ")
    private Long abroad;

    @ApiModelProperty(value = " 排序码")
    private Long orderNum;




}