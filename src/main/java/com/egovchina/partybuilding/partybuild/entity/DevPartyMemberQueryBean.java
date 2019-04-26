package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 出国出境模块-查询条件
 * Created by FanYanGen on 2019/4/23 15:19
 */
@Data
@ApiModel("出国出境模块-查询条件")
public class DevPartyMemberQueryBean {

    @ApiModelProperty(value = "增加方式: 1: 新发展 2:恢复党籍(归国) 3:恢复党籍(重新取得联系) 4:其他原因增加", required = true)
    private Long addType;

    @ApiModelProperty(value = "党员名称", required = true)
    private String userName;

    @ApiModelProperty(value = "身份证号", required = true)
    private String idCardNo;

    @ApiModelProperty(value = "是否失联0,1")
    private Short isLost;

}
