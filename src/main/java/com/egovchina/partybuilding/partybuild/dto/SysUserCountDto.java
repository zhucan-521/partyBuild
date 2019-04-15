package com.egovchina.partybuilding.partybuild.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author YangYingXiang on 2019/01/22
 */
@ApiModel(value = "党员统计")
@Data
@Accessors(chain = true)
public class SysUserCountDto extends Model<SysUserCountDto> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织id")
    private Long deptId;

    @ApiModelProperty(value = "直管党员")
    private Long directPartyUser;

    @ApiModelProperty(value = "流动党员")
    private Long flowUser;

    @ApiModelProperty(value = "在职党员")
    private Long positiveUser;

    @ApiModelProperty(value = "班子成员")
    private Long leadTeamMemberUser;

    @ApiModelProperty(value = "困难党员")
    private Long hardshipUser;

    @ApiModelProperty(value = "退休党员")
    private Long retiredUser;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
