package com.egovchina.partybuilding.partybuild.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 组织树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode implements Serializable {
    private String name;
    private Byte isParent;
    private Byte isBranch;
}
