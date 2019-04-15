package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TransferDetailDto;
import com.egovchina.partybuilding.partybuild.dto.TransferListSpDto;
import com.egovchina.partybuilding.partybuild.dto.TransferListWaitingDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransfer;
import com.github.pagehelper.PageInfo;

public interface ITabPbTransferService {

    /**
     * 生成转接信息
     * @param transfer
     * @return
     */
    int add(TabPbTransfer transfer);

    int edit(TabPbTransfer transfer);

    TabPbTransfer getById(Long transferId);

    PageInfo<TabPbTransfer> getPageList(Page page);

    /**
     * 获取组织接转详情信息，包含审批信息
     *
     * @param transferId
     * @return
     */
    TransferDetailDto getTransferDetailById(Long transferId);

    /**
     * 组织关系接转 - 接收、转出 - 列表
     *
     * @param flowType 0接收列表、1转出列表
     * @param transfer
     * @param page
     * @return
     */
    PageInfo<TabPbTransfer> getPageList(int flowType, TabPbTransfer transfer, Page page);

    /**
     * 组织关系接转 - 待办工作 - 列表
     *
     * @param deptId
     * @param username
     * @param idCardNo
     * @param page
     * @return
     */
    PageInfo<TransferListWaitingDto> getWaitingPageList(Long deptId, String username, String idCardNo, Page page);


    /**
     * 组织关系接转 - 未办结工作 - 列表
     *
     * @param deptId
     * @param username
     * @param idCardNo
     * @param page
     * @return
     */
    PageInfo<TransferListWaitingDto> getNotFinshedPageList(Long deptId, String username, String idCardNo, Page page);

    /**
     * 组织关系接转 - 即将办理工作 - 列表
     *
     * @param handle
     * @param page
     * @return
     */
    PageInfo<TransferListSpDto> getHandlePageList(TransferListSpDto handle, Page page);

    /**
     * 组织关系接转 - 审批记录查询 - 列表
     *
     * @param spDto
     * @param page
     * @return
     */
    PageInfo<TabPbTransfer> getSpRecordPageList(TransferListSpDto spDto, Page page);

    /**
     * 审批流程全部通过后，修改组织关系转接接收状态未已接转
     *
     * @param transferId
     * @param receiveFlag
     * @return
     */
    int updateReceiveFlagById(Long transferId, String receiveFlag);

    /**
     * 组织关系接转 - 撤销
     *
     * @param transferId
     * @return
     */
    int transferRevoke(Long transferId);
}
