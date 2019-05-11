package com.egovchina.partybuilding.partybuild.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * desc: 党员信息数据传输对象
 * Created by FanYanGen on 2019/4/22 10:05
 */
@ApiModel("补录用户实体DTO")
@Data
public class PartyDTO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "人员类别 码表值 RYLB",required = true)
    @NotNull(message = "人员类别不能为空")
    private Long identityType;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "申请人姓名",required = true)
    @NotNull(message = "申请人姓名不能为空")

    private String realname;

    @ApiModelProperty(value = "身份证号码",required = true)
    @NotNull(message = "身份证号码不能为空")
    private String idCardNo;

    @ApiModelProperty(value = "性别 码表值 XB",required = true)
    @NotNull(message = "性别不能为空")
    private Long gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期",example ="yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "民族 码表值 MZ",required = true)
    @NotNull(message = "民族不能为空")
    private Long nation;

    @ApiModelProperty(value = "手机号",required = true)
    @NotNull(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "固定号码")
    private String fixPhone;

    @ApiModelProperty(value = "家庭住址")
    private String familyAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间",required = true,example ="yyyy-MM-dd")
    @NotNull(message = "加入党组织时间不能为空")
    private Date joinTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "加入党组织时间", required = true, example = "yyyy-MM-dd")
    private Date joinOrgTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @ApiModelProperty(value = "转正时间、正式党员时间",required = true,example ="yyyy-MM-dd")
    private Date regularTime;

    @ApiModelProperty(value = "居住地所在社区   码表值XZQH")
    private Long communityAddr;

    @ApiModelProperty(value = "曾任职务")
    private String positived;

    @ApiModelProperty(value = "档案管理单位名称 ", required = true)
    @NotNull(message = "档案管理单位名称不能为空")
    private Long filesManageUnit;

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他、 7发展中的党员", required = true)
    @NotNull(message = "党籍不能为空")
    private Long registryStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户标签列表")
    private UserTagDTO userTags;

    @ApiModelProperty(value = "组织ID ,党支部Id", required = true)
    private Long deptId;

    @ApiModelProperty(value = "是否失联")
    private Byte isLlost;

    @ApiModelProperty(value = "流动状态 码表值 LDZT")
    private Long flowStatus;

    @ApiModelProperty(value = "工作简历")
    private String workResumes;

    @ApiModelProperty(value = "婚姻状况 码表值 FYZK")
    private Long maritalStatus;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    private Long health;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    private Long ancestorPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工作时间", example = "2018-05-24")
    private Date workDate;

}
