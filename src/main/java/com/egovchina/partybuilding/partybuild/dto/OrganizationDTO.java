package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 组织DTO实体
 *
 * @Author Zhang Fan
 **/
@Data
@ApiModel(value = "组织DTO实体")
public class OrganizationDTO {

    @ApiModelProperty(value = "组织ID")
    private Long deptId;

    @ApiModelProperty(value = "组织名称", required = true)
    @NotBlank(message = "组织名称不能为空")
    private String name;

    @ApiModelProperty(value = "组织短名称", required = true)
    @NotBlank(message = "组织短名称不能为空")
    private String orgShortName;

    @ApiModelProperty(value = "组织编码", required = true)
    @NotBlank(message = "组织编码不能为空")
    private String orgCode;

    @ApiModelProperty(value = "父组织ID")
    @NotNull(message = "父组织不能为空")
    private Long parentId;

    @ApiModelProperty(value = "分类定等 dict FLDD")
    private Long orgLevel;

    @ApiModelProperty(value = "定等日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date levelDate;

    @ApiModelProperty(value = "组织状态 dict ZZZT")
    private Long orgStatus;

    @ApiModelProperty(value = "是否是城市 dict SYSFLAG")
    private Byte isCity;

    @ApiModelProperty(value = "组织类别 dict ZZLB")
    @NotNull(message = "组织类别不能为空")
    private Long orgnizeProperty;

    @ApiModelProperty(value = "依赖关系 dict KZ41")
    private String dependencyRelation;

    @ApiModelProperty(value = "组织书记ID")
    private Long orgnizeMasterId;

    @ApiModelProperty(value = "组织书记名称")
    private String orgnizeMaster;

    @ApiModelProperty(value = "联系人ID")
    private Long contactorId;

    @ApiModelProperty(value = "联系人名称")
    private String contactor;

    @ApiModelProperty(value = "办公面积")
    private Long officeSize;

    @ApiModelProperty(value = "通讯地址")
    private String address;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "所在单位ID")
    private Long unitId;

    @ApiModelProperty(value = "所属社区ID")
    private Long communityAddr;

    @ApiModelProperty(value = "建立日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date foundedDate;

    @ApiModelProperty(value = "建立文号")
    private String foundedFileNumber;

    @ApiModelProperty(value = "联系方式")
    private String contactNumber;

    @ApiModelProperty(value = "传真号码")
    private String faxNumber;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "组织描述")
    private String description;

    @ApiModelProperty(value = "组织所在坐标")
    private String position;

    @ApiModelProperty(value = "组织范围坐标")
    private String positionRange;

    @ApiModelProperty(value = "建立原因")
    private String foundedReason;

    @ApiModelProperty(value = "党组织所在单位情况 dict DZZSZDWQK")
    @NotNull(message = "党组织所在单位情况不能为空")
    private Long unitState;

    @ApiModelProperty(value = "单位信息实体")
    private List<UnitInfoDTO> units;

}
