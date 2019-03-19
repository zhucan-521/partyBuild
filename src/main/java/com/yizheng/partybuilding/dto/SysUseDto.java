package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUseDto extends SysUser {
   @ApiModelProperty(value = "所在党组织名称")
   private String deptName;

   @ApiModelProperty(value = "档案管理单位所在名称")
   private String FilesManageUnitIdName;

    @ApiModelProperty(value = "所属社区名称")
   private String communityName;
}
