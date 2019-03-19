package com.yizheng.partybuilding.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yizheng.partybuilding.service.inf.ITabSysDictService;
import com.yizheng.partybuilding.system.dto.DictTree;
import com.yizheng.partybuilding.system.entity.SysDict;
import com.yizheng.partybuilding.system.service.SysDictService;
import com.yizheng.partybuilding.system.vo.TreeUtil;
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
@Api(tags = "系统数据字典模块")
@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private SysDictService sysDictService;

	@Autowired
    private ITabSysDictService tabSysDictService;

	/**
	 * 通过ID查询字典信息
	 *
	 * @param id ID
	 * @return 字典信息
	 */
    @ApiOperation(value = "通过ID查询字典信息", notes = "通过ID查询字典信息", httpMethod = "GET")
	@GetMapping("/{id}")
	public SysDict dict(@ApiParam(value = "字典ID",required=true)@PathVariable Integer id) {
		return tabSysDictService.selectById(id);
	}

	/**
	 * 分页查询字典信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
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

    /**
     * 无分页查询字典信息
     *
     * @param params 条件
     * @return 字典list
     */
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

    /**
     * 获取根目录字典
     * @return
     */
    @ApiOperation(value = "获取根目录字典", notes = "获取根目录字典", httpMethod = "GET")
    @GetMapping("/rootDictList")
    public List<SysDict> rootDictList(){
        return tabSysDictService.rootDictList();
    }

	/**
	 * 通过字典类型查找字典
	 *
	 * @param type 类型
	 * @return 同类型字典
	 */
    @ApiOperation(value = "通过字典类型查找字典", notes = "通过字典类型查找字典", httpMethod = "GET")
	@GetMapping("/type/{type}")
	public List<SysDict> findDictByType(@ApiParam(value = "类型",required=true)@PathVariable String type) {
		return tabSysDictService.selectListByType(type.toUpperCase());
	}

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
    public DictDto findOne(@ApiParam(value = "dictType",required=true)@PathVariable String dictType, @ApiParam(value = "dictValue",required=true)@PathVariable String dictValue) {
        return tabSysDictService.findOne(dictType,dictValue);
    }*/

	/**
	 * 添加字典
	 *
	 * @param sysDict 字典信息
	 * @return success、false
	 */
    @ApiOperation(value = "添加字典", notes = "添加字典", httpMethod = "POST")
	@PostMapping
	public Boolean dict(@ApiParam(value = "字典信息")@Valid @RequestBody SysDict sysDict) {
		return tabSysDictService.insert(sysDict);
	}

	/**
	 * 删除字典，并且清除字典缓存
	 *
	 * @param id   ID
	 * @return R
	 */
	@DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除字典", notes = "删除字典")
	public Boolean deleteDict(@ApiParam(value = "字典id",required=true)@PathVariable Integer id) {
		return tabSysDictService.deleteById(id);
	}

	/**
	 * 修改字典
	 *
	 * @param sysDict 字典信息
	 * @return success/false
	 */
    @ApiOperation(value = "修改字典", notes = "修改字典", httpMethod = "PUT")
	@PutMapping
	public Boolean editDict(@ApiParam(value = "字典信息")@Valid @RequestBody SysDict sysDict) {
		return tabSysDictService.updateById(sysDict);
	}
}
