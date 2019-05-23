package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "双述双评")
@Accessors(chain = true)
public class TabPbDoubleCommentary {

    private Long commentaryId;

    private Long orgId;

    private String planYear;

    private Date reportDate;

    private Date checkDate;

    private String description;

    private String commentaryContent;

    private Long checkResult;

    private Long resultSituation;

    private Long checkOrg;

    private String checkOrgName;

    private Long checkUser;

    private String checkUserName;

    private String checkDesc;

    private String orgName;

    //==================

    /**
     * 文档数
     **/
    private Integer docNum;

    /**
     * 图片数
     **/
    private Integer imgNum;

    /**
     * 视频数
     **/
    private Integer videoNum;

    /**
     * 附件实体集合
     **/
    private List<TabPbAttachment> attachments;

    //==================

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