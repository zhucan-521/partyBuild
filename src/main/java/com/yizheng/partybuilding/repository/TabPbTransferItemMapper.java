package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbTransferItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbTransferItemMapper {

    int insert(TabPbTransferItem record);

    int insertSelective(TabPbTransferItem record);

    TabPbTransferItem selectById(Long itemId);

    TabPbTransferItem selectByIdAndTransferId(@Param("itemId") Long itemId, @Param("transferId") Long transferId);

    int updateBySelective(TabPbTransferItem record);

    int updateByPrimaryKey(TabPbTransferItem record);

    List<TabPbTransferItem> listByTransferId(Long transferId);

    int updateIsSpFlag(@Param("itemId") Long itemId, @Param("isSpFlag") String isSpFlag);

    //获取已审批的数量
    int approvalCount(@Param("transferId") Long transferId, @Param("isSpFlag") String isSpFlag);

    //获取下一个待审批的信息
    TabPbTransferItem getNextItemByTransferIdAndIndex(@Param("transferId") Long transferId, @Param("shIndex") Integer shIndex);
}