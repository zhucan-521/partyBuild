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
@Data
public class TabPbPartyMembership implements Serializable {

    private Long membershipId;

    private Long userId;

    private Long identityType;

    private Long type;

    private String reason;

    private Date createTime;

    private Long createUserid;

    private String createUsername;
}
