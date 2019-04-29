package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbUserTagMapper;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: GuanYingxin
 * Date: 2019/4/19
 */
@Service
public class UserTagServiceImpl implements UserTagService {

    @Autowired
    private TabPbUserTagMapper tabPbUserTagMapper;

    /**
     * 根据用户id和标签id添加用户标记
     *
     * @param userId
     * @param tagType
     * @return
     */
    @Transactional
    @Override
    public int addUserTag(Long userId, Long tagType) {
        boolean judge = ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(tagType) || tabPbUserTagMapper.exist(userId, tagType);
        if (judge) {
            return 0;
        }
        TabPbUserTag userTag = new TabPbUserTag();
        PaddingBaseFieldUtil.paddingBaseFiled(userTag);
        userTag.setTagType(tagType);
        userTag.setUserId(userId);
        return addUserTag(userTag);
    }

    /**
     * 根据用户id和标签id添加用户标记
     *
     * @param userTagDTO
     * @return
     */
    @Transactional
    @Override
    public int insertUserTagDTO(UserTagDTO userTagDTO) {
        boolean judge = ObjectUtils.isEmpty(userTagDTO.getUserId()) || ObjectUtils.isEmpty(userTagDTO.getTagType()) || tabPbUserTagMapper.exist(userTagDTO.getUserId(), userTagDTO.getTagType());
        if (judge) {
            return 0;
        }
        TabPbUserTag userTag = new TabPbUserTag();
        userTag.setTagType(userTagDTO.getUserId());
        userTag.setUserId(userTagDTO.getTagType());
        return addUserTag(userTag);
    }

    /**
     * 根据标签id删除用户标记
     *
     * @param usertagId
     * @return
     */
    @Transactional
    @Override
    public int delete(Long usertagId) {
        return tabPbUserTagMapper.deleteByPrimaryKey(usertagId);
    }

    @Override
    public int delete(Long userId, Long tagType) {
        return tabPbUserTagMapper.deleteByUserIdAndTagType(userId, tagType);
    }

    private int addUserTag(TabPbUserTag userTag) {
        if (!ObjectUtils.isEmpty(userTag.getUsertagId())) {
            return 0;
        }
        userTag.setCreateTime(new Date());
        userTag.setCreateUserid(UserContextHolder.getUserId().longValue());
        userTag.setCreateUsername(UserContextHolder.getUserName());
        userTag.setUpdateTime(new Date());
        userTag.setUpdateUserid(UserContextHolder.getUserId().longValue());
        userTag.setUpdateUsername(UserContextHolder.getUserName());
        return tabPbUserTagMapper.insertUserTagDTOSelective(userTag);
    }

    private int updateUserTag(TabPbUserTag userTag) {
        if (ObjectUtils.isEmpty(userTag.getUsertagId())) {
            return 0;
        }
        userTag.setUpdateTime(new Date());
        userTag.setUpdateUserid(UserContextHolder.getUserId().longValue());
        userTag.setUpdateUsername(UserContextHolder.getUserName());
        return tabPbUserTagMapper.updateByPrimaryKeySelective(userTag);
    }

    @Override
    public List<TabPbUserTag> selectTagByUserId(Long userId) {
        List<TabPbUserTag> userTags = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userId)) {
            userTags = tabPbUserTagMapper.selectListSelective(new TabPbUserTag().setUserId(userId));
        }
        return userTags;
    }


    @Override
    public boolean updateUserTagByTagType(List<TabPbUserTag> userTags) {
        userTags.forEach(userTag -> {
            Long userTagId = userTag.getUsertagId();
            Long tagType = userTag.getTagType();
            Long userId = userTag.getUserId();
            Integer id = userTag.getId();
            if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(id)) {
                return;
            }
            if (!ObjectUtils.isEmpty(userTagId) && tagType != null && tagType == 0L) {
                tabPbUserTagMapper.deleteByPrimaryKey(userTagId);
            }
            if (ObjectUtils.isEmpty(userTagId) && tagType != null && tagType == 1L) {
                this.addUserTag(userId, id.longValue());
            }
        });
        return true;
    }
}
