package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyConsolationDTO;
import com.egovchina.partybuilding.partybuild.service.PartyConsolationService;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "党员-慰问情况", tags = {"党员-慰问情况-v1-朱灿"})
@RestController
@RequestMapping("/v1/partyconsolation")
public class PartyConsolationController {

    @Autowired
    private PartyConsolationService partyConsolationService;

    @ApiOperation(value = "根据用户Id获取他慰问情况", httpMethod = "GET")
    @ApiImplicitParam(value = "用户ID", name = "userId", paramType = "path", required = true)
    @GetMapping("/{userId}")
    public List<PartyConsolationVO> getPartyConsolationVO(@PathVariable Long userId) {
        return partyConsolationService.getPartyConsolationVO(userId);
    }

    @ApiOperation(value = "添加慰问党员", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addPartyConsolationVO(@RequestBody @ApiParam(value = "被慰问党员") @Validated PartyConsolationDTO partyConsolationDTO) {
        return ReturnUtil.buildReturn(partyConsolationService.addPartyConsolationDTO(partyConsolationDTO));
    }

}
