package com.yizheng.partybuilding.system.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {
    /**
     * ID
     */
    protected int id;
    /**
     * 父ID
     */
    protected int parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
