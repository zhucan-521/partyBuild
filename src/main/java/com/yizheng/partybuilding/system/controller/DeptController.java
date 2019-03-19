package com.yizheng.partybuilding.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yizheng.partybuilding.system.dto.DeptTree;
import com.yizheng.partybuilding.system.entity.SysDept;
import com.yizheng.partybuilding.system.service.SysDeptService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.commons.util.CollectionUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "系统组织模块")
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {

    private final SysDeptService sysDeptService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @ApiOperation(value = "通过ID查询组织信息", notes = "通过ID查询部门信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public SysDept get(@ApiParam(value = "组织id") @PathVariable Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("dept_id", id);
        map.put("del_flag", "0");
        List<SysDept> sysDepts = sysDeptService.selectByMap(map);
        if (CollectionUtil.isNotEmpty(sysDepts)) {
            return sysDepts.get(0);
        }
        return null;
    }

    /**
     * 通过parentId查询组织信息
     *
     * @param parentId
     * @return
     */
    @ApiOperation(value = "根据父节点id查询组织信息", notes = "根据父节点id查询部门信息", httpMethod = "GET")
    @GetMapping("/getByParentId/{parentId}")
    public List<DeptTree> getByParentId(@ApiParam(value = "父节点id") @PathVariable Integer parentId) {
        SysDept condition = new SysDept();
        condition.setParentId(parentId);
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return sysDeptService.selectListTreeByParentId(new EntityWrapper<>(condition));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @ApiOperation(value = "返回树形菜单集合", notes = "返回树形菜单集合", httpMethod = "GET")
    @GetMapping(value = "/tree")
    public List<DeptTree> getTree() {
        SysDept condition = new SysDept();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return sysDeptService.selectListTree(new EntityWrapper<>(condition));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @ApiOperation(value = "返回树形菜单集合", notes = "返回树形菜单集合", httpMethod = "GET")
    @GetMapping(value = "/tree/{type}")
    public List<DeptTree> getTree(@ApiParam(value = "类型",required=true)@PathVariable int type) {
        SysDept condition = new SysDept();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        Wrapper<SysDept> wrapper = new EntityWrapper<>(condition);
        if(type!=0 && type != 14307){
            wrapper.like("full_path",","+String.valueOf(type));
        }
        return sysDeptService.selectListTree(wrapper.orderBy("order_num", false),type);
    }

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @ApiOperation(value = "添加组织", notes = "添加组织", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组织名称", paramType = "form"),
            @ApiImplicitParam(name = "parentId", value = "父组织ID", paramType = "form"),
            @ApiImplicitParam(name = "sort", value = "排序值", paramType = "form")
    })
    @PostMapping
    public Boolean add(@Valid @ApiIgnore SysDept sysDept) {
        return sysDeptService.insertDept(sysDept);
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @ApiOperation(value = "删除组织", notes = "删除组织", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean delete(@ApiParam(value = "组织id") @PathVariable Integer id) {
        SysDept dept = sysDeptService.selectById(id);
        return sysDeptService.deleteDeptById(id, dept.getParentId());
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @ApiOperation(value = "编辑组织", notes = "编辑组织", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组织名称", paramType = "form"),
            @ApiImplicitParam(name = "parentId", value = "父组织ID", paramType = "form"),
            @ApiImplicitParam(name = "sort", value = "排序", paramType = "form")
    })
    @PutMapping
    public Boolean edit(@ApiIgnore @Valid SysDept sysDept) {
        sysDept.setUpdateTime(new Date());
        return sysDeptService.updateDeptById(sysDept);
    }

}
