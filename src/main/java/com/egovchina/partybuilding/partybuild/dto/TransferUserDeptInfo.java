package com.egovchina.partybuilding.partybuild.dto;

import lombok.Data;

/**
 * 组织转接审批组织与审批人信息
 */
@Data
public class TransferUserDeptInfo {

    private Integer manageDeptId;
    private String deptName;
    private Long userId;
    private String username;
    private String phone;
}
