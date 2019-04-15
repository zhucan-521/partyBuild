package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbTransfer;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransferItem;
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
