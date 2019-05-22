package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.SignInDTO;
import com.egovchina.partybuilding.partybuild.service.PartyMassesActivityService;
import com.egovchina.partybuilding.partybuild.vo.SignInToListVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 党群活动签到控制器
 * @author: WuYunJie
 * @create: 2019-05-22 15:30
 **/
@Api(tags = "党组织-党群活动-签到-v1-吴云杰")
@RestController
@RequestMapping("/v1/party-masses-sign-ins")
public class PartyMassesActivityInSignController {

    @Autowired
    private PartyMassesActivityService partyMassesActivityService;

    @ApiOperation(value = "签到情况列表", notes = "签到情况详情", httpMethod = "GET")
    @GetMapping
    public PageInfo<SignInToListVO> selectBySignInToListVO(@RequestParam @ApiParam(value = "党群活动ID", required = true) Long partyMassesActivityId,
                                                           @RequestParam(required = false) @ApiParam(value = "签到状态 1 已签到； 2 未签到") Long signType,
                                                           @RequestParam(required = false) @ApiParam(value = "名字") String realName,
                                                           Page page) {
        List<SignInToListVO> list = partyMassesActivityService.selectSignInVOListByCondition(partyMassesActivityId, signType, page, realName);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "签到变更", notes = "已签到会取消签到记录，未签到的会将当前时间作为签到时间", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateSignIn(@RequestBody @Validated SignInDTO signInDTO) {
        return ReturnUtil.buildReturn(partyMassesActivityService.updateSignIn(signInDTO));
    }

    @ApiOperation(value = "签到接口加人/二维码签到", notes = "签到接口加人/二维码签到", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyMassesActivityId", value = "党群活动Id", paramType = "path", required = true),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", paramType = "path", required = true)
    })
    @PostMapping("/{idCardNo}/{partyMassesActivityId}")
    public ReturnEntity insertSignInWithPeople(@PathVariable Long partyMassesActivityId, @PathVariable String idCardNo) {
        return ReturnUtil.buildReturn(partyMassesActivityService.insertSignInWithPeople(partyMassesActivityId, idCardNo));
    }

}
