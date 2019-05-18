package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbAttachmentMapper {

    int insert(TabPbAttachment record);

    int insertSelective(TabPbAttachment record);

    TabPbAttachment selectById(Long attachmentId);

    List<TabPbAttachment> listByHostId(@Param("hostId") Long hostId, @Param("attachmentType") String attachmentType);

    int updateByPrimaryKeySelective(TabPbAttachment record);

    int updateByPrimaryKeyWithBLOBs(TabPbAttachment record);

    int updateById(TabPbAttachment record);

    int deleteById(Long attachmentId);

    int deleteByHostId(Long hostId);

    int batchInsert(List<TabPbAttachment> pendingList);

    int batchLogicDelete(List<TabPbAttachment> tabPbAttachments);

    int batchUpdate(List<TabPbAttachment> pendingUpdateList);

    /**
     * 根据一些条件进行模糊查询等
     *
     * @param attr
     * @return
     */
    List<TabPbAttachment> selectBySomeCondition(TabPbAttachment attr);

    int batchLogicDeleteByHostIdAndAttType(@Param("hostId") Long hostId, @Param("attType") String attType);
}