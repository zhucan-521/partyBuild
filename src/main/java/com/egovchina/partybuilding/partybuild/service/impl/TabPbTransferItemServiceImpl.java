package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TransferApprovalDto;
import com.egovchina.partybuilding.partybuild.dto.TransferItemDto;
import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransfer;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransferItem;
import com.egovchina.partybuilding.partybuild.repository.TabPbTransferItemMapper;
import com.egovchina.partybuilding.partybuild.service.OrganizationService;
import com.egovchina.partybuilding.partybuild.service.SysUserService;
import com.egovchina.partybuilding.partybuild.service.TabPbTransferItemService;
import com.egovchina.partybuilding.partybuild.service.TabPbTransferService;
import com.egovchina.partybuilding.partybuild.util.DeptStrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Deprecated
@Service
@Transactional
public class TabPbTransferItemServiceImpl implements TabPbTransferItemService {

    @Autowired
    private TabPbTransferItemMapper mapper;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TabPbTransferService transferService;

    @Override
    public TabPbTransferItem getById(Long itemId) {
        return mapper.selectById(itemId);
    }

    @Override
    public TabPbTransferItem getById(Long itemId, Long transferId) {
        return mapper.selectByIdAndTransferId(itemId, transferId);
    }

    /**
     * 后面还需要做接口，对接到全国党统系统
     * 根据所在组织与目标组织的层级，找到每一层组织与组织的党务专干帐号，生成审批流程
     * 59002 区县内转接
     * 59003 市内跨区县转接
     * 59004 省内跨市转接
     * 59018 全国范围内转接
     * @param transfer
     * @return
     */
    @Override
    public int add(TabPbTransfer transfer) {
        int i = -1;
        SysDept outDept = organizationService.selectByPrimaryKey(transfer.getFlowOutDeptId());//转出支部Id
        SysDept inDept = organizationService.selectByPrimaryKey(transfer.getFlowInDeptId());  //接受支部Id
        if (outDept != null && inDept != null) {

            String startStr = DeptStrUtil.clearFormat(outDept.getFullPath());
            startStr = DeptStrUtil.getReverseStr(startStr);
            String endStr = DeptStrUtil.clearFormat(inDept.getFullPath());
            String path = ""; //审批组织路径
            if (transfer.getFlowOutType() == 59002) {
                //区县内转接基层党委内部转接 - 上级审批即可
                path = DeptStrUtil.getJiCengPath(startStr, endStr);
            } else {
                //除区县内转接外其他 都需要市级审批
                path = startStr + endStr;
            }
            //得到所有需要审批的组织Id集合
            List<String> manageDeptIds = DeptStrUtil.getPathList(path);
            //查询出所有需要审批的组织与审批人信息
            List<TransferUserDeptInfo> userDeptInfos = sysUserService.getDWRoleUserInfoByDeptIds(manageDeptIds);
            if (!(userDeptInfos != null && userDeptInfos.size() == manageDeptIds.size())) {
                throw new BusinessException("生成转接路径错误");
            }

            int sortIndex = 0;
            for (String deptId : manageDeptIds) {
                sortIndex++;
                //查询组织信息与组织下的审批员(党组织联系人)
                TransferUserDeptInfo userDeptInfo = getUserDeptInfo(Long.valueOf(deptId), userDeptInfos);//sysUserService.getDWRoleUserInfoByDeptId(Long.valueOf(deptId));
                if (userDeptInfo == null) {
                    throw new BusinessDataNotFoundException("未找到组织与审批人信息");
                }
                TabPbTransferItem item = new TabPbTransferItem();
                item.setTransferId(transfer.getTransferId());
                item.setDeptId(Long.valueOf(deptId));
                item.setDeptName(userDeptInfo.getDeptName());
                item.setUserId(userDeptInfo.getUserId());
                item.setPbPhone(userDeptInfo.getPhone());
                item.setPbContacts(userDeptInfo.getUsername());
                item.setShIndex(sortIndex);     //审核顺序，退回后，审核顺序将重新调整
                item.setCreateTime(new Date());
                item.setCreateUserid(Long.valueOf(UserContextHolder.getUserId()));
                item.setRevokeFlag("0"); //是否撤销 0否 ，1是
                //是否能审批 ,0不能审批 ，1能审批，2已审批
                if (sortIndex <= 1) {
                    //默认第一步能审批，其他后续步骤不能，当第一步审批后，后面第二部才能
                    item.setIsSpFlag("1");
                    item.setUpdateTime(new Date());//上级审批时间
                } else {
                    item.setIsSpFlag("0");
                }
                i = mapper.insertSelective(item);
            }
        }
        return i;
    }

    private TransferUserDeptInfo getUserDeptInfo(Long manageDeptId, List<TransferUserDeptInfo> userDeptInfos) {
        TransferUserDeptInfo userDeptInfo = null;
        for (TransferUserDeptInfo info : userDeptInfos) {
            if (info.getManageDeptId().intValue() == manageDeptId) {
                userDeptInfo = info;
            }
        }
        return userDeptInfo;
    }

    @Override
    public int approvalById(TransferItemDto itemDto) {
        TabPbTransferItem item = getById(itemDto.getItemId(), itemDto.getTransferId());
        if (item == null) {
            throw new BusinessDataNotFoundException("数据不存在");
        }
        item.setStatus(itemDto.getStatus());
        item.setOptResult(itemDto.getOptResult());
        item.setOptTime(new Date());
        return mapper.updateBySelective(item);
    }

    @Override
    public List<TabPbTransferItem> listByTransferId(Long transferId) {
        return mapper.listByTransferId(transferId);
    }

    /**
     * 获取组织关系-上级是否审批
     *
     * @param transferId
     * @return
     */
    @Override
    public boolean isApproval(Long transferId) {
        int count = mapper.approvalCount(transferId, "2");
        return count > 0 ? true : false;
    }

    /**
     * 组织关系接转-审批操作
     * 1.判断是否审批不同意
     * 2.同意则修改审批状态
     * 3.判断是否为最后一步，是则把党员的组织id换到目标组织去
     * @param approvalDto
     */
    @Override
    public void transferApproval(TransferApprovalDto approvalDto) {
        TabPbTransferItem transferItem = getById(approvalDto.getItemId(), approvalDto.getTransferId());
        //TODO 1.如果审批不同意，需要退回到上级再次审批，需要重新生成审批流程
        if (approvalDto.getStatus() == 58963) {
            //重新生成审批流程

            return;
        } else if (approvalDto.getStatus() == 58962) {
            //2.同意则修改审批状态
            if (transferItem == null) {
                throw new BusinessDataNotFoundException("数据不存在");
            }
            //判断当前用户是否为审批人
            //if (transferItem.getUserId().intValue() != UserContextHolder.getUserId()) {
            //    throw new BusinessException("您没有权限审批该信息");
            //}
            transferItem.setOptTime(new Date());
            transferItem.setStatus(approvalDto.getStatus());
            transferItem.setOptResult(approvalDto.getOptResult());
            transferItem.setIsSpFlag("2");//0不能审批 ，1能审批，2已审批
            mapper.updateBySelective(transferItem);

            //没有下过审批信息则是最后一步
            TabPbTransferItem nextItem = mapper.getNextItemByTransferIdAndIndex(approvalDto.getTransferId(), (transferItem.getShIndex() + 1));
            if (nextItem != null) {
                //获取流程中下个需要审批的项，修改审批状态未1 ,修改update_time为当前上级审批时间，用来计算当前审批耗时
                nextItem.setIsSpFlag("1");
                mapper.updateIsSpFlag(nextItem.getItemId(), nextItem.getIsSpFlag());
            } else {
                //3.判断是否为最后一步，是则把党员的组织id换到目标组织去
                TabPbTransfer transfer = transferService.getById(approvalDto.getTransferId());
                if (transfer == null) {
                    throw new BusinessException("操作失败，请联系管理员");
                }
                sysUserService.updateDeptIdByUserId(transfer.getUserId().intValue(), transfer.getFlowInDeptId().intValue());
                transferService.updateReceiveFlagById(approvalDto.getTransferId(), "1");
            }
        }
    }
}
