package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.dto.TransferApprovalDto;
import com.yizheng.partybuilding.dto.TransferItemDto;
import com.yizheng.partybuilding.entity.TabPbTransfer;
import com.yizheng.partybuilding.entity.TabPbTransferItem;

import java.util.List;

public interface ITabPbTransferItemService {

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
