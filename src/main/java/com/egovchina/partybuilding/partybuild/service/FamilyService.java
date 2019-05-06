package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.FamilyMemberDTO;
import com.egovchina.partybuilding.partybuild.vo.FamilyMemberVO;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/26
 */
public interface FamilyService  {

    /**
     * 根据党员id加载list数据
     * @param partyMemberId
     * @return
     */
    List<FamilyMemberVO> selectFamilyMemberList(Long partyMemberId);

    /**
     * 根据主键ID查询单条记录
     * @param relationId
     * @return
     */
    FamilyMemberVO selectFamilyMemberById(Long relationId);

    /**
     * 根据主键ID删除单条记录
     * @param relationId
     */
    int deleteFamilyMemberByPrimaryKey(Long relationId);

    /**
     * 保存实体
     * @param tabPbFamily
     */
    int addFamilyDTO(FamilyMemberDTO tabPbFamily);

    /**
     * 更新不为null的数据
     * @param tabPbFamily
     */
    int updateByPrimaryKeySelective(FamilyMemberDTO tabPbFamily);

}
