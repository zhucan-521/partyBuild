package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/6/24 15:45
 * @description 组织历史信息图
 */
@ApiModel("组织历史信息图")
@Accessors(chain = true)
@Data
public class OrgnizeLifeGraphVO {

    @ApiModelProperty("时间段")
    private String timeline;

    @ApiModelProperty("数据集")
    private List<OrgnizeLifeGraphVO> lines;

}
