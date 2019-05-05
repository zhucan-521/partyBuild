package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.SecretaryInfoVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhucan
 */
@Api(tags = "党员-书记-v1-朱灿")
@RestController
@RequestMapping("/v1/secretaries")
public class SecretaryController {

    @Autowired
    SecretaryService secretaryService;

    @ApiOperation(value = "根据党员userId来获取书记的信息（家庭成员、党内职务、以及基本信息（插入前用））", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "path", required = true)
    @GetMapping("/info/{userId}")
    public SecretaryInfoVO getSecretaryByUserId(@PathVariable Long userId) {
        return secretaryService.getSecretaryInfoVOByUserId(userId);
    }

    @ApiOperation(value = "根据书记secretaryId获取书记详情", httpMethod = "GET")
    @ApiImplicitParam(name = "secretaryId", value = "书记Id", paramType = "path", required = true)
    @GetMapping("/{secretaryId}")
    public SecretaryMemberVO getSecretaryVOBySecretaryId(@PathVariable Long secretaryId) {
        return secretaryService.selectSecretaryBySecretaryId(secretaryId);
    }

    @ApiOperation(value = "添加书记需要userId（手动添加书记不需要userId）", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertSecretaries(@ApiParam(value = "保存书记") @RequestBody SecretaryMemberDTO secretaryMemberDTO) {
        return secretaryService.insertSecretary(secretaryMemberDTO);
    }

    @ApiOperation(value = "修改书记", notes = "（书记的家庭成员和职务传他们的id则是修改不传则是添加）", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateSecretary(@ApiParam(value = "修改书记") @RequestBody @Validated SecretaryMemberDTO secretaryMemberDTO) {
        return secretaryService.updateSecretary(secretaryMemberDTO);
    }

    @ApiOperation(value = "书记列表", notes = "可指定条件查询", httpMethod = "GET")
    @GetMapping
    public PageInfo<SecretaryMemberVO> secretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        return secretaryService.selectSecretaryList(secretaryMemberQueryBean, page);
    }

    @ApiOperation(value = "根据传入的id删除书记", httpMethod = "DELETE")
    @ApiImplicitParam(value = "书记id", name = "secretaryId", paramType = "path")
    @DeleteMapping("/{secretaryId}")
    public ReturnEntity deleteSecretary(@PathVariable Long secretaryId) {
        return secretaryService.deleteSecretary(secretaryId);
    }

}
