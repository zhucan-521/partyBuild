package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("党建联席会成员组织")
public class TabPbJointMeetOrg {
    private static final String FORMAT = "yyyy-MM-dd";


    @ApiModelProperty(value="成员组织主键")
    private Long memberOrgId;

    @ApiModelProperty(value="党建联席会主键", required = true)
    private Long jointMeetId;

    @ApiModelProperty(value="组织主键")
    private Long orgId;

    @ApiModelProperty(value="组织名称")
    private String orgName;

    @ApiModelProperty(value="单位名称")
    private String unitName;

    @ApiModelProperty(value="单位id")
    private Long unitId;

    @ApiModelProperty(value="加入日期")
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date joinDate;

    @ApiModelProperty(value="有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value="删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value="排序码", hidden = true)
    @JsonIgnore
    private Long orderNum;

    @ApiModelProperty(value="数据描述", hidden = true)
    @JsonIgnore
    private String description;

    @ApiModelProperty(value="创建时间", hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value="创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value="创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value="修改时间", hidden = true)
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value="修改用户Id", hidden = true)
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value="修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value="版本", hidden = true)
    @JsonIgnore
    private Long version;

}