package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbUserTagMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbUserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: huang
 * Date: 2019/1/14
 */
@Service
public class TabPbUserTagServiceImpl implements ITabPbUserTagService {

    @Autowired
    TabPbUserTagMapper userTagMapper;

    @Override
    public int addUserTag(Long userId, Long tagType) {
        if(ObjectUtils.isEmpty(userId)||ObjectUtils.isEmpty(tagType)){
            return 0;
        }
        if(userTagMapper.exist(userId,tagType)){
            return 0;
        }
        TabPbUserTag userTag = new TabPbUserTag();
        userTag.setTagType(tagType);
        userTag.setUserId(userId);
        return addUserTag(userTag);
    }

    @Override
    public int delete(Long usertagId) {
        return userTagMapper.deleteByPrimaryKey(usertagId);
    }

    @Override
    public int delete(Long userId, Long tagType) {
        return userTagMapper.deleteByUserIdAndTagType(userId,tagType);
    }


    private int addUserTag(TabPbUserTag userTag){
        if(!ObjectUtils.isEmpty(userTag.getUsertagId())){
            return 0;
        }
        userTag.setCreateTime(new Date());
        userTag.setCreateUserid(UserContextHolder.getUserId().longValue());
        userTag.setCreateUsername(UserContextHolder.getUserName());
        userTag.setUpdateTime(new Date());
        userTag.setUpdateUserid(UserContextHolder.getUserId().longValue());
        userTag.setUpdateUsername(UserContextHolder.getUserName());
        return userTagMapper.insertSelective(userTag);
    }

    private int updateUserTag(TabPbUserTag userTag){
        if(ObjectUtils.isEmpty(userTag.getUsertagId())){
           return 0;
        }
        userTag.setUpdateTime(new Date());
        userTag.setUpdateUserid(UserContextHolder.getUserId().longValue());
        userTag.setUpdateUsername(UserContextHolder.getUserName());
        return userTagMapper.updateByPrimaryKeySelective(userTag);
    }


    @Override
    public List<TabPbUserTag> selectTagByUserId(Long userId){
        List<TabPbUserTag> userTags = new ArrayList<>();
        if(!ObjectUtils.isEmpty(userId)){
            userTags = userTagMapper.selectListSelective(new TabPbUserTag().setUserId(userId));
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
            if(ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(id)){
                return;
            }
            if(!ObjectUtils.isEmpty(userTagId) && tagType != null && tagType == 0L){
                    userTagMapper.deleteByPrimaryKey(userTagId);
            }
            if(ObjectUtils.isEmpty(userTagId) && tagType != null && tagType == 1L){
                this.addUserTag(userId,id.longValue());
            }
        });
        return true;
    }

}
