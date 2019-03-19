package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人员实体
 *
 * @author wuyunjie
 * Date 2019-01-16 17:51
 */
@ApiModel("人员实体")
@Data
public class Personnel {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "性别 dict XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "类型 dict USERTAG")
    @JsonSerialize(using = DictSerializer.class)
    private String type;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织联系人")
    private String contactor;

    @ApiModelProperty(value = "组织联系方式")
    private String contactNumber;
}
