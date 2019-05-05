package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    /**
     * 组织ID
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 组织名称
	 */
    private String name;

    /**
     * 组织短名称
	 */
    private String orgShortName;

    /**
     * 组织编码
	 */
    private String orgCode;

    /**
     * 组织内部编码
	 */
    private String orgInnerCode;

    /**
     * 全路径（所有父结点）
     */
    private String fullPath;

    /**
     * 父组织ID
     */
    private Long parentId;

    /**
     * 是否是党小组 dict SYSFLAG
	 */
    private Byte isTeam;

    /**
     * 分类定等 dict FLDD
	 */
    private Long orgLevel;

    /**
     * 定等日期 yyyy-MM-dd", example = "yyyy-MM-dd
	 */
    private Date levelDate;

    /**
     * 组织状态 dict ZZZT
	 */
    private Long orgStatus;

    /**
     * 是否是城市 dict SYSFLAG
	 */
    private Byte isCity;

    /**
     * 是否是父结点
	 */
    private Byte isParent;

    /**
     * 组织类别 dict ZZLB
	 */
    private Long orgnizeProperty;

    /**
     * 依赖关系 dict KZ41
	 */
    private String dependencyRelation;

    /**
     * 组织书记ID
	 */
    private Long orgnizeMasterId;

    /**
     * 组织书记名称
	 */
    private String orgnizeMaster;

    /**
     * 联系人ID
	 */
    private Long contactorId;

    /**
     * 联系人名称
	 */
    private String contactor;

    /**
     * 办公面积
	 */
    private Long officeSize;

    /**
     * 通讯地址
	 */
    private String address;

    /**
     * 所在单位
	 */
    private String unitName;

    /**
     * 所在单位ID
	 */
    private Long unitId;

    /**
     * 所属社区ID
	 */
    private Long communityAddr;

    /**
     * 建立日期 yyyy-MM-dd", example = "yyyy-MM-dd
	 */
    private Date foundedDate;

    /**
     * 建立文号
	 */
    private String foundedFileNumber;

    /**
     * 联系方式
	 */
    private String contactNumber;

    /**
     * 传真号码
	 */
    private String faxNumber;

    /**
	 * 邮编
	 */
    private String postCode;

    /**
     * 原系统UUID
	 */
    private String systemUuid;

    /**
     * 组织描述
	 */
    private String description;

    /**
     * 组织所在坐标
	 */
    private String position;

    /**
     * 组织范围坐标
	 */
    private String positionRange;

    /**
     * 建立原因
	 */
    private String foundedReason;

    /**
     * 党组织所在单位情况 dict DZZSZDWQK
	 */
    private Long unitState;

    /**
     * 联点领导ID
	 */
    private Long leaderUserId;

    /**
     * 结对共建组织
	 */
    private Long partnerOrgId;

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