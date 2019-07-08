package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/6/24 17:25
 * @description 组织信息
 */
@ApiModel("组织信息")
@Accessors(chain = true)
@Data
public class OrgnizeInformationVO {

    @ApiModelProperty(value = "id",hidden = true)
    @JsonIgnore
    private Long id;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;

    @ApiModelProperty(value = "标题")
    private String label;

    @ApiModelProperty("子标题")
    private String subLabel;

}
