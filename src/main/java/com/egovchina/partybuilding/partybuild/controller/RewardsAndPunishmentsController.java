package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PunishmentDTO;
import com.egovchina.partybuilding.partybuild.dto.RewardsDTO;
import com.egovchina.partybuilding.partybuild.entity.RewardsAndPunishmentsQueryBean;
import com.egovchina.partybuilding.partybuild.service.RewardsAndPunishmentsService;
import com.egovchina.partybuilding.partybuild.vo.PunishmentVO;
import com.egovchina.partybuilding.partybuild.vo.RewardsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "党员-奖惩模块v1-刘唐港")
@RestController
@RequestMapping("/v1")
public class RewardsAndPunishmentsController {

    @Autowired
    RewardsAndPunishmentsService rewardsAndPunishmentsService;

    @ApiOperation(value = "添加处分", notes = "处分新增", httpMethod = "POST")
    @PostMapping("/punishments")
    public ReturnEntity addPunishment(@ApiParam(value = "处分信息") @Validated @RequestBody PunishmentDTO punishmentDTO) {
        return ReturnUtil.buildReturn(rewardsAndPunishmentsService.insertPunishment(punishmentDTO));
    }

    @ApiOperation(value = "删除处分", notes = "删除处分", httpMethod = "DELETE")
    @ApiImplicitParam(value = "处分ID", name = "id", dataType = "long", paramType = "path", required = true)
    @DeleteMapping("/punishments/{id}")
    public ReturnEntity deletePunishment(@PathVariable Long id) {
        return ReturnUtil.buildReturn(rewardsAndPunishmentsService.deletePunishmentById(id));
    }

    @ApiOperation(value = "修改处分", notes = "修改处分", httpMethod = "PUT")
    @PutMapping("/punishments")
    public ReturnEntity editPunishment(@ApiParam(value = "处分信息") @Validated @RequestBody PunishmentDTO punishmentDTO) {
        return ReturnUtil.buildReturn(rewardsAndPunishmentsService.updatePunishmentById(punishmentDTO));
    }

    @ApiOperation(value = "通过ID查询处分信息", notes = "通过ID查询处分信息", httpMethod = "GET")
    @ApiImplicitParam(value = "处分ID", name = "id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/punishments/{id}")
    public PunishmentVO punishment(@PathVariable Long id) {
        return rewardsAndPunishmentsService.selectPunishment(id);
    }

    @ApiOperation(value = "查询处分信息列表", notes = "查询处分信息", httpMethod = "GET")
    @GetMapping("/punishments/users")
    public PageInfo<PunishmentVO> selectPunishmentList(@ApiParam(value = "奖惩查询实体") RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(rewardsAndPunishmentsService.selectPunishmentVOListAndFilesById(rewardsAndPunishmentsQueryBean));
    }

    @ApiOperation(value = "添加奖励", notes = "奖励新增", httpMethod = "POST")
    @PostMapping("/rewards")
    public ReturnEntity addRewards(@ApiParam(value = "奖励信息") @Validated @RequestBody RewardsDTO rewardsDTO) {
        return ReturnUtil.buildReturn(rewardsAndPunishmentsService.insertRewards(rewardsDTO));
    }

    @ApiOperation(value = "删除奖励", notes = "删除奖励")
    @ApiImplicitParam(value = "奖励id", name = "id", dataType = "long", paramType = "path", required = true)
    @DeleteMapping("/rewards/{id}")
    public ReturnEntity deleteRewards(@PathVariable Long id) {
        return ReturnUtil.buildReturn(rewardsAndPunishmentsService.deleteRewardsById(id));
    }

    @ApiOperation(value = "修改奖励", notes = "修改奖励", httpMethod = "PUT")
    @PutMapping("/rewards")
    public ReturnEntity editRewards(@ApiParam(value = "奖励信息") @Validated @RequestBody RewardsDTO rewardsDTO) {
        return ReturnUtil.buildReturn(rewardsAndPunishmentsService.updateRewardsById(rewardsDTO));
    }

    @ApiOperation(value = "通过ID查询奖励信息", notes = "通过ID查询奖励信息", httpMethod = "GET")
    @ApiImplicitParam(value = "奖励id", name = "id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/rewards/{id}")
    public RewardsVO rewards(@PathVariable Long id) {
        return rewardsAndPunishmentsService.selectRewards(id);
    }

    @ApiOperation(value = "查询奖励信息列表", notes = "查询奖励信息", httpMethod = "GET")
    @GetMapping("/rewards/users")
    public PageInfo<RewardsVO> selectRewardsList(@ApiParam(value = "奖惩查询实体") RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(rewardsAndPunishmentsService.getRewardsListAndFiles(rewardsAndPunishmentsQueryBean));
    }
}
