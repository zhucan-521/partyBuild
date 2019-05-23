package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesParticipantDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesParticipantQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesParticipantService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesParticipantVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:接口类
 *
 * @author WuYunJie
 * @date 2019/05/22 10:16:01
 */
@Api(tags = "党组织-党群活动参与人-v1-吴云杰")
@RestController
@RequestMapping("/tab-pb-party-masses-participants")
public class PartyMassesParticipantController {

    @Autowired
    private PartyMassesParticipantService partyMassesParticipantService;

    @ApiOperation(value = "添加")
    @PostMapping
    public ReturnEntity save(@RequestBody PartyMassesParticipantDTO partyMassesParticipantDTO) {
        return ReturnUtil.buildReturn(partyMassesParticipantService.save(partyMassesParticipantDTO));
    }

    @ApiOperation(value = "根据id更新")
    @PutMapping
    public ReturnEntity update(@RequestBody PartyMassesParticipantDTO partyMassesParticipantDTO) {
        return ReturnUtil.buildReturn(partyMassesParticipantService.updateById(partyMassesParticipantDTO));
    }

    @ApiOperation(value = "根据id删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesParticipantService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public PartyMassesParticipantVO findByIdPartyMassesParticipant(@PathVariable("id") Long id) {
        return partyMassesParticipantService.selectById(id);
    }

    @ApiOperation(value = "查询列表")
    @GetMapping
    public PageInfo<PartyMassesParticipantVO> selectList(PartyMassesParticipantQueryBean partyMassesParticipantQueryBean, Page page) {
        return new PageInfo<>(partyMassesParticipantService.selectList(partyMassesParticipantQueryBean, page));
    }
}
