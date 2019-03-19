package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.dto.SysDeptDto;
import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2018/11/28
 */
@ApiModel(value = "在职党员社区报到",description = "在职党员社区报到实体类")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tab_pb_positive_regist")
public class TabPbPositiveRegist extends Model<TabPbPositiveRegist> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private static final String DATA_EXAMPLE = "2018-10-30";
    @ApiModelProperty(hidden = true)
    private static final String FORMAT = "yyyy-MM-dd";

    @ApiModelProperty(value = "报到Id")
    @TableId(type = IdType.AUTO)
    private Long positiveRegistId;

    @ApiModelProperty(value = "组织主键")
    @NotNull(message = "组织主键不能为空")
    private Long deptId;

    @ApiModelProperty(value = "人员Id")
    @NotNull(message = "人员Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "报到日期")
    @NotBlank(message = "报到日期不能为空")
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private String registDate;

    @ApiModelProperty(value = "报到说明")
    private String registComment;

    @ApiModelProperty(value = "所属组织Id")
    private Long fromOrgnizeId;

    @ApiModelProperty(value = "所属组织代码")
    private String fromOrgnizeCode;

    @ApiModelProperty(value = "所属组织名称")
    private String fromOrgnizeName;

    @ApiModelProperty(value = "党组织所在地")
    private String fromOrgnizePlace;

    @ApiModelProperty(value = "处理人",hidden = true)
    private String processMan;

    @ApiModelProperty(value = "处理时间",hidden = true)
    private Date processTime;

    @ApiModelProperty(value = "处理结果",hidden = true)
    private String processResult;

    @ApiModelProperty(value = "撤销标识 1为已报到，2为已返回")
    private Byte revokeTag;

    @ApiModelProperty(value = "撤销说明")
    private String revokeTomment;

    @ApiModelProperty(value = "撤销时间")
    private Date revokeDate;

    @ApiModelProperty(value = "有效标记",hidden = true)
    private String eblFlag;

    @ApiModelProperty(value = "删除标记",hidden = true)
    private String delFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id",hidden = true)
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名",hidden = true)
    private String createUsername;

    @ApiModelProperty(value = "修改时间",hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id",hidden = true)
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名",hidden = true)
    private String updateUsername;

    @ApiModelProperty(value = "版本",hidden = true)
    private Long version;

    @Override
    protected Serializable pkVal() {
        return this.positiveRegistId;
    }
    /**
     * 非数据库字段
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "党员姓名")
    private String userName;

    @TableField(exist = false)
    @ApiModelProperty(value = "人员列表")
    private List<SysUser> sysUserList;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织列表")
    private List<SysDeptDto> sysDeptList;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织id")
    private Long rangeDeptId;

    @TableField(exist = false)
    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @TableField(exist = false)
    @ApiModelProperty(value = "附件list")
    private List<TabPbAttachment> attachmentList;

    @TableField(exist = false)
    @ApiModelProperty(value = "报到证")
    private String attachmentInstance;

    @TableField(exist = false)
    @ApiModelProperty(value = "报到证文件名字")
    private String attachmentDescription;
}
