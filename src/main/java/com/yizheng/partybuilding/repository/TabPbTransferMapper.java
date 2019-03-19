package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TransferListSpDto;
import com.yizheng.partybuilding.dto.TransferListWaitingDto;
import com.yizheng.partybuilding.entity.TabPbTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbTransferMapper {

    int insertSelective(TabPbTransfer record);

    TabPbTransfer getById(Long transferId);

    int updateById(TabPbTransfer record);

    List<TabPbTransfer> getPageList();

    //获取组织关系转出列表
    List<TabPbTransfer> getFlowOutPageList(TabPbTransfer transfer);

    //获取组织关系接收列表
    List<TabPbTransfer> getFlowInPageList(TabPbTransfer transfer);

    //组织关系接转 - 即将办理工作 - 列表
    List<TransferListSpDto> getHandlePageList(TransferListSpDto spDto);

    //组织关系接转 - 审批记录查询 - 列表
    List<TabPbTransfer> getSpRecordPageList(TransferListSpDto spDto);

    //组织关系接转 - 待办工作 - 列表
    List<TransferListWaitingDto> getWaitingPageList(@Param("deptId") Long deptId, @Param("username") String username, @Param("idCardNo") String idCardNo);

    //组织关系接转 - 未办结工作 - 列表
    List<TransferListWaitingDto> getNotFinshedPageList(@Param("deptId") Long deptId, @Param("username") String username, @Param("idCardNo") String idCardNo);

    //审批流程全部通过后，修改组织关系转接接收状态未已接转
    int updateReceiveFlagById(@Param("transferId") Long transferId, @Param("receiveFlag") String receiveFlag);

    //组织关系接转 - 撤销
    int updateRevokeFlagById(@Param("transferId") Long transferId, @Param("revokeFlag") String revokeFlag);
}