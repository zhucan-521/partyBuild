package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("党员发展信息")
public class TabPbDevPartyMember {
    @ApiModelProperty(value = "主键id")
    private Long dpId;

    @ApiModelProperty(value = "环节状态,即hostId后的字段", required = true)
    private Long status;

    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    private Long hostId11;

    private Long hostId12;

    private Long hostId21;

    private Long hostId22;

    private Long hostId23;

    private Long hostId24;

    private Long hostId31;

    private Long hostId32;

    private Long hostId33;

    private Long hostId34;

    private Long hostId35;

    private Long hostId41;

    private Long hostId42;

    private Long hostId43;

    private Long hostId44;

    private Long hostId45;

    private Long hostId46;

    private Long hostId47;

    private Long hostId51;

    private Long hostId52;

    private Long hostId53;

    private Long hostId54;

    private Long hostId55;

    private Long hostId56;

    private Long hostId57;


    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织id")
    private Long deptId;
}