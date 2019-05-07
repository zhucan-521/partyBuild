package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.OrganizationDTO;
import com.egovchina.partybuilding.partybuild.dto.OrganizationPositionDTO;
import com.egovchina.partybuilding.partybuild.entity.OrganizationQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.vo.ContainsStatisticsOrganizationVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPartyBuildingWorkVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPositionVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织信息service层接口
 *
 * @Author Zhang Fan
 **/
public interface OrganizationService {

    /**
     * 根据条件查询组织vo
     *
     * @param queryBean 查询实体
     * @param page      分页实体
     * @return
     */
    List<OrganizationVO> selectOrganizationVOWithCondition(OrganizationQueryBean queryBean, Page page);

    /**
     * 检测组织名称有效性
     *
     * @param orgName 组织名称
     * @param orgId   组织id
     * @return
     */
    Boolean checkOrgNameAvailability(String orgName, Long orgId);

    /**
     * 校验组织编码的可用性
     *
     * @param orgCode
     * @param deptId
     * @return
     */
    Boolean checkOrgCodeAvailability(String orgCode, Long deptId);

    /**
     * 根据id查询组织信息
     *
     * @param deptId id
     * @return
     */
    SysDept selectByPrimaryKey(Long deptId);

    /**
     * 新增组织
     *
     * @param organizationDTO 组织DTO
     * @return
     */
    int insertOrganization(OrganizationDTO organizationDTO);

    /**
     * 根据id逻辑删除组织
     *
     * @param orgId 组织id
     * @return
     */
    int logicDeleteById(Long orgId);

    /**
     * 查询组织信息包括关联的信息(单位等)
     *
     * @param orgId 组织ID
     * @return
     */
    OrganizationVO selectOrganizationVOByOrgId(Long orgId);

    /**
     * 修改组织及组织相关联的实体
     *
     * @param organizationDTO
     * @return
     */
    int updateOrganization(OrganizationDTO organizationDTO);

    /**
     * 根据组件修改组织信息
     *
     * @param sysDept 组织实体
     * @return
     */
    int updateByPrimaryKeySelective(SysDept sysDept);

    /**
     * 判断组织是否存在
     *
     * @param deptId 组织id
     * @return
     */
    boolean checkOrgIsExists(Long deptId);

    /**
     * 根据条件查询组织阵地vo列表
     *
     * @param conditions 条件
     * @param page       分页实体
     * @return
     */
    List<OrganizationPositionVO> selectOrganizationPositionVOByCondition(Map<String, Object> conditions, Page page);

    /**
     * 根据id查询组织阵地详情包含附件
     *
     * @param orgId 组织id
     * @return
     */
    OrganizationPositionVO selectOrganizationPositionVOAndAttachmentsById(Long orgId);

    /**
     * 修改组织阵地
     *
     * @param organizationPositionDTO 阵地DTO
     * @return
     */
    int updateOrganizationPosition(OrganizationPositionDTO organizationPositionDTO);

    /**
     * 如有必要,修改 full_path (包括下级组织)
     *
     * @param sysDept 组织
     */
    void modifyFullPathAndSubDeptIfNecessary(SysDept sysDept);

    /**
     * 查询组织简单列表根据条件
     *
     * @param conditions 条件
     * @param page       分页实体
     * @return
     */
    List<HashMap<String, Object>> selectOrganizationSimpleListByCondition(Map<String, Object> conditions, Page page);

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
     *
     * @param deptId
     * @return
     */
    SysDept selectAloneByPrimaryKey(Long deptId);

    /**
     * 链接统计信息
     *
     * @param organizationId 组织id
     * @return
     */
    ContainsStatisticsOrganizationVO linkStatisticsData(Long organizationId);

    /**
     * 根据组织id统计党建工作信息
     *
     * @param deptId
     * @return
     */
    OrganizationPartyBuildingWorkVO selectOrganizationPartyBuildingWorkVOByOrgId(Long deptId);

}
