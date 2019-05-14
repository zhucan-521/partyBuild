package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgTagDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgTagMapper;
import com.egovchina.partybuilding.partybuild.service.OrgTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;

/**
 * @description: 组织标签实现类
 * @author: WuYunJie
 * @create: 2019-05-13 22:31
 **/
@Service
public class OrgTagServiceImpl implements OrgTagService {

    @Autowired
    private TabPbOrgTagMapper tabPbOrgTagMapper;

    @Transactional
    @Override
    public int batchInsertOrgTagDTO(OrgTagDTO orgTagDTO) {
        //如果标签为空，全部删除
        if (CollectionUtil.isEmpty(orgTagDTO.getOrgTagTypes())) {
            return batchUpdateOrgTagByOrgId(orgTagDTO.getOrgId());
        }
        //如果没有记录，直接新增
        List<TabPbOrgTag> dbTabPbOrgTags = tabPbOrgTagMapper.selectByOrgId(orgTagDTO.getOrgId());
        if (CollectionUtil.isEmpty(dbTabPbOrgTags)) {
            return insertOrgTagDTO(orgTagDTO);
        }

        List<TabPbOrgTag> deleteOrgTags = new ArrayList<>();
        List<TabPbOrgTag> insertList = new ArrayList<>();
        //筛选新增和删除数据
        filterNewAndDeletedData(orgTagDTO, dbTabPbOrgTags, insertList, deleteOrgTags);

        //分别新增和删除标签
        int value = 1;
        if (CollectionUtil.isNotEmpty(deleteOrgTags)) {
            value += tabPbOrgTagMapper.batchDelOrgTag(deleteOrgTags);
        }
        if (CollectionUtil.isNotEmpty(insertList)) {
            value += tabPbOrgTagMapper.batchInsertSelective(insertList);
        }
        return value;
    }

    @Override
    public int batchUpdateOrgTagByOrgId(Long orgId) {
        TabPbOrgTag tabPbOrgTag = new TabPbOrgTag();
        tabPbOrgTag.setOrgId(orgId);
        tabPbOrgTag.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbOrgTag);
        return tabPbOrgTagMapper.batchUpdateOrgTagByOrgId(tabPbOrgTag);
    }

    @Override
    public int insertOrgTagDTO(OrgTagDTO orgTagDTO) {
        List<TabPbOrgTag> tabPbOrgTags = generateTabPbOrgTagList(orgTagDTO);
        paddingBaseFiled(tabPbOrgTags);
        return tabPbOrgTagMapper.batchInsertSelective(tabPbOrgTags);
    }

    //生成组织标签
    private List<TabPbOrgTag> generateTabPbOrgTagList(OrgTagDTO orgTagDTO) {
        return orgTagDTO.getOrgTagTypes().stream().map(orgTagTypes ->
                new TabPbOrgTag().setOrgId(orgTagDTO.getOrgId()).setOrgTagType(orgTagTypes)).collect(Collectors.toList());
    }

    /**
     * 筛选新增和删除数据
     *
     * @param orgTagDTO     用户标记dto
     * @param dbTagList     数据库标记集合
     * @param insertList    需要添加的标记集合
     * @param deleteOrgTags 需要删除的标记集合
     */
    private void filterNewAndDeletedData(
            OrgTagDTO orgTagDTO, List<TabPbOrgTag> dbTagList, List<TabPbOrgTag> insertList, List<TabPbOrgTag> deleteOrgTags) {
        List<Long> dbOrgTagList = new ArrayList<>();
        List<Long> orgTagTypes = orgTagDTO.getOrgTagTypes();
        for (TabPbOrgTag tabPbOrgTag : dbTagList) {
            //前端没有但是数据库有的，删除
            if (!orgTagTypes.contains(tabPbOrgTag.getOrgTagType())) {
                TabPbOrgTag orgTag = new TabPbOrgTag();
                orgTag.setOrgTagId(tabPbOrgTag.getOrgTagId());
                orgTag.setDelFlag(CommonConstant.STATUS_DEL);
                paddingUpdateRelatedBaseFiled(orgTag);
                deleteOrgTags.add(orgTag);
            }
            dbOrgTagList.add(tabPbOrgTag.getOrgTagType());
        }
        //前端有但是数据库里面没有的，增加
        for (Long orgTagType : orgTagTypes) {
            if (!dbOrgTagList.contains(orgTagType)) {
                TabPbOrgTag orgTag = new TabPbOrgTag();
                orgTag.setOrgId(orgTagDTO.getOrgId());
                orgTag.setOrgTagType(orgTagType);
                PaddingBaseFieldUtil.paddingBaseFiled(orgTag);
                insertList.add(orgTag);
            }
        }
    }

}
