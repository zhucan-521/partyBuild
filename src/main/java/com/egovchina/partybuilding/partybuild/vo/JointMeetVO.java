package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("党建联席会VO")
public class JointMeetVO {
    @ApiModelProperty(value = "党建联席会主键",hidden = true)
    private Long jointMeetId;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "成立时间" )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date foundedDate;

    @ApiModelProperty(value = "数据描述")
    private String description;

}