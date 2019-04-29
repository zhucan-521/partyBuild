package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PositivesDTO;
import com.egovchina.partybuilding.partybuild.service.PositivesService;
import com.egovchina.partybuilding.partybuild.vo.PositivesVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
@Api(tags = "党员-职务模块v1-刘唐港")
@RestController
@RequestMapping("/v1/positives")
public class PositivesController {

    @Autowired
    PositivesService positivesService;

    @ApiOperation(value = "添加职务", notes = "党内/行政 职务新增", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addPositives(@ApiParam(value = "职务信息") @Validated @RequestBody PositivesDTO positivesDTO) {
        return ReturnUtil.buildReturn(positivesService.insertPositives(positivesDTO));
    }

    @ApiOperation(value = "删除职务", notes = "删除职务", httpMethod = "DELETE")
    @ApiImplicitParam(value = "职务id", name = "id", dataType = "long", paramType = "path", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deletePositives(@PathVariable Integer id) {
        return ReturnUtil.buildReturn(positivesService.deleteById(id));
    }

    @ApiOperation(value = "修改职务", notes = "修改职务", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity editPositives( @ApiParam(value = "职务信息") @Validated @RequestBody PositivesDTO positivesDTO) {
        return ReturnUtil.buildReturn(positivesService.updateById(positivesDTO));
    }

    @ApiOperation(value = "通过ID查询职务信息", notes = "通过ID查询职务信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "职务ID", paramType = "path",required = true)
    @GetMapping("/{id}")
    public PositivesVO positives(@PathVariable Integer id) {
        return positivesService.selectPositiveVOById(id);
    }

    @ApiOperation(value = "查询职务列表信息", notes = "查询职务列表信息", httpMethod = "GET")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positiveType", value = "职务类型", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",required=true)
    })
    public PageInfo<PositivesVO> selectList(Long userId ,String positiveType ,Page page){
        PageHelper.startPage(page);
        return new PageInfo<>(positivesService.selectPositives(userId,positiveType));
    }



}
