package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhucan
 */
@ApiModel("书记列表显示")
@Data
public class SecretarysVO {

    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职开始")
    private Date positiveStart;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职结束")
    private Date positiveFinished;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "党内职务 dict DNZW")
    private Long positiveName;

    @ApiModelProperty(value = "职务级别 dict ZWJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveLevel;

    @ApiModelProperty(value = "任职 机构/党组织 名称")
    private String positiveOrgName;

    @ApiModelProperty(value = "单位类别 码表值DWLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long unitProperty;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;

}
