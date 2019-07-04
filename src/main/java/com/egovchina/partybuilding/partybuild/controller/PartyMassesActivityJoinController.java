package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesActivityJoinDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityJoinQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesActivityJoinService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityJoinVO;
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
 * @date 2019/05/31 11:49:59
 */
@Api(tags = "党组织-党群活动-关联社区-v1-吴云杰")
@RestController
@RequestMapping("/party-masses-activity-joins")
public class PartyMassesActivityJoinController {

    @Autowired
    private PartyMassesActivityJoinService partyMassesActivityJoinService;

    @ApiOperation(value = "添加党群活动关联社区")
    @PostMapping
    public ReturnEntity save(@RequestBody PartyMassesActivityJoinDTO partyMassesActivityJoinDTO) {
        return ReturnUtil.buildReturn(partyMassesActivityJoinService.save(partyMassesActivityJoinDTO));
    }

    @ApiOperation(value = "根据id更新党群活动关联社区")
    @PutMapping
    public ReturnEntity update(@RequestBody PartyMassesActivityJoinDTO partyMassesActivityJoinDTO) {
        return ReturnUtil.buildReturn(partyMassesActivityJoinService.updateById(partyMassesActivityJoinDTO));
    }

    @ApiOperation(value = "根据id删除党群活动关联社区")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesActivityJoinService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询党群活动关联社区", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public PartyMassesActivityJoinVO findByIdPartyMassesActivityJoin(@PathVariable("id") Long id) {
        return partyMassesActivityJoinService.selectById(id);
    }

    @ApiOperation(value = "查询党群活动关联社区列表")
    @GetMapping
    public PageInfo<PartyMassesActivityJoinVO> selectList(PartyMassesActivityJoinQueryBean partyMassesActivityJoinQueryBean, Page page) {
        return new PageInfo<>(partyMassesActivityJoinService.selectList(partyMassesActivityJoinQueryBean, page));
    }
}
