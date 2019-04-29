package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.FamilyMemberDTO;
import com.egovchina.partybuilding.partybuild.service.FamilyService;
import com.egovchina.partybuilding.partybuild.vo.FamilyMemberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhucan on 2018/11/26
 */
@Api(value = "家庭成员", tags = {"党员-家庭成员-v1-朱灿"})
@RestController
@RequestMapping("/v1/families")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @ApiOperation(value = "根据输入的人员id查询他的家庭成员信息", notes = "人员ID为必填", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "人员ID", required = true,  paramType = "path")
    @GetMapping("/members/{userId}")
    public List<FamilyMemberVO> selectFamilyMemberList(@PathVariable Long userId) {
        return familyService.selectFamilyMemberList(userId);
    }

    @ApiOperation(value = "根据家庭成员主键Id查询信息", notes = "家庭成员主键ID为必填", httpMethod = "GET")
    @ApiImplicitParam(name = "relationId", value = "家庭成员主键ID", required = true,  paramType = "path")
    @GetMapping("/{relationId}/members")
    public FamilyMemberVO getFamiyMemberByPrimaryKey(@PathVariable Long relationId) {
        return familyService.selectFamilyMemberById(relationId);
    }

    @ApiOperation(value = "根据家庭成员主键ID删除信息", notes = "家庭成员主键ID为必填",httpMethod = "DELETE")
    @ApiImplicitParam(name = "relationId", value = "家庭成员主键ID", required = true, paramType = "path")
    @DeleteMapping("/{relationId}")
    public ReturnEntity deleteFamilyMemberByPrimaryKey(@PathVariable Long relationId) {
        return ReturnUtil.buildReturn(familyService.deleteFamilyMemberByPrimaryKey(relationId));
    }

    @ApiOperation(value = "保存单个家庭成员信息", notes = "添加家庭成员", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addFamilyMember(@RequestBody @ApiParam(value = "家庭成员对象")  FamilyMemberDTO familyMemberDTO) {
        return ReturnUtil.buildReturn(familyService.addFamilyDTO(familyMemberDTO));
    }

    @ApiOperation(value = "修改单个家庭成员信息", notes = "家庭成员主键ID为必填",httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateFamilyMemberById(@Validated @RequestBody @ApiParam(value = "家庭成员对象") FamilyMemberDTO familyMemberDTO) {
        return ReturnUtil.buildReturn(familyService.updateByPrimaryKeySelective(familyMemberDTO));
    }

}
