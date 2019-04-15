package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TransferDetailDto;
import com.egovchina.partybuilding.partybuild.dto.TransferListSpDto;
import com.egovchina.partybuilding.partybuild.dto.TransferListWaitingDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransfer;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransferItem;
import com.egovchina.partybuilding.partybuild.repository.TabPbTransferMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbTransferService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织关系接转服务
 */
@Service
@Transactional
public class TabPbTransferServiceImpl implements ITabPbTransferService {

    @Autowired
    private TabPbTransferMapper mapper;

    @Autowired
    private TabPbTransferItemServiceImpl itemService;

    @Override
    public int add(TabPbTransfer transfer) {
        transfer.setCreateUserId(Long.valueOf(UserContextHolder.getUserId()));
        transfer.setCreateUsername(UserContextHolder.getUserName());
        transfer.setCreateTime(new Date());
        transfer.setReceiveFlag("0");//是否接收 0否 ，1是
        transfer.setRevokeFlag("0"); //是否撤销 0否 ，1是
        int i = mapper.insertSelective(transfer);
        Long transferId = transfer.getTransferId(); // 主键

        //根据所在组织与目标组织，生成审批流程
        itemService.add(transfer);
        return i;
    }

    @Override
    public int edit(TabPbTransfer transfer) {
        return mapper.updateById(transfer);
    }

    @Override
    public TabPbTransfer getById(Long transferId) {
        return mapper.getById(transferId);
    }

    @Override
    public PageInfo<TabPbTransfer> getPageList(Page page) {
        PageHelper.startPage(page);
        List<TabPbTransfer> list = mapper.getPageList();
        return new PageInfo<>(list);
    }

    //获取组织接转详情信息，包含审批信息
    @Override
    public TransferDetailDto getTransferDetailById(Long transferId) {
        //分别获取主表与审批表的信息
        TransferDetailDto detailDto = new TransferDetailDto();
        TabPbTransfer transfer = getById(transferId);
        detailDto.setTransfer(transfer);

        //获取审批信息
        List<TabPbTransferItem> itemList = itemService.listByTransferId(transferId);
        detailDto.setItemList(itemList);
        return detailDto;
    }

    /**
     * 组织关系接转 - 接收、转出 - 列表
     *
     * @param flowType 0接收列表、1转出列表
     * @param transfer
     * @param page
     * @return
     */
    @Override
    public PageInfo<TabPbTransfer> getPageList(int flowType, TabPbTransfer transfer, Page page) {
        PageHelper.startPage(page);
        List<TabPbTransfer> list = new ArrayList<>();
        if (flowType > 0) {
            list = mapper.getFlowOutPageList(transfer);//1转出列表
        } else {
            list = mapper.getFlowInPageList(transfer); //0接收列表
        }
        return new PageInfo<>(list);
    }

    /**
     * 组织关系接转 - 待办工作 - 列表
     *
     * @param deptId
     * @param username
     * @param idCardNo
     * @param page
     * @return
     */
    @Override
    public PageInfo<TransferListWaitingDto> getWaitingPageList(Long deptId, String username, String idCardNo, Page page) {
        PageHelper.startPage(page);
        List<TransferListWaitingDto> list = mapper.getWaitingPageList(deptId, username, idCardNo);
        return new PageInfo<>(list);
    }

    /**
     * 组织关系接转 - 未办结工作 - 列表
     *
     * @param deptId
     * @param username
     * @param idCardNo
     * @param page
     * @return
     */
    @Override
    public PageInfo<TransferListWaitingDto> getNotFinshedPageList(Long deptId, String username, String idCardNo, Page page) {
        PageHelper.startPage(page);
        List<TransferListWaitingDto> list = mapper.getNotFinshedPageList(deptId, username, idCardNo);
        return new PageInfo<>(list);
    }

    /**
     * 组织关系接转 - 即将办理工作 - 列表
     *
     * @param handle
     * @param page
     * @return
     */
    @Override
    public PageInfo<TransferListSpDto> getHandlePageList(TransferListSpDto handle, Page page) {
        PageHelper.startPage(page);
        List<TransferListSpDto> list = mapper.getHandlePageList(handle);
        return new PageInfo<>(list);
    }

    /**
     * 组织关系接转 - 审批记录查询 - 列表
     *
     * @param spDto
     * @param page
     * @return
     */
    @Override
    public PageInfo<TabPbTransfer> getSpRecordPageList(TransferListSpDto spDto, Page page) {
        PageHelper.startPage(page);
        List<TabPbTransfer> list = mapper.getSpRecordPageList(spDto);
        return new PageInfo<>(list);
    }

    /**
     * 审批流程全部通过后，修改组织关系转接接收状态未已接转
     *
     * @param transferId
     * @param receiveFlag
     * @return
     */
    @Override
    public int updateReceiveFlagById(Long transferId, String receiveFlag) {
        return mapper.updateReceiveFlagById(transferId, receiveFlag);
    }

    /**
     * 组织关系接转 - 撤销
     * 上级党组织未进行审批时，系统支持转出方党组织撤销转出申请
     * @param transferId
     * @return
     */
    @Override
    public int transferRevoke(Long transferId) {
        boolean isApproval = itemService.isApproval(transferId);
        if (isApproval) {
            throw new BusinessDataCheckFailException("上级已审批，不能撤销");
        }
        return mapper.updateRevokeFlagById(transferId, "1");
    }
}
