package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;
import com.egovchina.partybuilding.partybuild.vo.FamilyMemberVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbFamilyMapper extends BaseMapper<TabPbFamily> {
    int deleteByPrimaryKey(TabPbFamily family);

    int insertSelective(TabPbFamily record);

    TabPbFamily selectByPrimaryKey(Long relationId);

    int updateByPrimaryKeySelective(TabPbFamily record);

    int updateByPrimaryKey(TabPbFamily record);

    /**
     * 根据用户id去查找用户的家庭成员集合
     * @param userId
     * @return
     */
    List<FamilyMemberVO> getFamilyMemberVoByUserId(Long userId);

    /**
     * 根据主键查询家庭成员
     *
     * @param relationId
     * @return
     */
    TabPbFamily findById(Long relationId);

    /**
     * 批量插入家庭
     * @param tabPbFamilyList
     * @return
     */
    int batchInsertFamilyList(List<TabPbFamily> tabPbFamilyList);

    /**
     * 书记导出家庭数据数据
     * @param userId 用户id
     * @return
     */
    List<Map<String, Object>> selectExportDataForSecretaryByUserId(Long userId);
}