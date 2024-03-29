package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.repository.TabPbAttachmentMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 附件服务
 * 添加附件信息时，必传参数：
 * host_id 业务id
 * attachment_instance   上传后得到的文件id
 * attachment_type       业务类型：组织信息、发展党员
 * attachment_file_type  文件类型：
 * 1	视频
 * 2	图片
 * 3	文档
 */
@Service
public class TabPbAttachmentServiceImpl implements ITabPbAttachmentService {

    @Autowired
    private TabPbAttachmentMapper tabPbAttachmentMapper;

    @Override
    public List<TabPbAttachment> listByHostId(Long hostId, String attachmentType) {
        return tabPbAttachmentMapper.listByHostId(hostId, attachmentType);
    }

    @Override
    public List<TabPbAttachment> listByHostId(Long hostId, Long attachmentType) {
        return listByHostId(hostId, attachmentType.toString());
    }

    @Override
    public TabPbAttachment getById(Long attachmentId) {
        return tabPbAttachmentMapper.selectById(attachmentId);
    }

    @Override
    public TabPbAttachment add(TabPbAttachment attachment) {
        attachment.setCreateTime(new Date());
        attachment.setUpdateTime(new Date());
        tabPbAttachmentMapper.insertSelective(attachment);
        return attachment;
    }

    @Override
    public int add(TabPbAttachment attachment, Long hostId) {
        attachment.setHostId(hostId);
        attachment.setCreateTime(new Date());
        attachment.setUpdateTime(new Date());
        return tabPbAttachmentMapper.insertSelective(attachment);
    }

    @Override
    public void addList(List<TabPbAttachment> attachments, Long hostId) {
        for (TabPbAttachment attachment : attachments) {
            attachment.setHostId(hostId);
            attachment.setCreateTime(new Date());
            attachment.setUpdateTime(new Date());
            tabPbAttachmentMapper.insertSelective(attachment);
        }
    }

    @Override
    public int deleteById(Long attachmentId) {
        return tabPbAttachmentMapper.deleteById(attachmentId);
    }

    @Override
    public int deleteByHostId(Long hostId) {
        return tabPbAttachmentMapper.deleteByHostId(hostId);
    }

    @PaddingBaseField
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int intelligentOperation(List<TabPbAttachment> pendingList, Long hostId,
                                    Long attType) {
        return intelligentOperation(pendingList, hostId,
                attType.toString());
    }

    @Override
    public int intelligentOperation(List<TabPbAttachment> pendingList, Long hostId,
                                    String attType) {
        int retVal = 0;
        if (CollectionUtil.isEmpty(pendingList)) {
            tabPbAttachmentMapper.batchLogicDeleteByHostIdAndAttType(hostId, attType);
            return retVal;
        }
        //数据处理
        final List<TabPbAttachment> finalPendingList = pendingList.stream().filter(tabPbAttachment -> tabPbAttachment != null
                && StringUtils.isNotEmpty(tabPbAttachment.getAttachmentInstance())).collect(Collectors.toList());
        pendingList.forEach(tabPbAttachment -> {
            tabPbAttachment.setHostId(hostId);
            tabPbAttachment.setAttachmentFileType(getFileType(tabPbAttachment.getAttachmentInstance()));
            tabPbAttachment.setAttachmentType(Long.parseLong(attType));
        });

        //根据当前业务id获取相关的所有附件
        List<TabPbAttachment> dbList = tabPbAttachmentMapper.listByHostId(hostId, attType);

        //数据库没数据，直接新增
        if (dbList == null || dbList.size() <= 0) {
            retVal += tabPbAttachmentMapper.batchInsert(pendingList);
            return retVal;
        }

        //待操作的所有附件ID
        List<String> pendingInstanceList = pendingList.stream()
                .map(TabPbAttachment::getAttachmentInstance)
                .collect(Collectors.toList());
        //数据库的所有附件ID
        List<String> dbInstanceList = dbList.stream()
                .map(TabPbAttachment::getAttachmentInstance)
                .collect(Collectors.toList());

        //筛选
        List<TabPbAttachment> pendingRemoveList = dbList.stream()
//                .filter(tabPbAttachment -> !pendingInstanceList.contains(tabPbAttachment.getAttachmentInstance()))
                .filter(attachment -> pendingList.stream().noneMatch(dbAttachment -> Objects.equals(dbAttachment.getAttachmentInstance(),
                        attachment.getAttachmentInstance()) && Objects.equals(dbAttachment.getAttachmentId(), attachment.getAttachmentId())))
//                .map(TabPbAttachment::getAttachmentId)
                .collect(Collectors.toList());


        List<TabPbAttachment> pendingAddList = pendingList.stream()
                .filter(tabPbAttachment -> !dbInstanceList.contains(tabPbAttachment.getAttachmentInstance()) || tabPbAttachment.getAttachmentId() == null)
                .collect(Collectors.toList());

        List<TabPbAttachment> pendingUpdateList = finalPendingList.stream().filter(attachment ->
                dbList.stream().anyMatch(dbAttachment -> Objects.equals(dbAttachment.getAttachmentInstance(), attachment.getAttachmentInstance())
                        && Objects.equals(dbAttachment.getAttachmentId(), attachment.getAttachmentId())
                        && (!Objects.equals(dbAttachment.getRotate(), attachment.getRotate())
                        || !Objects.equals(dbAttachment.getOrderNum(), attachment.getOrderNum())))).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(pendingAddList)) {
            //批量新增
            retVal += tabPbAttachmentMapper.batchInsert(pendingAddList);
        }
        if (CollectionUtil.isNotEmpty(pendingRemoveList)) {
            //批量删除
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(pendingRemoveList);
            retVal += tabPbAttachmentMapper.batchLogicDelete(pendingRemoveList);
        }
        if (CollectionUtil.isNotEmpty(pendingUpdateList)) {
            retVal += tabPbAttachmentMapper.batchUpdate(pendingUpdateList);
        }
        return retVal;
    }

    @Override
    public int intelligentOperation(String[] instances, Long hostId, Long attType) {
        if (instances == null || instances.length <= 0) {
            return 0;
        }
        List<TabPbAttachment> pendingList = Arrays.stream(instances).map(instance -> {
            TabPbAttachment tabPbAttachment = new TabPbAttachment();
            tabPbAttachment.setHostId(hostId);
            tabPbAttachment.setAttachmentInstance(instance);
            //组织信息业务
            tabPbAttachment.setAttachmentType(attType);
            tabPbAttachment.setAttachmentFileType(getFileType(instance));
            return tabPbAttachment;
        }).collect(Collectors.toList());

        return intelligentOperation(pendingList, hostId, attType.toString());
    }

    private Long getFileType(String file) {
        try {
            String fileExtension = file.substring(file.lastIndexOf(".") + 1);
            if (StringUtils.isEmpty(fileExtension)) {
                //其他
                return 59074L;
            }
            if (Pattern.matches("(?i)(asf|avi|f4v|ismv|mp4|flv|rmvb|mkv)", fileExtension)) {
                //视频
                return 58991L;
            } else if (Pattern.matches("(?i)(bmp|jpg|jpeg|png|gif)", fileExtension)) {
                //图片
                return 58992L;
            } else if (Pattern.matches("(?i)(pdf|doc|docx|txt|xlsx|xls|md|zip|rar)", fileExtension)) {
                //文档
                return 58993L;
            }
        } catch (Exception e) {
            //ignore
        }
        return 59074L;
    }

}
