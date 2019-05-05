package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author YangYingXiang on 2018/11/28
 */
@Data
@Accessors(chain = true)
@TableName("tab_pb_positive_regist")
public class TabPbPositiveRegist {

    private Long positiveRegistId;

    private Long deptId;

    private Long userId;

    private Date registDate;

    private String registComment;

    private Long fromOrgnizeId;

    private String fromOrgnizeCode;

    private String fromOrgnizeName;

    private String fromOrgnizePlace;

    private String processMan;

    private Date processTime;

    private String processResult;

    private Byte revokeTag;

    private String revokeTomment;

    private Date revokeDate;

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
}
