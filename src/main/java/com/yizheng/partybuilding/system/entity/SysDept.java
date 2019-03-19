package com.yizheng.partybuilding.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "组织实体")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "组织ID")
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;

    @ApiModelProperty(value = "组织名称")
    @NotBlank(message = "组织名称不能为空")
    private String name;

    @ApiModelProperty(value = "组织短名称")
    private String orgShortName;

    @ApiModelProperty(value = "组织编码")
    private String orgCode;

    @ApiModelProperty(value = "组织内部编码")
    private String orgInnerCode;

    @JsonIgnore
    private String fullPath;

    @ApiModelProperty(value = "父组织ID")
    @NotNull(message = "父组织不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "是否是党小组 dict SYSFLAG")
    @JsonSerialize(using = DictSerializer.class)
    private Byte isTeam;

    @ApiModelProperty(value = "分类定等 dict FLDD")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgLevel;

    @ApiModelProperty(value = "定等日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date levelDate;

    @ApiModelProperty(value = "组织状态 dict ZZZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgStatus;

    @ApiModelProperty(value = "是否是城市 dict SYSFLAG")
    @JsonSerialize(using = DictSerializer.class)
    private Byte isCity;

    @ApiModelProperty(value = "是否是父结点")
    private Byte isParent;

    @ApiModelProperty(value = "是否是支部")
    @TableField(exist = false)
    private Byte isBranch;

    @ApiModelProperty(value = "组织类别 dict ZZLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgnizeProperty;

    @ApiModelProperty(value = "依赖关系 dict KZ41")
    @JsonSerialize(using = DictSerializer.class)
    private String dependencyRelation;

    @ApiModelProperty(value = "组织书记ID")
    private Long orgnizeMasterId;

    @ApiModelProperty(value = "组织书记名称")
    private String orgnizeMaster;

    @ApiModelProperty(value = "联系人ID")
    private Long contactorId;

    @ApiModelProperty(value = "联系人名称")
    private String contactor;

    @ApiModelProperty(value = "办公面积")
    private Long officeSize;

    @ApiModelProperty(value = "通讯地址")
    private String address;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "所在单位ID")
    private Long unitId;

    @ApiModelProperty(value = "单位类别")
    @JsonSerialize(using = DictSerializer.class)
    @TableField(exist = false)
    private Long unitProperty;

    @ApiModelProperty(value = "所属社区ID")
    @JsonSerialize(using = DictSerializer.class)
    private Long communityAddr;

    @ApiModelProperty(value = "建立日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date foundedDate;

    @ApiModelProperty(value = "建立文号")
    private String foundedFileNumber;

    @ApiModelProperty(value = "联系方式")
    private String contactNumber;

    @ApiModelProperty(value = "传真号码")
    private String faxNumber;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "原系统UUID")
    private String systemUuid;

    @ApiModelProperty(value = "组织描述")
    private String description;

    @ApiModelProperty(value = "组织所在坐标")
    private String position;

    @ApiModelProperty(value = "组织范围坐标")
    private String positionRange;

    @ApiModelProperty(value = "建立原因")
    private String foundedReason;

    @ApiModelProperty(value = "党组织所在单位情况 dict DZZSZDWQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long unitState;

    @ApiModelProperty(value = "联点领导ID")
    private Long leaderUserId;

    @ApiModelProperty(value = "结对共建组织")
    private Long partnerOrgId;

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @ApiModelProperty(value = "排序值")
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
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

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;

    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }

    /**
     * 是否支部
     */
    public boolean ifBranch() {
        return this.orgnizeProperty == 989L || this.orgnizeProperty == 992L
            || this.orgnizeProperty == 995L || this.orgnizeProperty == 1002L
            || this.orgnizeProperty == 1003L || this.orgnizeProperty == 1007L
            || this.orgnizeProperty == 1008L;
    }
}