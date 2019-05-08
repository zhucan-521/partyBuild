package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbUserTagMapper;
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

    //困难党员字典id
    private final Long HARD_MEMBERSHIP = 59428L;

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
        if (HARD_MEMBERSHIP.equals(tabPbUserTag.getTagType())) {
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

    @Transactional
    @Override
    public void updateUserTagByTagType(List<TabPbUserTag> userTags) {
        userTags.forEach(userTag -> {
            Long userTagId = userTag.getUsertagId();
            Long tagType = userTag.getTagType();
            Long userId = userTag.getUserId();
            Integer id = userTag.getId();
            if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(id)) {
                throw new BusinessDataCheckFailException("用户Id或者id为空");
            }
            if (!ObjectUtils.isEmpty(userTagId) && tagType != null && tagType == 0L) {
                tabPbUserTagMapper.deleteByPrimaryKey(userTagId);
            }
            if (ObjectUtils.isEmpty(userTagId) && tagType != null && tagType == 1L) {
                this.addUserTag(userId, id.longValue());
            }
        });
    }

    /**
     * 批量插入标记
     *
     * @param userTagDTO 用户表及dto
     * @return
     */
    @Transactional
    @Override
    public int batchInsertUserTagDTO(UserTagDTO userTagDTO) {

        //如果从前端传过来的tagType标签集合为空则批量删除.
        boolean judge = quickDelete(userTagDTO);
        if (judge) {
            return 1;
        }

        //生成用户标签实体集合
        List<TabPbUserTag> list = generateTabPbUserTagList(userTagDTO);

        //获取数据库中原有用户的tagType标签集合(集合中也包含了usertagId)
        List<TabPbUserTag> dbTagList = tabPbUserTagMapper.selectTagTypesList(userTagDTO.getUserId());

        //如果数据库中原有用户的tagType集合为空，则批量增加
        judge = quickInsert(dbTagList, list);
        if (judge) {
            return 1;
        }

        List<Long> deleteIds = new ArrayList<>();
        List<TabPbUserTag> insertList = new ArrayList<>();
        //筛选出待新增和删除的数据
        filterNewAndDeletedData(userTagDTO, dbTagList, insertList, deleteIds);

        //分别新增和删除标签
        int value = 0;
        if (CollectionUtil.isNotEmpty(deleteIds)) {
            value += tabPbUserTagMapper.batchDeleteById(deleteIds);
        }
        if (CollectionUtil.isNotEmpty(insertList)) {
            value += tabPbUserTagMapper.batchInsertUserTagDTO(insertList);
        }
        return value;
    }

    /**
     * 快速删除
     *
     * @param userTagDTO
     * @return
     */
    private boolean quickDelete(UserTagDTO userTagDTO) {
        int result = 0;
        if (CollectionUtil.isEmpty(userTagDTO.getTagTypes())) {
            result = tabPbUserTagMapper.batchDeleteByUserId(userTagDTO.getUserId());
        }
        return result > 0;
    }

    /**
     * 快速增加
     *
     * @param dbTagList
     * @param list
     * @return
     */
    private boolean quickInsert(List<TabPbUserTag> dbTagList, List<TabPbUserTag> list) {
        int result = 0;
        if (CollectionUtil.isEmpty(dbTagList)) {
            result = tabPbUserTagMapper.batchInsertUserTagDTO(list);
        }
        return result > 0;
    }

    /**
     * 困难党员的维护
     *
     * @param tagType    标记类型
     * @param userTagDTO 用户标记dto
     */
    private void maintenanceOfDifficultPartyMembers(Long tagType, UserTagDTO userTagDTO) {
        if (HARD_MEMBERSHIP.equals(tagType)) {
            HardshipPartyMemberDTO hardshipPartyDTO = new HardshipPartyMemberDTO();
            hardshipPartyDTO.setUserId(userTagDTO.getUserId());
            hardshipPartyDTO.setOrgId(userTagDTO.getOrgId());
            PaddingBaseFieldUtil.paddingBaseFiled(hardshipPartyDTO);
            hardshipService.insertHardshipPartyMember(hardshipPartyDTO);
        }
    }

    /**
     * 生成TabPbUserTag实体
     *
     * @param userId  用户id
     * @param tagType 标记类型
     * @return
     */
    private TabPbUserTag generateTabPbUserTag(Long userId, Long tagType) {
        TabPbUserTag tabPbUserTag = new TabPbUserTag();
        tabPbUserTag.setUserId(userId);
        tabPbUserTag.setTagType(tagType);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbUserTag);
        return tabPbUserTag;
    }

    /**
     * 生成TabPbUserTag实体集合
     *
     * @param userTagDTO 用户标记dto
     * @return
     */
    private List<TabPbUserTag> generateTabPbUserTagList(UserTagDTO userTagDTO) {
        return userTagDTO.getTagTypes().stream().map(tagType -> {
            //针对困难党员进行处理
            maintenanceOfDifficultPartyMembers(tagType, userTagDTO);
            //返回用户标记实体
            return generateTabPbUserTag(userTagDTO.getUserId(), tagType);
        }).collect(Collectors.toList());
    }

    /**
     * 筛选新增和删除数据
     *
     * @param userTagDTO 用户标记dto
     * @param dbTagList  数据库标记集合
     * @param insertList 需要添加的标记集合
     * @param deleteIds  需要删除的标记集合
     */
    private void filterNewAndDeletedData(UserTagDTO userTagDTO, List<TabPbUserTag> dbTagList, List<TabPbUserTag> insertList, List<Long> deleteIds) {
        List<Long> dbUserTagList = new ArrayList<>();
        List<Long> tagTypes = userTagDTO.getTagTypes();
        for (TabPbUserTag tabPbUserTag : dbTagList) {
            //前端没有但是数据库有的，删除
            if (!tagTypes.contains(tabPbUserTag.getTagType())) {
                deleteIds.add(tabPbUserTag.getUsertagId());
                if (HARD_MEMBERSHIP.equals(tabPbUserTag.getTagType())) {
                    hardshipService.deleteByHardshipId(userTagDTO.getUserId());
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
    }
}
