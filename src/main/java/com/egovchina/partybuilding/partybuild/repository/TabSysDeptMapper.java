package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.PartyBuildingWorkInfoDto;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDtoWithCountInfo;
import com.egovchina.partybuilding.partybuild.dto.TabDeptPositionDto;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface TabSysDeptMapper {
    int deleteByPrimaryKey(Long deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKeyWithBLOBs(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> selectWithConditions(Map<String, Object> conditions);

    SysDept checkOrgNameAvailability(String deptName);

    void batchInsert(List<SysDept> effectiveList);

    int logicDeleteById(Long deptId);

    SysDeptDto selectWithAboutInfoByPrimaryKey(Long deptId);

    void insertToDeptRelationTable(@Param("parentId") Integer parentId, @Param("deptId") Integer deptId);

    void batchInsertRelation(List<SysDept> effectiveList);

    List<TabDeptPositionDto> selectPositionWithConditions(Map<String, Object> conditions);

    TabDeptPositionDto selectPositionWithAboutInfoByPrimaryKey(Long deptId);

    int updateLeaderUserIdByDeptId(@Param("deptId") Long deptId, @Param("leaderUserId") Long leaderUserId);

    SysDept selectAloneByPrimaryKey(Long deptId);

    int updateWithRelationByPrimaryKeySelective(SysDept sysDept);

    /**
     * 修改下级组织的 full_path
     *
     * @param oldFullPath 旧的 full_path
     * @param newFullPath 新的 full_path
     * @param nowDeptId   当前操作的组织id
     */
    int updateFullPathForSubs(@Param("oldFullPath") String oldFullPath,
                              @Param("newFullPath") String newFullPath,
                              @Param("nowDeptId") Integer nowDeptId);

    /**
     * 数据里列表-map
     *
     * @param conditions
     * @return
     */
    List<HashMap<String, Object>> selectToMapWithConditions(Map<String, Object> conditions);

    /**
     * 检查
     *
     * @param name
     * @return
     */
    int checkNameOrShortName(@Param("name") String name);

    /**
     * 推送结对组织ID到组织表
     *
     * @param pairOrgId 结对组织ID
     * @param orgId     组织ID
     * @return
     */
    int pushPairOrgIdToDeptTable(@Param("pairOrgId") Long pairOrgId, @Param("orgId") Long orgId);

    /**
     * 统计组织和党员信息
     *
     * @param orgId
     * @return
     */
    SysDeptDtoWithCountInfo countOrgWithPartyManInfo(@Param("orgId") Integer orgId);

    /**
     * 统计党建工作信息
     *
     * @param deptId
     * @return
     */
    PartyBuildingWorkInfoDto countPartyBuildingWorkInfo(Long deptId);

    /**
     * desc: 判断数据是否存在
     *
     * @param deptId 主键ID
     * @return boolean
     * @author FanYanGen
     * @date 2019/4/24 11:37
     **/
    boolean isExist(Long deptId);

}