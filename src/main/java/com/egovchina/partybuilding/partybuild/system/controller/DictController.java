package com.egovchina.partybuilding.partybuild.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.egovchina.partybuilding.partybuild.service.ITabSysDictService;
import com.egovchina.partybuilding.partybuild.system.dto.DictTree;
import com.egovchina.partybuilding.partybuild.system.entity.SysDict;
import com.egovchina.partybuilding.partybuild.system.service.SysDictService;
import com.egovchina.partybuilding.partybuild.system.vo.TreeUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2017-11-19
 */
@Deprecated
@Api(tags = "系统数据字典模块")
@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private SysDictService sysDictService;

	@Autowired
    private ITabSysDictService tabSysDictService;

	@Deprecated
    @ApiOperation(value = "通过ID查询字典信息", notes = "通过ID查询字典信息", httpMethod = "GET")
	@GetMapping("/{id}")
	public SysDict dict(@ApiParam(value = "字典ID",required=true)@PathVariable Integer id) {
		return tabSysDictService.selectById(id);
	}

	@Deprecated
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required=true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query",required=true),
            //@ApiImplicitParam(name = "orderByField", value = "排序字段", paramType = "query"),
            //@ApiImplicitParam(name = "isAsc", value = "是否正序", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "字典名称", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "字典类型", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父id", paramType = "query")
    })
	@GetMapping("/dictPage")
	public PageInfo<SysDict> dictPage(@ApiIgnore @RequestParam Map<String, Object> params) {
		return tabSysDictService.selectPage(params);
	}

	@Deprecated
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息", httpMethod = "GET")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "isAsc", value = "是否正序", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "字典名称", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "字典类型", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父id", paramType = "query")
    })
    @GetMapping("/dictList")
    public List<SysDict> dictList(@ApiIgnore @RequestParam Map<String, Object> params) {
        SysDict sysDict = JSONObject.parseObject(JSONObject.toJSONString(params), SysDict.class);
        return tabSysDictService.selectLists(sysDict);
    }

	@Deprecated
    @ApiOperation(value = "获取根目录字典", notes = "获取根目录字典", httpMethod = "GET")
    @GetMapping("/rootDictList")
    public List<SysDict> rootDictList(){
        return tabSysDictService.rootDictList();
    }

	@Deprecated
    @ApiOperation(value = "通过字典类型查找字典", notes = "通过字典类型查找字典", httpMethod = "GET")
	@GetMapping("/type/{type}")
	public List<SysDict> findDictByType(@ApiParam(value = "类型",required=true)@PathVariable String type) {
		return tabSysDictService.selectListByType(type.toUpperCase());
	}

	@Deprecated
    @ApiOperation(value = "返回type字典树", notes = "返回type字典树", httpMethod = "GET")
    @GetMapping("/typeTree/{type}")
    public List<DictTree> typeTree(@ApiParam(value = "类型",required=true)@PathVariable String type) {
    	type = type.toUpperCase();
        List<DictTree> dictTrees = tabSysDictService.selectTreeByType(type);
        SysDict dict = tabSysDictService.selectOneByType(type);
        return TreeUtil.bulid(dictTrees, dict!=null?dict.getId():0);
    }

    /**
     * 通过ID查询字典信息
     *
     * @return 字典信息
     */
    /*@ApiOperation(value = "通过ID查询字典信息", notes = "通过ID查询字典信息", httpMethod = "GET")
    @GetMapping("/{dictType}/{dictValue}")
    public DictDto getFlowOutMember(@ApiParam(value = "dictType",required=true)@PathVariable String dictType, @ApiParam(value = "dictValue",required=true)@PathVariable String dictValue) {
        return tabSysDictService.getFlowOutMember(dictType,dictValue);
    }*/

	@Deprecated
    @ApiOperation(value = "添加字典", notes = "添加字典", httpMethod = "POST")
	@PostMapping
	public Boolean dict(@ApiParam(value = "字典信息")@Valid @RequestBody SysDict sysDict) {
		return tabSysDictService.insert(sysDict);
	}

	@Deprecated
	@DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除字典", notes = "删除字典")
	public Boolean deleteDict(@ApiParam(value = "字典id",required=true)@PathVariable Integer id) {
		return tabSysDictService.deleteById(id);
	}

	@Deprecated
    @ApiOperation(value = "修改字典", notes = "修改字典", httpMethod = "PUT")
	@PutMapping
	public Boolean editDict(@ApiParam(value = "字典信息")@Valid @RequestBody SysDict sysDict) {
		return tabSysDictService.updateById(sysDict);
	}
}
