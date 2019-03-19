package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "党员民主评议实体")
public class TabPbActivitiesAssess {

    @ApiModelProperty(value = "评议ID")
    private Long id;

    @ApiModelProperty(value = "组织ID")
    private Long deptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "评议年度 yyyy", example = "yyyy")
    @NotNull(message = "评议年度不能为空")
    private Integer years;

    @ApiModelProperty(value = "党员ID")
    private Long userId;

    @ApiModelProperty(value = "党员姓名")
    private String username;

    @ApiModelProperty(value = "党员类型 1预备党员 2正式党员")
    private String type;

    @ApiModelProperty(value = "党员评议结果 dict ZS_PYJG YB_PYJG")
    @JsonSerialize(using = DictSerializer.class)
    private Long assessResult;

    @ApiModelProperty(value = "文档附件")
    private List<TabPbAttachment> docAttachments;

    @ApiModelProperty(value = "图片附件")
    private List<TabPbAttachment> imgAttachments;

    @ApiModelProperty(value = "视频附件")
    private List<TabPbAttachment> videoAttachments;

    /**
     * 签到时间
     */
    @JsonIgnore
    private Date signInTime;

    /**
     * host_id
     */
    private Long hostId;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建用户Id
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
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

}