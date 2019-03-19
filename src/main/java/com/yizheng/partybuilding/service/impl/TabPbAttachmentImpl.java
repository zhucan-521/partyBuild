package com.yizheng.partybuilding.service.impl;

import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.repository.TabPbAttachmentMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
public class TabPbAttachmentImpl implements ITabPbAttachmentService {

    @Autowired
    private TabPbAttachmentMapper mapper;

    @Override
    public List<TabPbAttachment> listByHostId(Long hostId, String attachmentType) {
        return mapper.listByHostId(hostId, attachmentType);
    }

    @Override
    public List<TabPbAttachment> listByHostId(Long hostId, Long attachmentType) {
        return listByHostId(hostId, attachmentType.toString());
    }

    @Override
    public TabPbAttachment getById(Long attachmentId) {
        return mapper.selectById(attachmentId);
    }

    @Override
    public TabPbAttachment add(TabPbAttachment attachment) {
        attachment.setCreateTime(new Date());
        attachment.setUpdateTime(new Date());
        mapper.insertSelective(attachment);
        return attachment;
    }

    @Override
    public int add(TabPbAttachment attachment, Long hostId) {
        attachment.setHostId(hostId);
        attachment.setCreateTime(new Date());
        attachment.setUpdateTime(new Date());
        return mapper.insertSelective(attachment);
    }

    @Override
    public void addList(List<TabPbAttachment> attachments, Long hostId) {
        for (TabPbAttachment attachment : attachments) {
            attachment.setHostId(hostId);
            attachment.setCreateTime(new Date());
            attachment.setUpdateTime(new Date());
            mapper.insertSelective(attachment);
        }
    }

    @Override
    public int deleteById(Long attachmentId) {
        return mapper.deleteById(attachmentId);
    }

    @Override
    public int deleteByHostId(Long hostId) {
        return mapper.deleteByHostId(hostId);
    }

    @PaddingBaseField
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
            mapper.batchLogicDeleteByHostIdAndAttType(hostId, attType);
            return retVal;
        }
        //数据处理
        pendingList = pendingList.stream().filter(tabPbAttachment -> tabPbAttachment != null
                && StringUtils.isNotEmpty(tabPbAttachment.getAttachmentInstance())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(pendingList)) {
            mapper.batchLogicDeleteByHostIdAndAttType(hostId, attType);
            return retVal;
        }
        pendingList.forEach(tabPbAttachment -> {
            tabPbAttachment.setHostId(hostId);
            tabPbAttachment.setAttachmentFileType(getFileType(tabPbAttachment.getAttachmentInstance()));
            tabPbAttachment.setAttachmentType(Long.parseLong(attType));
        });

        //根据当前业务id获取相关的所有附件
        List<TabPbAttachment> dbList = mapper.listByHostId(hostId, attType);

        //数据库没数据，直接新增
        if (dbList == null || dbList.size() <= 0) {
            retVal += mapper.batchInsert(pendingList);
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
        List<Long> pendingRemoveIDList = dbList.stream()
                .filter(tabPbAttachment -> !pendingInstanceList.contains(tabPbAttachment.getAttachmentInstance()))
                .map(TabPbAttachment::getAttachmentId)
                .collect(Collectors.toList());

        List<TabPbAttachment> pendingAddList = pendingList.stream()
                .filter(tabPbAttachment -> !dbInstanceList.contains(tabPbAttachment.getAttachmentInstance()))
                .collect(Collectors.toList());

        if (!pendingAddList.isEmpty()) {
            //批量新增
            retVal += mapper.batchInsert(pendingAddList);
        }
        if (!pendingRemoveIDList.isEmpty()) {
            //批量删除
            retVal += mapper.batchLogicDelete(pendingRemoveIDList);
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
