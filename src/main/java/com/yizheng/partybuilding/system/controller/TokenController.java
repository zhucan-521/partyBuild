package com.yizheng.partybuilding.system.controller;

import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.partybuilding.system.service.SysTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统令牌接口
 *
 * @Author Zhang Fan
 **/
@Api(tags = "系统令牌模块")
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private SysTokenService sysTokenService;

    @ApiOperation(value = "令牌列表", httpMethod = "GET")
    @GetMapping
    public List<Map<String, Object>> list() {
        return sysTokenService.selectAllToken();
    }

    @ApiOperation(value = "吊销指定令牌", httpMethod = "DELETE")
    @DeleteMapping("{idCard}")
    public ReturnEntity delete(@PathVariable("idCard") @ApiParam(value = "身份证", required = true) String idCard) {
        return ReturnUtil.buildReturn(sysTokenService.deleteTokenByIdCard(idCard));
    }

}
