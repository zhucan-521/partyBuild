package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.partybuilding.entity.TabPbSpcialWorker;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="党建专干Dto")
@Data
public class TabSpecialWorkerResultDto extends TabPbSpcialWorker {


    @ApiModelProperty(value = "所属组织ID")
    private Long deptId;

    @ApiModelProperty(value = "组织联系人")
    private String contactor;

    @ApiModelProperty(value = "组织联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "性别 码表XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "组织名称 供显示用的字符串")
    private String deptName;

    @ApiModelProperty(value = "所属部门名称  供显示用的字符串")
    private String manageOrgName;

    @ApiModelProperty(value = "变更状态，-1离职，1在职")
    private int status;

    @ApiModelProperty(value = "列表范围 0查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "组织ID")
    private Long rangeDeptId;

}
