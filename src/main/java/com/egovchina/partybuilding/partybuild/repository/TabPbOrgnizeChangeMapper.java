package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.OrgChangeQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;
import com.egovchina.partybuilding.partybuild.vo.OrgnizeLifeGraphVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbOrgnizeChangeMapper {
    int deleteByPrimaryKey(Long changeId);

    int insert(TabPbOrgnizeChange record);

    int insertSelective(TabPbOrgnizeChange record);

    TabPbOrgnizeChange selectByPrimaryKey(Long changeId);

    int updateByPrimaryKeySelective(TabPbOrgnizeChange record);

    int updateByPrimaryKeyWithBLOBs(TabPbOrgnizeChange record);

    int updateByPrimaryKey(TabPbOrgnizeChange record);

    /**
     * 根据组织主键查询最新的变更记录
     *
     * @param changeId 变动id
     * @return OrgChangeVO
     */
    OrgChangeVO selectOrgChangeById(@Param("changeId") Long changeId);

    /**
     * 查询组织变动记录
     *
     * @param orgChangeQueryBean 查询实体
     * @return List<OrgChangeVO>
     */
    List<OrgChangeVO> selectOrgChangeVOList(OrgChangeQueryBean orgChangeQueryBean);

    /**
     * 根据组织主键查询最新的变更记录
     *
     * @param deptId     组织id
     * @param changeType 变动类型
     * @return OrgChangeVO
     */
    OrgChangeVO selectOrgChangeByDeptIdOrderTime(@Param("deptId") Long deptId, @Param("changeType") Long changeType);

    /**
     * 组织历史信息图
     *
     * @param orgnizeLife 组织生活复选框，默认显示组织调整
     * @param orgId       组织id
     * @return
     */
    List<OrgnizeLifeGraphVO> selectOrgnizeLifeGraphVO(@Param("orgnizeLife") Boolean orgnizeLife, @Param("orgId") Long orgId);

}