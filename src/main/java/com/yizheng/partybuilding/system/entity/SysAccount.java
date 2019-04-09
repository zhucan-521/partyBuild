package com.yizheng.partybuilding.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yizheng.commons.util.BeanUtil;
import com.yizheng.partybuilding.util.PasswordCodecUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "系统后台用户表")
@Data
@TableName("sys_account")
@Accessors(chain = true)
public class SysAccount  implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "党员表主键id")
    private Integer sysUserId;

    @ApiModelProperty(value = "身份证")
    @NotEmpty(message = "身份证不能为空")
    private String idCardNo;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    @NotEmpty(message = "用户名不能为空")
    private String realname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "随机盐")
    private String salt;

    @ApiModelProperty(value = "简介")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "管理党组织ID")
    private Integer manageDeptId;

    @ApiModelProperty(value = "微信openid")
    private String wxOpenid;

    @ApiModelProperty(value = "QQ openid")
    private String qqOpenid;

    @ApiModelProperty(value = "XZQH数据字典值，后台帐号管理某一区域（区县、乡镇）的党代表")
    private Integer representDeptId;

    @ApiModelProperty(value = "删除状态，0-正常，1-删除")
    private String delFlag;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "修改用户Id", hidden = true)
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 复制一个对象
     * @param other 其他对象
     * @return
     */
    public static SysAccount copyOf(SysAccount other) {
        SysAccount sysAccount = new SysAccount();
        BeanUtil.copyPropertiesIgnoreNull(other, sysAccount);
        sysAccount.setUsername(other.getRealname());
        if (StringUtils.isNotEmpty(other.getPassword())) {
            sysAccount.setPassword(PasswordCodecUtil.encode(other.getPassword()));
        }
        return sysAccount;
    }
}