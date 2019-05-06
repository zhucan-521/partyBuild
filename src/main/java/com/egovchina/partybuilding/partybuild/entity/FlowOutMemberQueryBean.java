package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "党员信息流出表")
@Data
public class FlowOutMemberQueryBean {

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "列表范围 0查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "流入地")
    private String flowToUnitName;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "状态")
    private Long flowOutState;

}