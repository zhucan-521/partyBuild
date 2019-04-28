package com.egovchina.partybuilding.partybuild.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.partybuild.system.dto.DeptTree;
import com.egovchina.partybuilding.partybuild.entity.SysDept;

import java.util.List;

/**
 * <p>
 * 组织 服务类
 * </p>
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询组织树菜单
     *
     * @param sysDeptEntityWrapper
     * @return 树
     */
    List<DeptTree> selectListTree(EntityWrapper<SysDept> sysDeptEntityWrapper);

    List<DeptTree> selectListTree(Wrapper<SysDept> wrapper,int root);

    /**
     * 根据parentId查询组织树菜单
     *
     * @param sysDeptEntityWrapper
     * @return
     */
    List<DeptTree> selectListTreeByParentId(EntityWrapper<SysDept> sysDeptEntityWrapper);

    /**
     * 添加信息组织
     *
     * @param sysDept
     * @return
     */
    Boolean insertDept(SysDept sysDept);

    /**
     * 删除组织
     *
     * @param id 组织 ID
     * @return 成功、失败
     */
    Boolean deleteDeptById(Integer id, Integer parentId);

    /**
     * 更新组织
     *
     * @param sysDept 组织信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);

}
