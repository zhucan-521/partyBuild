package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;

import java.util.List;

/**
 * @author: GuanYingxin
 * Date: 2019/4/19
 */
public interface UserTagService {

    /**
     * 根据用户id以及标记类型添加标签
     *
     * @param userId  用户id
     * @param tagType 标记类型
     * @return
     */
    int addUserTag(Long userId, Long tagType);

    /**
     * 根据用户id 和 标记类型 删除 标签。
     *
     * @param userId  用户id
     * @param tagType 标记类型
     * @return
     */
    int delete(Long userId, Long tagType);

    /**
     * 批量插入标记
     *
     * @param userTagDTO 用户标签dto
     * @return
     */
    int batchInsertUserTagDTO(UserTagDTO userTagDTO);

}
