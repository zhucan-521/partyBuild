package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "党群VO")
@Data
public class PartyMassesVO {

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "党群名称")
    private String partyMassesName;

    @ApiModelProperty(value = "行政区划id")
    private Long administrativeDivisionId;

    @ApiModelProperty(value = "行政区划名称")
    private String administrativeDivisionName;

    @ApiModelProperty(value = "坐标")
    private String coordinate;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "服务时间")
    private String serviceHours;

    @ApiModelProperty(value = "简介")
    private String content;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

}
