package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.IBaseExcelImport;
import com.egovchina.partybuilding.partybuild.dto.PartyBuildingWorkInfoDto;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDtoWithCountInfo;
import com.egovchina.partybuilding.partybuild.dto.TabDeptPositionDto;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织信息service层接口
 *
 * @Author Zhang Fan
 **/
public interface TabSysDeptService extends IBaseExcelImport<SysDept> {

    List<SysDept> selectWithConditions(Map<String, Object> conditions, Page page);

    boolean checkOrgNameAvailability(String deptName);

    int insert(SysDept sysDept);

    SysDept selectByPrimaryKey(Long deptId);

    int insertWithAbout(SysDeptDto sysDeptDto);

    int logicDeleteById(Long deptId);

    /**
     * 查询组织信息包括关联的信息(单位等)
     *
     * @param deptId
     * @return
     */
    SysDeptDto selectWithAboutInfoByPrimaryKey(Long deptId);

    /**
     * 修改组织及组织相关联的实体
     *
     * @param sysDeptDto
     * @return
     */
    int updateWithAbout(SysDeptDto sysDeptDto);

    int updateByPrimaryKeySelective(SysDept record);

    boolean checkOrgIsExists(Long deptId);

    List<TabDeptPositionDto> selectPositionWithConditions(Map<String, Object> conditions, Page page);

    TabDeptPositionDto selectPositionWithAboutInfoByPrimaryKey(Long deptId);

    int updatePositionWithAnnexs(SysDept sysDept, List<TabPbAttachment> attList);

    /**
     * 如有必要,修改 full_path (包括下级组织)
     *
     * @param sysDept
     * @return
     */
    boolean modifyFullPathAndSubDeptIfNecessary(SysDept sysDept);

    /**
     * 数据列表，返回map
     *
     * @param conditions
     * @param page
     * @return
     */
    List<HashMap<String, Object>> selectToMapWithConditions(Map<String, Object> conditions, Page page);

    /**
     * 推送结对组织ID到组织表
     *
     * @param pairOrgId 结对组织ID
     * @param orgId     组织ID
     * @return
     */
    int pushPairOrgIdToDeptTable(Long pairOrgId, Long orgId);

    /**
     * 根据id查询查询不带附加数据的信息
     * @param deptId
     * @return
     */
    SysDept selectAloneByPrimaryKey(Long deptId);

    /**
     * 链接统计信息
     * @param sysDept
     */
    SysDeptDtoWithCountInfo linkCountInfo(SysDept sysDept);

    /**
     * 根据组织id统计党建工作信息
     * @param deptId
     * @return
     */
    PartyBuildingWorkInfoDto countPartyBuildingWorkInfo(Long deptId);
}
