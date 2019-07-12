package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("tab_pb_flow_in")
public class TabPbFlowIn {

    private Long flowInId;

    private Long orgId;

    private Long userId;

    private Date flowInDate;

    private Long flowInType;

    private Long flowInRange;

    private String flowInReason;

    private String oldPlace;

    private String oldUnitName;

    private Long oldOrgnizeId;

    private String oldOrgnizeCode;

    private String oldOrgnizeName;

    private String oldOrgnizePhone;

    private String oldOrgnizeContactor;

    private String oldContactPhone;

    private String oldUnitPostcode;

    private Date returnDate;

    private String returnTag;

    private Long flowOutId;

    private String eblFlag;

    private String delFlag;

    private Long orderNum;

    private String description;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

    private Long flowInState;

    private Date lostTime;

    private Long linkStatus;

}