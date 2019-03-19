package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("组织关系升格临时记录实体")
@Data
public class SysDeptUpgradeTemp {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "升格组织id", required = true)
    private Long deptId;

    @ApiModelProperty(value = "原党组织名称")
    private String deptName;

    @ApiModelProperty(value = "升格后的组织名称", required = true)
    private String upgradeDeptName;

    @ApiModelProperty(value = "升格后的组织简称")
    private String upgradeShortName;

    /**
     * 删除标记，默认0，删除为1
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建人id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改人员id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改人员姓名
     */
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "下级支部字符集合，保存下级支部与单位信息")
    private String deptBranchs;

    @ApiModelProperty(value = "上级批准组织名称")
    private String approveOrgName;
}