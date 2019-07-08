package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/6/11 16:23
 * @description 历史信息VO
 */
@ApiModel("历史信息VO")
@Data
@Accessors(chain = true)
public class HistoryInformationVO {

    @ApiModelProperty(value = "id", hidden = true)
    @JsonIgnore
    private Long id;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;

    @ApiModelProperty("标题")
    private String label;

    @ApiModelProperty("子标题")
    private String subLabel;

}
