package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.SpecialWorkerDTO;
import com.egovchina.partybuilding.partybuild.entity.SpecialWorkerQueryBean;
import com.egovchina.partybuilding.partybuild.service.SpecialWorkerService;
import com.egovchina.partybuilding.partybuild.vo.SpecialWorkerVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 党员专干管理模块
 *
 * @Author wuyunjie
 **/
@Api(tags = "党组织-专干管理模块-v1 吴云杰")
@RestController
@RequestMapping(value = "/v1/dedicateds")
public class SpecialWorkerController {

    @Autowired
    SpecialWorkerService specialWorkerService;

    /**
     * 专干管理模块新增
     */
    @ApiOperation(value = "新增专干", notes = "新增专干")
    @PostMapping
    public ReturnEntity addSpecialWorker(@ApiParam("专干信息") @RequestBody @Validated SpecialWorkerDTO specialWorkerDto) {
        return ReturnUtil.buildReturn(specialWorkerService.insert(specialWorkerDto));
    }

    @ApiOperation(value = "删除", notes = "传入specialWorkerId删除,未删除sys_user_role表里面的值")
    @ApiImplicitParam(name = "specialWorkerId", value = "专干主键", required = true, paramType = "path")
    @DeleteMapping("/{specialWorkerId}")
    public ReturnEntity deleteSpecialWorker(@PathVariable Long specialWorkerId) {
        return ReturnUtil.buildReturn(specialWorkerService.deleteBySpecialWorkerId(specialWorkerId));
    }

    @ApiOperation(value = "条件分页查询", notes = "status为变更状态，1为在职，-1为离职")
    @GetMapping
    public PageInfo<SpecialWorkerVO> getSpecialWorkerList(Page page, SpecialWorkerQueryBean specialWorkerQueryBean) {
        Long rangeDeptId = specialWorkerQueryBean.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            specialWorkerQueryBean.setRangeDeptId(UserContextHolder.getOrgId());
        }
        return specialWorkerService.getSpecialWorkerList(page, specialWorkerQueryBean);
    }

    @ApiOperation(value = "修改专干", notes = "根据specialWorkerId修改")
    @PutMapping
    public ReturnEntity update(@ApiParam(value = "党员专干Dto") @RequestBody @Validated SpecialWorkerDTO specialWorkerDto) {
        return ReturnUtil.buildReturn(specialWorkerService.updateBySpecialWorkerId(specialWorkerDto));
    }

    @ApiOperation(value = "详情查询根据id", notes = "详情查询根据id")
    @ApiImplicitParam(value = "专干主键", name = "specialWorkerId", paramType = "path", required = true)
    @GetMapping("/{specialWorkerId}")
    public SpecialWorkerVO getSpecialWorkerById(@PathVariable Long specialWorkerId) {
        return specialWorkerService.selectSpecialWorkerById(specialWorkerId);
    }
}
