package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 党员选择查询实体
 *
 * @author Zhang Fan
 **/
@ApiModel("党员选择查询实体")
@Data
public class PartyMemberChooseQueryBean {

    @ApiModelProperty("姓名")
    private String realname;

    @ApiModelProperty("身份证号码")
    private String idCardNo;

    @ApiModelProperty("党员标识 dict USERTAG")
    private Long userTag;

    @ApiModelProperty("列表范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织 默认查当前")
    private String orgRange;

    @ApiModelProperty("组织id")
    private Long orgId;

    @ApiModelProperty("排除已绑定账号的 1 是；0 否；默认0")
    private Byte excludeBinding = 0;

    /**
     * index
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long index;

    /**
     * limit
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long limit;

}
