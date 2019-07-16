package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.dto.MessageAddDTO;
import com.egovchina.partybuilding.common.dto.MessageReceiveDTO;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.enums.MessageTypeEnum;
import com.egovchina.partybuilding.common.enums.ReceiverTypeEnum;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.dto.FlowInMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.feign.SystemServiceFeignClient;
import com.egovchina.partybuilding.partybuild.feign.fallback.SystemServiceFallback;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowInMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowOutMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.FlowInService;
import com.egovchina.partybuilding.partybuild.service.StationNewsService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.util.UserTagType;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * 流入党员实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowInServiceImpl implements FlowInService {

    @Autowired
    private TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private StationNewsService stationNewsService;

    @Autowired
    private SystemServiceFeignClient systemServiceFeignClient;

    /**
     * 审核未通过
     */
    private final Long REFUSETOACCEPT = 59713L;

    /**
     * 流入党员列表查询
     *
     * @param
     * @return
     */
    @Override
    public PageInfo<FlowInMemberVO> getFlowInMemberList(FlowInMemberQueryBean flowInMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        List<FlowInMemberVO> tabPbFlowInDtos = tabPbFlowInMapper.selectListByFlowInVo(flowInMemberQueryBean);
        PageInfo<FlowInMemberVO> pageInfo = new PageInfo(tabPbFlowInDtos);
        return pageInfo;
    }

    /**
     * 根据流入id删除流入党员信息
     *
     * @param flowInId
     * @return
     */
    @Override
    public int deleteFlowInMember(Long flowInId) {
        TabPbFlowIn tabPbFlowIn = tabPbFlowInMapper.selectByPrimaryKey(flowInId);
        int flag = 0;
        if (tabPbFlowIn.getFlowInState().equals(CommonConstant.PENDING_REPORT)) {
            tabPbFlowIn.setDelFlag(CommonConstant.STATUS_DEL);
            tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
            SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(tabPbFlowIn.getUserId());
            //用户结束流动
            sysUser.setFlowStatus(CommonConstant.NORMAL);
            flag = tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
            if (flag > 0) {
                //取消流动标识
                userTagService.delete(sysUser.getUserId(), UserTagType.FLOW);
                //取消用户表流入流出党组织
                tabPbFlowInMapper.cancelSysUserFlowStaus(sysUser.getUserId());
                TabPbFlowOut tabPbFlowOut = new TabPbFlowOut();
                tabPbFlowOut.setDelFlag(CommonConstant.STATUS_DEL);
                tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
                tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbFlowOut);
            }
            return flag;
        }
        throw new BusinessDataCheckFailException("只有待报到的流动党员才可以删除");
    }

    /**
     * 录入
     *
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int acceptFlowInMember(FlowInMemberDTO flowInMemberDto) {
        //流入表
        TabPbFlowIn tabPbFlowIn = tabPbFlowInMapper.selectByPrimaryKey(flowInMemberDto.getFlowInId());
        if (tabPbFlowIn.getFlowInState().equals(CommonConstant.FLOWED_IN)) {
            throw new BusinessDataCheckFailException("该党员已经录入");
        }
        BeanUtils.copyProperties(flowInMemberDto, tabPbFlowIn);
        //设置状态已流入
        tabPbFlowIn.setFlowInState(CommonConstant.FLOWED_IN);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        TabPbFlowOut tabPbFlowOut = new TabPbFlowOut();
        tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
        //设置状态已流出
        tabPbFlowOut.setFlowOutState(CommonConstant.FLOWED_OUT);
        //修改流入流出党组织联系人和电话  user表
        Long userId = tabSysUserMapper.SelectUserIdByIDcard(flowInMemberDto.getIdCardNo());
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        //设置状态流动
        sysUser.setFlowStatus(CommonConstant.FLOW);
        BeanUtils.copyProperties(flowInMemberDto, sysUser);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //添加流动标识
        userTagService.addUserTag(sysUser.getUserId(), UserTagType.FLOW);
        this.sendMessage(userId, true);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 修改
     *
     * @param
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateFlowInDto(FlowInMemberDTO flowInMemberDto) {
        //不让修改流入党组织和流出党组织
        flowInMemberDto.setOrgId(null);
        flowInMemberDto.setFlowToOrgName(null);
        //修改流出表
        TabPbFlowOut tabPbFlowOutDto = generateTargetCopyPropertiesAndPaddingBaseField(flowInMemberDto, TabPbFlowOut.class, true);
        tabPbFlowOutDto.setFlowOutId(flowInMemberDto.getFlowOutId());
        tabPbFlowOutDto.setFlowOutPlace(flowInMemberDto.getOldPlace());
        tabPbFlowOutDto.setFlowOutReason(flowInMemberDto.getFlowInReason());
        tabPbFlowOutDto.setFlowToOrgnizeCode(flowInMemberDto.getOldOrgnizeCode());
        tabPbFlowOutDto.setOutIndustry(flowInMemberDto.getFlowInRange());
        tabPbFlowOutDto.setFlowOutType(flowInMemberDto.getFlowInType());
        tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutDto);
        //修改流入表
        TabPbFlowIn tabPbFlowInUpdate = generateTargetCopyPropertiesAndPaddingBaseField(flowInMemberDto, TabPbFlowIn.class, true);
        return tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowInUpdate);
    }

    /**
     * 結束流動
     *
     * @param
     * @return
     */
    @Override
    public int returnFlowInMember(FlowInMemberDTO flowInMemberDto) {
        //用户表 流动状态设置结束流动 清空流入组织联系人和电话、流出组织联系人和电话
        tabPbFlowInMapper.cancelSysUserFlowStaus(flowInMemberDto.getUserId());
        //取消流动标识
        userTagService.delete(flowInMemberDto.getUserId(), UserTagType.FLOW);
        //流入表 设置状态为返回 填充前端传过来的字段
        flowInMemberDto.setFlowInState(CommonConstant.RETURNED);
        TabPbFlowIn tabPbFlowIn = generateTargetCopyPropertiesAndPaddingBaseField(flowInMemberDto, TabPbFlowIn.class, true);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        //流出表 设置状态为返回，填充返回日期
        TabPbFlowOut tabPbFlowOut = tabPbFlowOutMapper.selectByPrimaryKey(flowInMemberDto.getFlowOutId());
        tabPbFlowOut.setFlowOutState(CommonConstant.RETURNED);
        tabPbFlowOut.setReturnDate(flowInMemberDto.getReturnDate());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFlowOut);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 根据流入id获取流入党员DTO信息
     *
     * @param flowInId
     * @return
     */
    @Override
    public FlowInMemberVO getFlowInMeberVoById(Long flowInId) {
        return tabPbFlowInMapper.selectFlowInVoByFlowId(flowInId);
    }

    /**
     * 发送信息 true则已经接受，false则拒绝接受
     *
     * @param userId
     * @param flag
     */
    public void sendMessage(Long userId, Boolean flag) {
        SysUser recipientUser = tabSysUserMapper.selectOneByUserId(userId);
        MessageReceiveDTO messageReceiveDTO = new MessageReceiveDTO().setReceiverId(userId).setReceiverName(recipientUser.getRealname());
        List<MessageReceiveDTO> messageReceiveDTOs = new ArrayList();
        messageReceiveDTOs.add(messageReceiveDTO);
        MessageAddDTO messageAddDTO = new MessageAddDTO().setReceiverType(ReceiverTypeEnum.PERSON.getReceiverType())
                .setType(MessageTypeEnum.SYSTEM_MESSAGE.getId()).setReceivers(messageReceiveDTOs).setTitle("流入党组织通知");
        String content = recipientUser.getRealname() + "您好，您申请流入" + recipientUser.getFlowToOrgName() + "流动挂靠失败！请重新申请";
        if (flag) {
            ReturnEntity returnEntity = systemServiceFeignClient.getMessageContent(SystemServiceFallback.SUCCESSFLOW);
            if (returnEntity.unOkResp()) {
                throw returnEntity.exception();
            }
            if (returnEntity != null) {
                if (returnEntity.getResultObj() != null) {
                    content = Optional.ofNullable(returnEntity.getResultObj()).orElse("")
                            .toString().replace("{{realname}}", recipientUser.getRealname()).replace("{{orgName}}", recipientUser.getFlowToOrgName());
                }
            }
            messageAddDTO.setContent(content);
            stationNewsService.batchInsertStationNews(messageAddDTO);
            return;
        }
        ReturnEntity returnEntity = systemServiceFeignClient.getMessageContent(SystemServiceFallback.FALSEFLOW);
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        if (returnEntity != null) {
            if (returnEntity.getResultObj() != null) {
                content = Optional.ofNullable(returnEntity.getResultObj()).orElse("")
                        .toString().replace("{{realname}}", recipientUser.getRealname()).replace("{{orgName}}", recipientUser.getFlowToOrgName());
            }
        }
        messageAddDTO.setContent(content);
        stationNewsService.batchInsertStationNews(messageAddDTO);
    }

    /**
     * 拒绝接受
     *
     * @param flowInId
     * @param returnTag
     * @return
     */
    @Override
    public int refuse(Long flowInId, String returnTag) {
        TabPbFlowIn tabPbFlowIn = tabPbFlowInMapper.selectByPrimaryKey(flowInId);
        //设置状态拒绝接受
        tabPbFlowIn.setFlowInState(REFUSETOACCEPT);
        tabPbFlowIn.setReturnTag(returnTag);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFlowIn);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        this.sendMessage(tabPbFlowIn.getUserId(), false);
        TabPbFlowOut tabPbFlowOut = new TabPbFlowOut();
        tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
        tabPbFlowOut.setReturnTag(returnTag);
        tabPbFlowOut.setFlowOutState(REFUSETOACCEPT);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFlowOut);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

}
