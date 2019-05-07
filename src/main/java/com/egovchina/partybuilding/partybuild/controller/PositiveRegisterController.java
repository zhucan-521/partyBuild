package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterCancelDTO;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterDTO;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegisterQueryBean;
import com.egovchina.partybuilding.partybuild.service.PositiveRegisterService;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegisterVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhucan on 2018/11/29
 */
@Api(tags = "党员-在职党员社区报到接口-v1-张帆")
@RestController
@RequestMapping("/v1/registers")
public class PositiveRegisterController {

    @Autowired
    private PositiveRegisterService positiveRegisterService;

    @ApiOperation(value = "登记党员报道信息", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertPositiveRegister(@ApiParam("报道信息") @RequestBody @Validated PositiveRegisterDTO positiveRegisterDTO) {
        return ReturnUtil.buildReturn(positiveRegisterService.insertPositiveRegister(positiveRegisterDTO));
    }

    @ApiOperation(value = "报到信息详情", httpMethod = "GET")
    @ApiImplicitParam(name = "positiveRegistId", value = "id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{positiveRegistId}")
    public PositiveRegisterVO getPositiveRegisterVO(@PathVariable Long positiveRegistId) {
        return positiveRegisterService.getPositiveRegisterVOById(positiveRegistId);
    }

    @ApiOperation(value = "删除登记信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "positiveRegistId", value = "id", dataType = "long", paramType = "path", required = true)
    @DeleteMapping("/{positiveRegistId}")
    public ReturnEntity deletePositiveRegister(@PathVariable Long positiveRegistId) {
        return ReturnUtil.buildReturn(positiveRegisterService.logicDeletePositiveRegisterById(positiveRegistId));
    }

    @ApiOperation(value = "报到信息列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<PositiveRegisterVO> getPositiveRegisterMemberVOList(@ApiParam("报到查询信息") @Validated PositiveRegisterQueryBean positiveRegisterQueryBean,
                                                                        Page page) {
        return new PageInfo<>(positiveRegisterService.selectPositiveRegisterVOListByCondition(positiveRegisterQueryBean, page));
    }

    @ApiOperation(value = "撤销报到信息", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity cancelPositiveRegister(@ApiParam("撤销报到信息") @RequestBody @Validated PositiveRegisterCancelDTO positiveRegisterCancelDTO) {
        return ReturnUtil.buildReturn(positiveRegisterService.cancelPositiveRegister(positiveRegisterCancelDTO));
    }
}
