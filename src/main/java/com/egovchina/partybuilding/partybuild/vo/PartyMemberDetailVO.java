package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("党员名册-党员概况")
public class PartyMemberDetailVO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @ApiModelProperty(value = "居住地所在社区名字")
    private String communityAddrName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "固定号码")
    private String fixPhone;

    @ApiModelProperty(value = "家庭住址")
    private String familyAddress;

    @ApiModelProperty(value = "入党所在支部名称")
    private String deptName;

    @ApiModelProperty(value = "曾任职务")
    private String positived;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    private Long identityType;

    @ApiModelProperty(value = "党组织所在社区   码表值XZQH")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgAddrName;

    @ApiModelProperty(value = "档案管理单位")
    private String filesManageUnit;

    @ApiModelProperty(value = "党籍 码表值 DJZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryStatus;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "加入党组织时间")
    private Date joinOrgTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "转正时间、正式党员时间")
    private Date regularTime;

    //书记的信息
    @ApiModelProperty("书记概况")
    private SecretarysDetailsVO secretarysDetails;

    @ApiModelProperty(value = "用户标签列表")
    private List<UserTagVO> tabPbUserTags;

}
