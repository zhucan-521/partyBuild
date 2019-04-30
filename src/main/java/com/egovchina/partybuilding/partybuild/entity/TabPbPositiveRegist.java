package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2018/11/28
 */
@ApiModel(value = "在职党员社区报到",description = "在职党员社区报到实体类")
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


    private String userName;


    private List<SysUser> sysUserList;


    private List<SysDeptDto> sysDeptList;


    private Long rangeDeptId;


    private Long orgRange;


    private List<TabPbAttachment> attachments;


    private String attachmentInstance;


    private String attachmentDescription;
}
