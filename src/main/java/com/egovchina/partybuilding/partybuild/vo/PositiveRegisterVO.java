package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "党员报到VO")
@Data
public class PositiveRegisterVO {

    @ApiModelProperty(value = "id")
    private Long positiveRegistId;

    @ApiModelProperty(value = "人员id")
    private Long userId;

    @ApiModelProperty(value = "党员姓名")
    private String realname;

    @ApiModelProperty(value = "性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "人员类别")
    @JsonSerialize(using = DictSerializer.class)
    private Long identityType;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "组织id")
    private Long deptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "报到日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registDate;

    @ApiModelProperty(value = "报到说明")
    private String registComment;

    @ApiModelProperty(value = "所属组织Id")
    private Long fromOrgnizeId;

    @ApiModelProperty(value = "所属组织代码")
    private String fromOrgnizeCode;

    @ApiModelProperty(value = "所属组织名称")
    private String fromOrgnizeName;

    @ApiModelProperty(value = "党组织所在地")
    private String fromOrgnizePlace;

    @ApiModelProperty(value = "撤销标识 1为已报到，2为已返回")
    private Byte revokeTag;

    @ApiModelProperty(value = "撤销说明")
    private String revokeTomment;

    @ApiModelProperty(value = "撤销时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date revokeDate;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;
}
