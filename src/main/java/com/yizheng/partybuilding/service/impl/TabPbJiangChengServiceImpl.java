package com.yizheng.partybuilding.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbPunishment;
import com.yizheng.partybuilding.entity.TabPbRewards;
import com.yizheng.partybuilding.repository.TabPbPunishmentMapper;
import com.yizheng.partybuilding.repository.TabPbRewardsMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.ITabPbJiangChengService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.commons.util.AttachmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: huang
 * Date: 2018/12/3
 */
@Service("jiangCheng")
public class TabPbJiangChengServiceImpl implements ITabPbJiangChengService {

    @Autowired
    TabPbRewardsMapper tabPbRewardsMapper;

    @Autowired
    TabPbPunishmentMapper tabPbPunishmentMapper;

    @Autowired
    ITabPbAttachmentService tabPbAttachmentService;

    @Override
    public int insertPunishment(TabPbPunishment punishment) {
        punishment.setDelFlag(CommonConstant.STATUS_NORMAL);
        punishment.setEblFlag(CommonConstant.STATUS_EBL);
        int insertRow = tabPbPunishmentMapper.insertSelective(punishment);
        savePunishAttach(punishment);
        return insertRow;
    }

    @Override
    public int deletePunishmentById(Long id) {
        if (ObjectUtils.isEmpty(id)){
            return 0;
        }
        TabPbPunishment pbPunishment = new TabPbPunishment();
        pbPunishment.setPunishmentId(id);
        pbPunishment.setDelFlag(CommonConstant.STATUS_DEL);
        return tabPbPunishmentMapper.updateByPrimaryKeySelective(pbPunishment);
    }

    @Override
    public int updatePunishmentById(TabPbPunishment punishment) {
        if(ObjectUtils.isEmpty(punishment.getPunishmentId())){
            return 0;
        }
        punishment.setUpdateTime(new Date());
        punishment.setCreateUserid(Integer.toUnsignedLong(UserContextHolder.getUserId()));
        punishment.setCreateUsername(UserContextHolder.getUserName());
        savePunishAttach(punishment);
        return tabPbPunishmentMapper.updateByPrimaryKeySelective(punishment);
    }

    @Override
    public TabPbPunishment selectPunishmentById(Long id) {
        if (ObjectUtils.isEmpty(id)){
            return null;
        }
        TabPbPunishment punishment = tabPbPunishmentMapper.selectByPrimaryKey(id);
        List<TabPbAttachment> tabPbAttachments = tabPbAttachmentService.listByHostId(id,AttachmentType.PUNISHMENT);
        punishment.setFileList(getStrList(tabPbAttachments,AttachmentType.DOC));
        punishment.setImgList(getStrList(tabPbAttachments,AttachmentType.PHOTO));
        return punishment;
    }

    @Override
    public List<TabPbPunishment> selectPunishmentList(Map<String, Object> params) {
        TabPbPunishment punishment = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbPunishment.class);
        punishment.setAttachmentType(AttachmentType.PUNISHMENT);
        return tabPbPunishmentMapper.selectList(punishment);
    }

    @Override
    public int insertRewards(TabPbRewards rewards) {
        rewards.setDelFlag(CommonConstant.STATUS_NORMAL);
        rewards.setEblFlag(CommonConstant.STATUS_EBL);
        int insertRow = tabPbRewardsMapper.insertSelective(rewards);
        saveRewardsAttach(rewards);
        return insertRow;
    }

    @Override
    public int deleteRewardsById(Long id) {
        if (ObjectUtils.isEmpty(id)){
            return 0;
        }
        TabPbRewards rewards = new TabPbRewards();
        rewards.setRewardsId(id);
        rewards.setDelFlag(CommonConstant.STATUS_DEL);
        return tabPbRewardsMapper.updateByPrimaryKeySelective(rewards);
    }

    @Override
    public int updateRewardsById(TabPbRewards rewards) {
        if (ObjectUtils.isEmpty(rewards.getRewardsId())){
            return 0;
        }
        rewards.setUpdateTime(new Date());
        rewards.setCreateUserid(Integer.toUnsignedLong(UserContextHolder.getUserId()));
        rewards.setCreateUsername(UserContextHolder.getUserName());
        saveRewardsAttach(rewards);
        return tabPbRewardsMapper.updateByPrimaryKeySelective(rewards);
    }

    @Override
    public TabPbRewards selectRewardsById(Long id) {
        if (ObjectUtils.isEmpty(id)){
            return null;
        }
        TabPbRewards rewards = tabPbRewardsMapper.selectByPrimaryKey(id);
        List<TabPbAttachment> tabPbAttachments = tabPbAttachmentService.listByHostId(id,AttachmentType.REWARDS);
        rewards.setFileList(getStrList(tabPbAttachments,AttachmentType.DOC));
        rewards.setImgList(getStrList(tabPbAttachments,AttachmentType.PHOTO));
        return rewards;
    }

    @Override
    public List<TabPbRewards> selectRewardsList(Map<String, Object> params) {
        TabPbRewards tabPbRewards = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbRewards.class);
        tabPbRewards.setAttachmentType(AttachmentType.REWARDS);
        return tabPbRewardsMapper.selectList(tabPbRewards);
    }

     private void intelligentOperation(List<String> pendingList, Long hostId, Long attType, Long fileType){
        if(ObjectUtils.isEmpty(pendingList)){
            return;
        }
         List<TabPbAttachment> attachments = new ArrayList<>();
         intelligentOperation(pendingList,fileType,attachments,hostId,attType);
         tabPbAttachmentService.intelligentOperation(attachments, hostId, attType);
     }

    private void intelligentOperation(List<String> pendingList, Long fileType,List<TabPbAttachment> attachments, Long hostId, Long attType){
        pendingList.forEach(file -> {
            TabPbAttachment attachment = new TabPbAttachment();
            attachment.setAttachmentInstance(file);
            attachment.setAttachmentFileType(fileType);
            attachment.setHostId(hostId);
            attachment.setAttachmentType(attType);
            attachments.add(attachment);
        });
    }

     private List<String> getStrList(List<TabPbAttachment> attachments,Long fileType){
        return attachments.stream()
                .filter(attachment -> fileType.equals(attachment.getAttachmentFileType()))
                .map(TabPbAttachment::getAttachmentInstance)
                .collect(Collectors.toList());
     }

    private void saveRewardsAttach(TabPbRewards rewards){
        List<TabPbAttachment> attachments = new ArrayList<>();
        intelligentOperation(rewards.getFileList(),AttachmentType.DOC,attachments,rewards.getRewardsId(),AttachmentType.REWARDS);
        intelligentOperation(rewards.getImgList(),AttachmentType.PHOTO,attachments,rewards.getRewardsId(),AttachmentType.REWARDS);
        tabPbAttachmentService.intelligentOperation(attachments,rewards.getRewardsId(),AttachmentType.REWARDS);
    }

    private void savePunishAttach(TabPbPunishment punishment){
        List<TabPbAttachment> attachments = new ArrayList<>();
        intelligentOperation(punishment.getFileList(),AttachmentType.DOC,attachments,punishment.getPunishmentId(),AttachmentType.PUNISHMENT);
        intelligentOperation(punishment.getImgList(),AttachmentType.PHOTO,attachments,punishment.getPunishmentId(),AttachmentType.PUNISHMENT);
        tabPbAttachmentService.intelligentOperation(attachments,punishment.getPunishmentId(),AttachmentType.PUNISHMENT);
    }
}
