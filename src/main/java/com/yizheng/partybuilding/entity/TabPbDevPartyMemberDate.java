package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbDevPartyMemberDate {

    @ApiModelProperty(hidden = true)
    private static final String FORMAT = "yyyy-MM-dd";

    private Long timeId;

    @ApiModelProperty(value = "党员发展步骤id", required = true)
    private Long hostId;

    @ApiModelProperty(value = "发展时间", required = true)
    @JsonFormat(timezone = "GMT+8")
    private Date devDate;

    @ApiModelProperty(value = "删除标记，默认0，删除为1", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建人", hidden = true)
    @JsonIgnore
    private Long createUserid;
}

