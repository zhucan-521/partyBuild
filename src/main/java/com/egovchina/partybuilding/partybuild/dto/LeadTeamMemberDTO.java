package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 领导班子成员DTO
 *
 * @author GuanYingxin
 **/
@ApiModel("领导班子成员DTO")
@Data
public class LeadTeamMemberDTO {

    @ApiModelProperty(value = "班子成员id")
    private Long memberId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "领导班子id", required = true)
    @NotNull(message = "班子id不能为空")
    private Long leadTeamId;

    @ApiModelProperty("职务级别 dict JB")
    private Long rank;

    @ApiModelProperty(value = "人员id", required = true)
    @NotNull(message = "人员id不能为空")
    private Long userId;

    @ApiModelProperty("人员姓名")
    private String personName;

    @ApiModelProperty(value = "党内职务主键", required = true)
    @NotNull(message = "职务id不能为空")
    private Long positiveId;

    @ApiModelProperty(value = "党内职务名称", required = true)
    @NotBlank(message = "党内职务名称不能为空")
    private String positiveName;

    @ApiModelProperty("任职方式")
    private Long tenureMode;

    @ApiModelProperty(value = "任职时间 yyyy-MM-dd", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tenureBegin;

    @ApiModelProperty("任职年限")
    private Long tenureDuration;

    @ApiModelProperty(value = "离任时间 yyyy-MM-dd", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tenureLeave;

    @ApiModelProperty("批准文号")
    private String approvalNumber;

    @ApiModelProperty("是否兼任村委会委员")
    private Byte asCommitteeMember = new Byte("0");

    @ApiModelProperty("是否兼任村委主任")
    private Byte asCommitteeDirector = new Byte("0");;

    @ApiModelProperty("班子职务排序")
    private Long leadMemberOrder;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "头像")
    private String avatar;

}
