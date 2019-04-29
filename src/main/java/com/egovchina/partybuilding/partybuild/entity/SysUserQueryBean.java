package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "系统用户查询实体")
@Data
public class SysUserQueryBean {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "组织id")
    @NotNull(message = "组织id不能为空")
    private Integer deptId;

    @ApiModelProperty(value = "人员类别")
    private Long identityType;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    private Long gender;

    @ApiModelProperty(value = "民族多个逗号隔开")
    private String nation;

    @ApiModelProperty(value = "学历,多个逗号隔开")
    private String education;

    @ApiModelProperty(value = "是否台湾籍")
    private Byte isTaiwaner;

    @ApiModelProperty(value = "流动状态 码表值 LDZT")
    private Long flowStatus;

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他、 7发展中的党员")
    private String registryStatus;

    @ApiModelProperty(value = "单位类型")
    private String unitType;

    @ApiModelProperty(value = "婚姻状况,多个逗号隔开")
    private String maritalStatus;

    @ApiModelProperty(value = "是否失联")
    private Byte isLlost;

    @ApiModelProperty(value = "是否困难")
    private Byte isPoor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间",example ="yyyy-MM-dd")
    private Date joinTimeBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出党时间",example ="yyyy-MM-dd")
    @TableField(exist = false)
    private Date joinTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始年龄",example ="yyyy-MM-dd")
    private Date ageBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束年龄",example ="yyyy-MM-dd")
    private Date ageEnd;

    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织； 不传查本级；3和不传查本级。（所有前提都是有deptid的情况，没有ddeptid就没有党组织筛选）")
    private String orgRange;
}
