package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.partybuild.service.PartyConsolationService;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "党员-慰问情况", tags = {"党员-慰问情况-v1-朱灿"})
@RestController
@RequestMapping("/v1/party-consolations")
public class PartyConsolationController {

    @Autowired
    private PartyConsolationService partyConsolationService;

    @ApiOperation(value = "根据用户Id获取他慰问情况", httpMethod = "GET")
    @ApiImplicitParam(value = "用户ID", name = "userId", paramType = "path", required = true)
    @GetMapping("/{userId}")
    public List<PartyConsolationVO> getPartyConsolationVO(@PathVariable Long userId) {
        return partyConsolationService.getPartyConsolationVO(userId);
    }

}
