package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyEducation;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyJobTitle;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyWork;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * desc: 党员DTO
 * Created by FanYanGen on 2019/4/11 16:00
 */
@Data
@AllArgsConstructor
public class SysUserDto {

    @ApiModelProperty(value = "党员")
    private SysUser sysUser;

    @ApiModelProperty(value = "学历信息")
    private List<TabPbPartyEducation> educationList;

    @ApiModelProperty(value = "技术职务")
    private List<TabPbPartyJobTitle> jobTitleList;

    @ApiModelProperty(value = "工作信息")
    private List<TabPbPartyWork> workList;

}
