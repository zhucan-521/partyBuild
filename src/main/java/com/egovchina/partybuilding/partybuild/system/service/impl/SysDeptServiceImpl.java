package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.partybuild.system.dto.DeptTree;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.system.entity.SysDeptRelation;
import com.egovchina.partybuilding.partybuild.system.mapper.SysDeptMapper;
import com.egovchina.partybuilding.partybuild.system.mapper.SysDeptRelationMapper;
import com.egovchina.partybuilding.partybuild.system.service.SysDeptService;
import com.egovchina.partybuilding.partybuild.system.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.system.vo.TreeUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织管理 服务实现类
 * </p>
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptRelationMapper sysDeptRelationMapper;

    /**
     * 添加信息组织
     *
     * @param dept 组织
     * @return
     */
    @Override
    //@CacheEvict(value = "dept_trees", key = "#sysDept.parentId")
    public Boolean insertDept(SysDept dept) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dept, sysDept);
        this.insert(sysDept);
        this.insertDeptRelation(sysDept);
        return Boolean.TRUE;
    }

    /**
     * 维护组织关系
     *
     * @param sysDept 组织
     */
    private void insertDeptRelation(SysDept sysDept) {
        //增加组织关系表
        SysDeptRelation deptRelation = new SysDeptRelation();
        deptRelation.setDescendant(sysDept.getParentId());
        List<SysDeptRelation> deptRelationList = sysDeptRelationMapper.selectList(new EntityWrapper<>(deptRelation));
        for (SysDeptRelation sysDeptRelation : deptRelationList) {
            sysDeptRelation.setDescendant(sysDept.getDeptId());
            sysDeptRelationMapper.insert(sysDeptRelation);
        }
        //自己也要维护到关系表中
        SysDeptRelation own = new SysDeptRelation();
        own.setDescendant(sysDept.getDeptId());
        own.setAncestor(sysDept.getDeptId());
        sysDeptRelationMapper.insert(own);
    }

    /**
     * 删除组织
     *
     * @param id 组织 ID
     * @return 成功、失败
     */
    @Override
    //@CacheEvict(value = "dept_trees", key = "#parentId")
    public Boolean deleteDeptById(Integer id, Integer parentId) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(id);
        sysDept.setUpdateTime(new Date());
        sysDept.setDelFlag(CommonConstant.STATUS_DEL);
        this.deleteById(sysDept);
        sysDeptRelationMapper.deleteAllDeptRealtion(id);
        return Boolean.TRUE;
    }

    /**
     * 更新组织
     *
     * @param sysDept 组织信息
     * @return 成功、失败
     */
    @Override
    //@CacheEvict(value = "dept_trees", key = "#sysDept.parentId")
    public Boolean updateDeptById(SysDept sysDept) {
        //更新组织状态
        this.updateById(sysDept);
        //更新组织关系
        SysDeptRelation relation = new SysDeptRelation();
        relation.setAncestor(sysDept.getParentId());
        relation.setDescendant(sysDept.getDeptId());
        sysDeptRelationMapper.updateDeptRealtion(relation);
        return Boolean.TRUE;
    }

    /**
     * 查询组织树
     *
     * @param sysDeptEntityWrapper
     * @return 树
     */
    @Override
    public List<DeptTree> selectListTree(EntityWrapper<SysDept> sysDeptEntityWrapper) {
        sysDeptEntityWrapper.orderBy("order_num", false);
        return getDeptTree(this.selectList(sysDeptEntityWrapper), 0);
    }

    /**
     * 查询组织树
     *
     * @param wrapper
     * @return 树
     */
    @Override
    public List<DeptTree> selectListTree(Wrapper<SysDept> wrapper,int root) {
        return getDeptTreeOne(this.selectList(wrapper), root);
    }

    /**
     * 根据parentId查询组织树菜单
     *
     * @param sysDeptEntityWrapper
     * @return
     */
    @Override
    //@Cacheable(value = "dept_trees", key = "#sysDeptEntityWrapper.getEntity().parentId")
    public List<DeptTree> selectListTreeByParentId(EntityWrapper<SysDept> sysDeptEntityWrapper) {
        sysDeptEntityWrapper.orderBy("order_num", true);
        List<SysDept> depts = this.selectList(sysDeptEntityWrapper);
        List<DeptTree> trees = new ArrayList<>();
        DeptTree node;
        for (SysDept dept : depts) {
            node = new DeptTree();
            node.setId(dept.getDeptId());
            node.setParentId(dept.getParentId());
            node.setName(dept.getOrgShortName());
            node.setIsParent(dept.getIsParent());
            node.setIsBranch((byte) (dept.ifBranch() ? 1 : 0));
            trees.add(node);
            node = null;
        }
        return trees;
    }

    /**
     * 构建组织树
     *
     * @param depts 组织
     * @param root  根节点
     * @return
     */
    private List<DeptTree> getDeptTree(List<SysDept> depts, int root) {
        List<DeptTree> trees = new ArrayList<>();
        DeptTree node;
        for (SysDept dept : depts) {
            if (dept.getParentId().equals(dept.getDeptId())) {
                continue;
            }
            node = new DeptTree();
            node.setId(dept.getDeptId());
            node.setParentId(dept.getParentId());
            node.setName(dept.getName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }

    /**
     * 构建组织树
     * huang重写的
     * @param depts 组织
     * @param root  根节点
     * @return
     */
    private List<DeptTree> getDeptTreeOne(List<SysDept> depts, int root) {
        List<DeptTree> trees = new ArrayList<>();
        DeptTree node;
        for (SysDept dept : depts) {
            if (dept.getParentId().equals(dept.getDeptId())) {
                continue;
            }
            node = new DeptTree();
            node.setId(dept.getDeptId());
            node.setParentId(dept.getParentId());
            node.setName(dept.getName());
            trees.add(node);
            if(dept.getDeptId().equals(root)){
                root = dept.getParentId();
            }
        }
        return TreeUtil.bulid(trees, root);
    }

}
