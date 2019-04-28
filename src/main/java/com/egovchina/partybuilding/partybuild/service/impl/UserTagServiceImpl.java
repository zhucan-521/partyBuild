package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyDTO;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbUserTagMapper;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.service.HardshipService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.system.service.SysUserService;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: GuanYingxin
 * Date: 2019/4/19
 */
@Service
public class UserTagServiceImpl implements UserTagService {

    @Autowired
    private TabPbUserTagMapper tabPbUserTagMapper;

    @Autowired
    private HardshipService hardshipService;

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
    @PaddingBaseField
    @Override
    public int insertUserTagDTO(UserTagDTO userTagDTO) {
        boolean judge = ObjectUtils.isEmpty(userTagDTO.getUserId()) || ObjectUtils.isEmpty(userTagDTO.getTagTypes()) || tabPbUserTagMapper.exist(userTagDTO.getUserId(), userTagDTO.getTagTypes().get(0));
        if (judge) {
            return 0;
        }
        TabPbUserTag userTag = new TabPbUserTag();
        userTag.setTagType(userTagDTO.getTagTypes().get(0));
        userTag.setUserId(userTagDTO.getUserId());
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
        TabPbUserTag tabPbUserTag = tabPbUserTagMapper.selectByPrimaryKey(usertagId);
        if (tabPbUserTag.getTagType() == 59428L) {
            HardshipPartyVO hardshipPartyVO = hardshipService.findHardshipPartyVOByUserId(tabPbUserTag.getUserId());
            hardshipService.deleteByHardshipId(hardshipPartyVO.getHardshipId());
        }


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

    /**
     * 批量插入标记
     *
     * @param userTagDTO
     * @return
     */
    @Transactional
    @PaddingBaseField(recursive = true)
    @Override
    public int batchInsertUserTagDTO(UserTagDTO userTagDTO) {
        if (ObjectUtils.isEmpty(userTagDTO)) {
            return 0;
        }
        List<TabPbUserTag> list = userTagDTO.getTagTypes().stream().map(tag -> {
            TabPbUserTag tabPbUserTag = new TabPbUserTag();
            tabPbUserTag.setUserId(userTagDTO.getUserId());
            tabPbUserTag.setTagType(tag);
            return tabPbUserTag;
        }).collect(Collectors.toList());

        for (Long tagType : userTagDTO.getTagTypes()) {
            if (tagType == 59428L) {
                HardshipPartyDTO hardshipPartyDTO = new HardshipPartyDTO();
                hardshipPartyDTO.setUserId(userTagDTO.getUserId());
                hardshipPartyDTO.setOrgId(userTagDTO.getOrgId());
                hardshipService.insertHardshipParty(hardshipPartyDTO);
            }
        }
        return tabPbUserTagMapper.batchInsertUserTagDTO(list);
    }
}
