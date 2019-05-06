package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbUserTagMapper;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.service.HardshipPartyMemberService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
    private HardshipPartyMemberService hardshipService;

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
        if (tabPbUserTagMapper.exist(userId, tagType)) {
            return 1;
        }
        TabPbUserTag userTag = new TabPbUserTag();
        PaddingBaseFieldUtil.paddingBaseFiled(userTag);
        userTag.setTagType(tagType);
        userTag.setUserId(userId);
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
        //困难党员字典
        final Long HARD_MEMBERSHIP = 59428L;

        int value = 0;

        //从前端传过来的tagType标签集合
        List<Long> tagTypes = userTagDTO.getTagTypes();

        //如果从前端传过来的tagType标签集合为空则批量删除.
        if (CollectionUtil.isEmpty(tagTypes)) {
            return tabPbUserTagMapper.batchDeleteByUserId(userTagDTO.getUserId());
        }
        List<TabPbUserTag> list = tagTypes.stream().map(tagType -> {
            if (tagType == HARD_MEMBERSHIP) {
                HardshipPartyMemberDTO hardshipPartyDTO = new HardshipPartyMemberDTO();
                hardshipPartyDTO.setUserId(userTagDTO.getUserId());
                hardshipPartyDTO.setOrgId(userTagDTO.getOrgId());
                hardshipService.insertHardshipPartyMember(hardshipPartyDTO);
            }
            TabPbUserTag tabPbUserTag = new TabPbUserTag();
            tabPbUserTag.setUserId(userTagDTO.getUserId());
            tabPbUserTag.setTagType(tagType);
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbUserTag);
            return tabPbUserTag;
        }).collect(Collectors.toList());

        //获取数据库中原有用户的tagType标签集合(集合中也包含了usertagId)
        List<TabPbUserTag> dbTagList = tabPbUserTagMapper.selectTagTypesList(userTagDTO.getUserId());

        //如果数据库中原有用户的tagType集合为空，则批量增加
        if (CollectionUtil.isEmpty(dbTagList)) {
            return tabPbUserTagMapper.batchInsertUserTagDTO(list);
        }

        List<Long> deleteIds = new ArrayList<>();
        List<TabPbUserTag> insertList = new ArrayList<>();
        List<Long> dbUserTagList = new ArrayList<>();
        for (TabPbUserTag tabPbUserTag : dbTagList) {
            //前端没有但是数据库有的，删除
            if (!tagTypes.contains(tabPbUserTag.getTagType())) {
                deleteIds.add(tabPbUserTag.getUsertagId());
                if (tabPbUserTag.getTagType() == HARD_MEMBERSHIP) {
                    hardshipService.logicDeleteByUserId(userTagDTO.getUserId());
                }
            }
            dbUserTagList.add(tabPbUserTag.getTagType());
        }
        //前端有但是数据库里面没有的，增加
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
