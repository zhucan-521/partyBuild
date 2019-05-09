package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * desc: 党小组-视图对象
 * Created by FanYanGen on 2019/4/28 10:32
 */
@Data
@ApiModel("党小组-视图对象")
public class PartyGroupVO {

    @ApiModelProperty(value = "组织名字")
    private String orgName;

    @ApiModelProperty(value = "组织简称")
    private String orgShortName;

    @ApiModelProperty(value = "党小组ID")
    private Long groupId;

    @ApiModelProperty(value = "党小组名称")
    private String groupName;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "是否撤销")
    private Integer isRevoke;

    @ApiModelProperty(value = "撤销人")
    private String revokeName;

    @ApiModelProperty(value = "撤销时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date revokeTime;

    @ApiModelProperty(value = "建立时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date buildTime;

    @ApiModelProperty(value = "说明")
    private String remarks;

    @ApiModelProperty(value = "该党小组成员集合")
    private List<PartyGroupMemberVO> members;

}
