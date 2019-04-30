package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class PositiveRegistMemberVO {

    @ApiModelProperty(value = "报到Id")
    private Long positiveRegistId;

    @ApiModelProperty(value = "组织主键")
    @NotNull(message = "组织主键不能为空")
    private Long deptId;

    @ApiModelProperty(value = "人员Id")
    @NotNull(message = "人员Id不能为空")
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "报到日期")
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

    @ApiModelProperty(value = "处理人")
    private String processMan;

    @ApiModelProperty(value = "处理时间")
    private Date processTime;

    @ApiModelProperty(value = "处理结果")
    private String processResult;

    @ApiModelProperty(value = "撤销标识 1为已报到，2为已返回")
    private Byte revokeTag;

    @ApiModelProperty(value = "撤销说明")
    private String revokeTomment;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "撤销时间")
    private Date revokeDate;

    @ApiModelProperty(value = "党员姓名")
    private String userName;

    @ApiModelProperty(value = "人员列表")
    private List<SysUser> sysUserList;

    @ApiModelProperty(value = "组织列表")
    private List<SysDeptDto> sysDeptList;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

//    @ApiModelProperty(value = "报到证")
//    private String attachmentInstance;
//
//    @ApiModelProperty(value = "报到证文件名字")
//    private String attachmentDescription;

    @ApiModelProperty(value = "0报道日期大于当前日期，1报道日期小于等于当前日期")
    private Long isEnableBack;


}
