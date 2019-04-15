package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: huang
 * Date: 2018/12/20
 */
@Data
@Accessors(chain = true)
@ApiModel("Echarts")
public class EchartsDto {

    private String name;

    private String value;
}
