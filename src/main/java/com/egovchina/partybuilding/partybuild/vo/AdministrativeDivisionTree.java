package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.partybuild.util.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: WuYunJie
 * @create: 2019-05-27 10:09
 **/
@ApiModel("行政区划树实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class AdministrativeDivisionTree extends TreeNode {

    private static final long serialVersionUID = -5092437878851800667L;

    @ApiModelProperty(value = "行政区划名称")
    private String administrativeDivisionName;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

}
