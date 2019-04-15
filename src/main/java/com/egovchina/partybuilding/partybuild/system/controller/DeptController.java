package com.egovchina.partybuilding.partybuild.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.partybuild.system.dto.DeptTree;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.egovchina.partybuilding.partybuild.system.service.SysDeptService;
import com.egovchina.partybuilding.partybuild.system.util.CommonConstant;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
@Api(tags = "系统组织模块")
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {

    private final SysDeptService sysDeptService;

    @Deprecated
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

    @Deprecated
    @ApiOperation(value = "根据父节点id查询组织信息", notes = "根据父节点id查询部门信息", httpMethod = "GET")
    @GetMapping("/getByParentId/{parentId}")
    public List<DeptTree> getByParentId(@ApiParam(value = "父节点id") @PathVariable Integer parentId) {
        SysDept condition = new SysDept();
        condition.setParentId(parentId);
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return sysDeptService.selectListTreeByParentId(new EntityWrapper<>(condition));
    }

    @Deprecated
    @ApiOperation(value = "返回树形菜单集合", notes = "返回树形菜单集合", httpMethod = "GET")
    @GetMapping(value = "/tree")
    public List<DeptTree> getTree() {
        SysDept condition = new SysDept();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return sysDeptService.selectListTree(new EntityWrapper<>(condition));
    }

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    @ApiOperation(value = "删除组织", notes = "删除组织", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean delete(@ApiParam(value = "组织id") @PathVariable Integer id) {
        SysDept dept = sysDeptService.selectById(id);
        return sysDeptService.deleteDeptById(id, dept.getParentId());
    }

    @Deprecated
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
