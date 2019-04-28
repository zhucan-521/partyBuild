package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;

import java.util.List;

/**
 * @author: GuanYingxin
 * Date: 2019/4/19
 */
public interface UserTagService {
    /**
     * 根据用户id以及字典id添加标签
     *
     * @param userId
     * @param tagType
     * @return
     */
    int addUserTag(Long userId, Long tagType);

    /**
     * 根据用户id以及字典id添加标签
     *
     * @param userTagDTO
     * @return
     */
    int insertUserTagDTO(UserTagDTO userTagDTO);

    /**
     * 根据字典id删除党员标签
     *
     * @param usertagId
     * @return
     */
    int delete(Long usertagId);

    /**
     * 根据用户id 和 字典id 删除 标签。
     *
     * @param userId
     * @param tagType
     * @return
     */
    int delete(Long userId, Long tagType);

    /**
     * 根据用户id获取标签列表
     *
     * @param userId
     * @return
     */
    List<TabPbUserTag> selectTagByUserId(Long userId);

    /**
     * 使用tagType 来确定标签是否存在的update
     *
     * @param userTags -- TagType  1真  0假
     * @return
     */
    boolean updateUserTagByTagType(List<TabPbUserTag> userTags);

    /**
     * 批量插入标记
     * @param userTagDTO
     * @return
     */
    int batchInsertUserTagDTO(UserTagDTO userTagDTO);
}
