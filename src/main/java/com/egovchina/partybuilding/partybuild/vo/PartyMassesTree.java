package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.partybuild.util.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: WuYunJie
 * @create: 2019-05-21 17:17
 **/
@ApiModel("党群树实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class PartyMassesTree extends TreeNode {

    @ApiModelProperty("党群id")
    private Long partyMassesId;

    @ApiModelProperty("党群名称")
    private String PartyMassesName;

}
