package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/4/16 16:27
 * @description 党员党籍实体类
 */
@ApiModel(value = "党员党籍实体")
@TableName("tab_pb_party_membership")
@Data
public class TabPbPartyMembership implements Serializable {

    @ApiModelProperty(value = "党籍id")
    @TableId(value = "membership_id", type = IdType.AUTO)
    private Long membershipId;

    @ApiModelProperty(value = "党员id", required = true)
    private Long userId;

    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long identityType;

    @ApiModelProperty(value = "党籍处理")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;

    @ApiModelProperty(value = "处理原因")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建人id", hidden = true)
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String createUsername;
}
