package com.egovchina.partybuilding.partybuild.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeNode implements Serializable {

    @ApiModelProperty("id")
    protected Long id;

    @ApiModelProperty("父id")
    protected Long parentId;

    protected List<TreeNode> children;

    public void add(TreeNode node) {
        children.add(node);
    }
}
