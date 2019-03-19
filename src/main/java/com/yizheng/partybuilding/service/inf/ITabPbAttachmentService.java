package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.TabPbAttachment;

import java.util.List;

public interface ITabPbAttachmentService {

    /**
     * 根据hostId获取附件信息
     * @param hostId
     * @param attachmentType 业务类型
     * @return
     */
    List<TabPbAttachment> listByHostId(Long hostId, String attachmentType);

    List<TabPbAttachment> listByHostId(Long hostId, Long attachmentType);

    /**
     * 根据id获取附件信息
     *
     * @param attachmentId
     * @return
     */
    TabPbAttachment getById(Long attachmentId);

    /**
     * 新增附件信息
     *
     * @param attachment
     * @return
     */
    TabPbAttachment add(TabPbAttachment attachment);

    /**
     * 新增附件信息
     *
     * @param attachment
     * @return
     */
    int add(TabPbAttachment attachment, Long hostId);

    /**
     * 新增多个附件
     *
     * @param attachment
     */
    void addList(List<TabPbAttachment> attachment, Long hostId);

    /**
     * 根据id删除附件信息，改删除标识
     *
     * @param attachmentId
     * @return
     */
    int deleteById(Long attachmentId);

    /**
     * 根据hostId删除附件信息，改删除标识
     *
     * @param hostId
     * @return
     */
    int deleteByHostId(Long hostId);

    /**
     * 智能操作
     * 对外屏蔽  “根据前端传的附件和数据库附件对比来判断进行新增还是修改还是删除的通用非核心业务逻辑"
     * @param pendingList 待操作的附件对象集合
     * @param hostId  附件表关联的业务ID
     * @param attType 操作的业务类型
     * @return
     */
    int intelligentOperation(List<TabPbAttachment> pendingList, Long hostId, String attType);

    int intelligentOperation(List<TabPbAttachment> pendingList, Long hostId, Long attType);

    /**
     * 数组形式
     * @param instances
     * @param hostId
     * @param attType
     * @return
     */
    @Deprecated
    int intelligentOperation(String[] instances, Long hostId, Long attType);

}
