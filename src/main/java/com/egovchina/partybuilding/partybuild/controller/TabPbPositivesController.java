package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
import com.egovchina.partybuilding.partybuild.service.TabPbPositivesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * 职务控制层
 * Date: 2018/11/30
 */
@Api(tags = "党员-职务模块")
@RestController
@RequestMapping("/positives")
public class TabPbPositivesController {

    @Autowired
    TabPbPositivesService tabPbPositivesService;

    /**
     * 添加职务
     *
     * @param positives 职务信息
     * @return success、false
     */
    @ApiOperation(value = "添加职务", notes = "党内/行政 职务新增", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addPositives(@ApiParam(value = "职务信息")@Valid @RequestBody TabPbPositives positives) {
        int insert = tabPbPositivesService.insert(positives);
        return ReturnUtil.buildReturn(insert);
    }

    /**
     * 删除职务
     *
     * @param id   ID
     * @return R
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除职务", notes = "删除职务")
    public ReturnEntity deletePositives(@ApiParam(value = "职务id",required=true)@PathVariable Integer id) {
        int retVal = tabPbPositivesService.deleteById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 修改职务
     *
     * @param positives 职务信息
     * @return success/false
     */
    @ApiOperation(value = "修改职务", notes = "修改职务", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity editPositives(@ApiParam(value = "职务信息")@Valid @RequestBody TabPbPositives positives) {
        int retVal = tabPbPositivesService.updateById(positives);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 通过ID查询职务信息
     *
     * @param id ID
     * @return 职务信息
     */
    @ApiOperation(value = "通过ID查询职务信息", notes = "通过ID查询职务信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public TabPbPositives positives(@ApiParam(value = "职务ID",required=true)@PathVariable Integer id) {
        return tabPbPositivesService.selectById(id);
    }

    @ApiOperation(value = "查询职务列表信息", notes = "查询职务列表信息", httpMethod = "GET")
    @GetMapping("/selectList")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "page", value = "页数", paramType = "query",required=true),
            //@ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query",required=true),
            //@ApiImplicitParam(name = "orderByField", value = "排序字段", paramType = "query"),
            //@ApiImplicitParam(name = "isAsc", value = "是否正序", paramType = "query"),
            @ApiImplicitParam(name = "positiveType", value = "职务类型", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",required=true)
    })
    public List<TabPbPositives> selectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return tabPbPositivesService.selectList(params);
    }

}
