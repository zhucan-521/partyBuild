package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 党员简单VO
 *
 * @author Zhang Fan
 **/
@ApiModel("党员选择VO")
@Data
public class PartyMemberChooseVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("姓名")
    private String realname;

    @ApiModelProperty("身份证号码")
    private String idCardNo;

    @ApiModelProperty("党员标识")
    private List<UserTagVO> userTags;

    @ApiModelProperty("性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty("所属组织id")
    private Long orgId;

    @ApiModelProperty("所属组织名称")
    private String orgName;

    @ApiModelProperty("居住社区")
    @JsonSerialize(using = DictSerializer.class)
    private Long communityAddr;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("人员类别")
    @JsonSerialize(using = DictSerializer.class)
    private Long identityType;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "头像2,微信端头像")
    private String avatar2;
}
