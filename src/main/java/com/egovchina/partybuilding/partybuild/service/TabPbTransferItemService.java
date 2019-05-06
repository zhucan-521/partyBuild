package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.TransferApprovalDto;
import com.egovchina.partybuilding.partybuild.dto.TransferItemDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransfer;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransferItem;

import java.util.List;

@Deprecated
public interface TabPbTransferItemService {

    /**
     * 根据所在组织与目标组织，生成审批流程
     *
     * @param transfer
     * @return
     */
    int add(TabPbTransfer transfer);

    /**
     * 审批
     *
     * @return
     */
    int approvalById(TransferItemDto itemDto);

    /**
     * 获取审批信息
     *
     * @param itemId
     * @return
     */
    TabPbTransferItem getById(Long itemId);

    /**
     * 获取审批信息
     *
     * @param itemId
     * @param transferId
     * @return
     */
    TabPbTransferItem getById(Long itemId, Long transferId);

    /**
     * @param transferId
     * @return
     */
    List<TabPbTransferItem> listByTransferId(Long transferId);

    /**
     * 组织关系接转-审批操作
     *
     * @param approvalDto
     */
    void transferApproval(TransferApprovalDto approvalDto);

    /**
     * 获取组织关系-上级是否审批
     *
     * @return
     */
    boolean isApproval(Long transferId);
}
