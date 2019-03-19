package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.entity.TabPbUserTag;

import java.util.List;

/**
 * @author: huang
 * Date: 2019/1/14
 */
public interface ITabPbUserTagService {
    int addUserTag(Long userId, Long tagType);

    int delete(Long usertagId);

    /**
     * 根据用户id 和 字典id 删除 标签。
     * @param userId
     * @param tagType
     * @return
     */
    int delete(Long userId, Long tagType);

    List<TabPbUserTag> selectTagByUserId(Long userId);

    /**
     * 使用tagType 来确定标签是否存在的update
     * @param userTags -- TagType  1真  0假
     * @return
     */
    boolean updateUserTagByTagType(List<TabPbUserTag> userTags);
}
