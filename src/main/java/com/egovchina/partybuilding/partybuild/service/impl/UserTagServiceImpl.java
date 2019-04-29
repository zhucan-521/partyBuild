package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbUserTagMapper;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.service.HardshipService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
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

    @PaddingBaseField
    private int addUserTag(TabPbUserTag userTag) {
        if (!ObjectUtils.isEmpty(userTag.getUsertagId())) {
            return 0;
        }
        return tabPbUserTagMapper.insertUserTagDTOSelective(userTag);
    }

    @PaddingBaseField(recursive = true)
    private int updateUserTag(TabPbUserTag userTag) {
        if (ObjectUtils.isEmpty(userTag.getUsertagId())) {
            return 0;
        }
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
    @Override
    public int batchInsertUserTagDTO(UserTagDTO userTagDTO) {
        int value = 0;

        List<Long> tagTypes = userTagDTO.getTagTypes();
        if (CollectionUtil.isEmpty(tagTypes)) {
            return tabPbUserTagMapper.batchDeleteByUserId(userTagDTO.getUserId());
        }

        List<TabPbUserTag> list = tagTypes.stream().map(tagType -> {

            if (tagType == 59428L) {
                HardshipPartyDTO hardshipPartyDTO = new HardshipPartyDTO();
                hardshipPartyDTO.setUserId(userTagDTO.getUserId());
                hardshipPartyDTO.setOrgId(userTagDTO.getOrgId());
                hardshipService.insertHardshipParty(hardshipPartyDTO);
            }

            TabPbUserTag tabPbUserTag = new TabPbUserTag();
            tabPbUserTag.setUserId(userTagDTO.getUserId());
            tabPbUserTag.setTagType(tagType);
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbUserTag);
            return tabPbUserTag;
        }).collect(Collectors.toList());

        //获取原有用户的tagType集合
        List<TabPbUserTag> dbTagList = tabPbUserTagMapper.selectTagTypesList(userTagDTO.getUserId());

        if (CollectionUtil.isEmpty(dbTagList)) {
            return tabPbUserTagMapper.batchInsertUserTagDTO(list);
        }

        List<Long> deleteIds = new ArrayList<>();
        List<TabPbUserTag> insertList = new ArrayList<>();
        List<Long> dbUserTagList = new ArrayList<>();
        for (TabPbUserTag tabPbUserTag : dbTagList) {
            //前端没有数据库有的，删除
            if (!tagTypes.contains(tabPbUserTag.getTagType())) {
                deleteIds.add(tabPbUserTag.getUsertagId());
                if (tabPbUserTag.getTagType() == 59428L) {
                    hardshipService.logicDeleteByUserId(userTagDTO.getUserId());
                }
            }
            dbUserTagList.add(tabPbUserTag.getTagType());
        }
        for (Long tagType : tagTypes) {
            if (!dbUserTagList.contains(tagType)) {
                TabPbUserTag userTag = new TabPbUserTag();
                userTag.setUserId(userTagDTO.getUserId());
                userTag.setTagType(tagType);
                PaddingBaseFieldUtil.paddingBaseFiled(userTag);
                insertList.add(userTag);
            }
        }
        if (CollectionUtil.isNotEmpty(deleteIds)) {
            value += tabPbUserTagMapper.batchDeleteById(deleteIds);
        }
        if (CollectionUtil.isNotEmpty(insertList)) {
            value += tabPbUserTagMapper.batchInsertUserTagDTO(insertList);
        }
        return value;
    }
}
