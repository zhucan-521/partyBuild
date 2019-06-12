package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.SecretaryInfoVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
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

    @ApiOperation(value = "根据书记secretaryId获取书记详情", httpMethod = "GET")
    @HasPermission("party_leadershipTeam_examine")
    @ApiImplicitParam(name = "secretaryId", value = "书记Id", paramType = "path", required = true)
    @GetMapping("/{secretaryId}")
    public SecretaryMemberVO getSecretaryVOBySecretaryId(@PathVariable Long secretaryId) {
        return secretaryService.selectSecretaryBySecretaryId(secretaryId);
    }

    @ApiOperation(value = "修改书记", notes = "（书记的家庭成员和职务传他们的id则是修改不传则是添加）", httpMethod = "PUT")
    @HasPermission("party_leadershipTeam_edit")
    @PutMapping
    public ReturnEntity updateSecretary(@ApiParam(value = "修改书记") @RequestBody @Validated SecretaryMemberDTO secretaryMemberDTO) {
        return ReturnUtil.buildReturn(secretaryService.updateSecretary(secretaryMemberDTO));
    }

    @ApiOperation(value = "书记列表", notes = "列表中会只显示中如果该书记有多个职务，那么只会显示他高职务", httpMethod = "GET")
    @HasPermission("party_leadershipTeam")
    @GetMapping
    public PageInfo<SecretarysVO> secretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        return new PageInfo<>(secretaryService.selectSecretaryList(secretaryMemberQueryBean, page));
    }

}
