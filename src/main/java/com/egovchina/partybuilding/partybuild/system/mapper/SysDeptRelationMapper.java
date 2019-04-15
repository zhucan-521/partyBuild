package com.egovchina.partybuilding.partybuild.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.system.entity.SysDeptRelation;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lengleng
 * @since 2018-02-12
 */
public interface SysDeptRelationMapper extends BaseMapper<SysDeptRelation> {

    /**
     * 删除组织关系表数据
     *
     * @param id 组织ID
     */
    void deleteAllDeptRealtion(Integer id);

    /**
     * 更改部分关系表数据
     *
     * @param deptRelation
     */
    void updateDeptRealtion(SysDeptRelation deptRelation);

    /**
     * 推送结对组织ID到组织表
     *
     * @param pairOrgId
     * @param orgId
     * @return
     */
    int pushPairOrgIdToDeptTable(@Param("pairOrgId") Long pairOrgId, @Param("orgId") Long orgId);
}
