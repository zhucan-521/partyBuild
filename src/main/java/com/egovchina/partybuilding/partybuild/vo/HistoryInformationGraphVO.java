package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/6/11 16:15
 * @description 党员历史信息图VO
 */
@ApiModel("党员历史信息图VO")
@Data
@Accessors(chain = true)
public class HistoryInformationGraphVO {

    @ApiModelProperty("时间段")
    private String timeline;

    @ApiModelProperty("数据集合")
    private List<HistoryInformationVO> lines;

}
