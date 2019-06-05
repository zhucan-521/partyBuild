package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long deptId;

    @ApiModelProperty(value = "人员类别")
    private Long identityType;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别多个逗号隔开")
    private String gender;

    @ApiModelProperty(value = "民族多个逗号隔开")
    private String nation;

    @ApiModelProperty(value = "学历,多个逗号隔开")
    private String education;

    @ApiModelProperty(value = "单位类型")
    private String unitProperty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间", example = "yyyy-MM-dd")
    private Date joinTimeBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出党时间", example = "yyyy-MM-dd")
    private Date joinTimeEnd;

    @ApiModelProperty(value = "开始年龄")
    private Integer ageBegin;

    @ApiModelProperty(value = "结束年龄")
    private Integer ageEnd;

    @ApiModelProperty(value = "1 查当前组织及其直属组织； 2 查当前组织及所有下级组织； 不传查本级；3和不传查本级。（所有前提都是有deptid的情况，没有ddeptid就没有党组织筛选）")
    private String orgRange;

    @ApiModelProperty(value = "党员标识")
    private String tagTypes;

    /**
     * index
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long index;

    /**
     * limit
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long limit;

}
