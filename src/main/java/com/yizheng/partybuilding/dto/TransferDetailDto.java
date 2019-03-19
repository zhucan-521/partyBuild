package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbTransfer;
import com.yizheng.partybuilding.entity.TabPbTransferItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 组织转接申请详情
 */
@Data
@ApiModel("组织转接申请详情")
public class TransferDetailDto {

    private TabPbTransfer transfer;
    private List<TabPbTransferItem> itemList;
}
