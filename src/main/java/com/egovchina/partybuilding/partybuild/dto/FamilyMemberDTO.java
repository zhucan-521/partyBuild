package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;


@ApiModel(value = "家庭成员对象",description = "家庭成员实体类")
@Data
public class FamilyMemberDTO {

    @ApiModelProperty(value = "家庭成员主键ID",required = true)
    @NotNull(message = "请传入relationId主键")
    private Long relationId;

    @ApiModelProperty(value = "人员主键ID")
    private Long userId;

    @ApiModelProperty(value = "称谓 码表值CW")
    private Long relation;

    @ApiModelProperty(value = "家庭成员主键")
    private Long relationUserId;

    @ApiModelProperty(value = "性别 码表值 XB")
    private Long gender;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "民族 码表值 MZ")
    private Long nation;

    @ApiModelProperty(value = "政治面貌 码表值 ZZMM")
    private Long policyFace;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "职务级别 码表值 HZZW")
    private Long positive;

    @ApiModelProperty(value = "出生日期",example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private Date birthday;

    @ApiModelProperty(value = "是否出国 1否，2是")
    private String whetherAbroad;

    @ApiModelProperty(value = " 地区名称 码表值 CGCJ")
    private Long abroad;

    @ApiModelProperty(value = " 排序码")
    private Long orderNum;
}