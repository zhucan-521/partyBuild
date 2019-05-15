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
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "党员-慰问情况", tags = {"党员-慰问情况-v1-朱灿"})
@RestController
@RequestMapping("/v1/party_consolations")
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

    @ApiOperation(value = "修改慰问党员", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updatePartyConsolation(@RequestBody @ApiParam(value = "被慰问党员") @Validated({Update.class}) PartyConsolationDTO partyConsolationDTO) {
        return ReturnUtil.buildReturn(partyConsolationService.updatePartyConsolationDTO(partyConsolationDTO));
    }

    @ApiOperation(value = "删除慰问党员", httpMethod = "DELETE")
    @ApiImplicitParam(value = "主键", name = "id", paramType = "path", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deletePartyConsolation(@PathVariable Long id) {
        return ReturnUtil.buildReturn(partyConsolationService.deletePartyConsolationById(id));
    }

    @ApiOperation(value = "根据主键id获取他慰问情况", httpMethod = "GET")
    @ApiImplicitParam(value = "主键id", name = "id", paramType = "query", required = true)
    @GetMapping
    public PartyConsolationVO getPartyConsolationVOById(@ApiParam(value = "主键id") Long id) {
        return partyConsolationService.getPartyConsolationVOById(id);
    }

}
