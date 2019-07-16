package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.OrganizationQueryBean;
import com.egovchina.partybuilding.partybuild.vo.ContainsStatisticsOrganizationVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPartyBuildingWorkVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPositionVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    int updateByPrimaryKey(SysDept record);

    /**
     * 根据条件查询组织VO列表
     *
     * @param queryBean 查询实体
     * @return
     */
    List<OrganizationVO> selectOrganizationVOWithCondition(OrganizationQueryBean queryBean);

    Boolean checkOrgNameAvailability(@Param("orgName") String orgName, @Param("orgId") Long orgId);

    Boolean checkOrgCodeAvailability(@Param("orgCode") String orgCode, @Param("orgId") Long deptId);

    void batchInsert(List<SysDept> effectiveList);

    int logicDeleteById(Long deptId);

    /**
     * 根据组织id查询组织vo实体（包含单位等信息）
     *
     * @param orgId 组织id
     * @return
     */
    OrganizationVO selectOrganizationVOAndUnitsByOrgId(Long orgId);

    void insertToDeptRelationTable(@Param("parentId") Long parentId, @Param("deptId") Long deptId);

    void batchInsertRelation(List<SysDept> effectiveList);

    List<OrganizationPositionVO> selectOrganizationPositionVOByCondition(Map<String, Object> conditions);

    OrganizationPositionVO selectOrganizationPositionVOAndAttachmentsById(Long deptId);

    int updateLeaderUserIdByDeptId(@Param("deptId") Long deptId, @Param("leaderUserId") Long leaderUserId);

    SysDept selectAloneByPrimaryKey(Long deptId);

    /**
     * 修改下级组织的 full_path
     *
     * @param oldFullPath 旧的 full_path
     * @param newFullPath 新的 full_path
     * @param nowDeptId   当前操作的组织id
     */
    int updateFullPathForSubs(@Param("oldFullPath") String oldFullPath,
                              @Param("newFullPath") String newFullPath,
                              @Param("nowDeptId") Long nowDeptId);

    /**
     * 修改下级组织的full_path及内部编码
     *
     * @param oldFullPath  旧的full_path
     * @param newFullPath  新的full_path
     * @param oldInnerCode 旧的内部编码
     * @param newInnerCode 新的内部编码
     * @param nowDeptId    当前操作的组织id
     * @return
     */
    int updateFullPathAndInnerCodeForSubs(@Param("oldFullPath") String oldFullPath,
                                          @Param("newFullPath") String newFullPath,
                                          @Param("oldInnerCode") String oldInnerCode,
                                          @Param("newInnerCode") String newInnerCode,
                                          @Param("nowDeptId") Long nowDeptId);

    /**
     * 数据里列表-map
     *
     * @param conditions
     * @return
     */
    List<HashMap<String, Object>> selectToMapByCondition(Map<String, Object> conditions);

    /**
     * 检查组织名称或简称是否重复
     *
     * @param name
     * @return int
     */
    Boolean checkNameOrShortName(@Param("name") String name, @Param("orgId") Long orgId);

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
    ContainsStatisticsOrganizationVO countOrganizationStatisticsByOrgId(@Param("orgId") Long orgId);

    /**
     * 统计党建工作信息
     *
     * @param deptId
     * @return
     */
    OrganizationPartyBuildingWorkVO selectOrganizationPartyBuildingWorkVOByOrgId(Long deptId);

    /**
     * desc: 判断数据是否存在
     *
     * @param deptId 主键ID
     * @return boolean
     * @author FanYanGen
     * @date 2019/4/24 11:37
     **/
    boolean checkIsExistByOrgId(Long deptId);

    /**
     * 检查是否存在非直属
     *
     * @param orgId     组织id
     * @param subOrgIds 直属组织id集合
     * @return
     */
    boolean checkIsExistUnDirectSub(@Param("orgId") Long orgId, @Param("subOrgIds") List<Long> subOrgIds);

    /**
     * desc: 根据主键查询组织的建立时间
     *
     * @param orgId 主键ID
     * @return Date
     * @auther FanYanGen
     * @date 2019-05-22 21:05
     */
    Date getFoundedDateByOrgId(Long orgId);

    /**
     * 根据父id查询子层级中新的内部编码
     *
     * @param parentId 父id
     * @return
     */
    String selectSubLevelNewInnerCodeByParentId(Long parentId);

    /**
     * 校验该组织是否为父级组织
     *
     * @param orgId
     * @return
     */
    Boolean checkOrgIsParentOrg(Long orgId);

    /**
     * 通过组织名称查询组织id
     *
     * @param name
     * @return
     */
    Long selectIdByName(String name);

    /**
     * 查看当前是否支部
     *
     * @param orgId
     * @return
     */
    Boolean checkOrgIsBranch(@Param("orgId") Long orgId);
}