package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author YangYingXiang on 2018/11/28
 */
@Data
@Accessors(chain = true)
public class TabPbPositiveRegist {

    /**
     * 报到Id
     */
    @TableId(type = IdType.AUTO)
    private Long positiveRegistId;

    /**
     * 社区主键
     */
    private Long communityId;

    /**
     * 人员Id
     */
    private Long userId;

    /**
     * 报到日期
     */
    private Date registDate;

    /**
     * 报到说明
     */
    private String registComment;

    /**
     * 所属组织Id
     */
    private Long fromOrgnizeId;

    /**
     * 所属组织代码
     */
    private String fromOrgnizeCode;

    /**
     * 所属组织名称
     */
    private String fromOrgnizeName;

    /**
     * 党组织所在地
     */
    private String fromOrgnizePlace;

    /**
     * 处理人
     */
    private String processMan;

    /**
     * 处理时间
     */
    private Date processTime;

    /**
     * 处理结果
     */
    private String processResult;

    /**
     * 撤销标识 1为已报到，2为已返回
     */
    private Byte revokeTag;

    /**
     * 撤销说明
     */
    private String revokeTomment;

    /**
     * 撤销时间
     */
    private Date revokeDate;

    /**
     * 有效标记
     */
    private String eblFlag;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 排序码
     */
    private Long orderNum;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户Id
     */
    private Long createUserid;

    /**
     * 创建人姓名
     */
    private String createUsername;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改用户Id
     */
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    private String updateUsername;

    /**
     * 版本
     */
    private Long version;
}
