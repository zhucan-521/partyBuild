package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author guanyingxin
 */
@ApiModel("书记列表显示")
@Data
public class PartySecretarysVO {

    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "任职开始")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date positiveStart;

    @ApiModelProperty(value = "党内职务 dict DNZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveName;

    @ApiModelProperty(value = "职务级别 dict ZWJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveLevel;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "入党日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinTime;

}
