package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: GuanYingxin
 * Date: 2019/4/19
 */
@Api(tags = "党员-党员标记v1-官颖鑫")
@RestController
@RequestMapping("/v1/usertags")
public class UserTagController {

    @Autowired
    private UserTagService userTagService;

    @ApiOperation(value = "根据用户id和对应的用户标签id插入", notes = "根据用户id和对应的用户标签id插入", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addUserTag(@ApiParam("党员标记") @Valid @RequestBody UserTagDTO userTagDTO) {
        return ReturnUtil.buildReturn(userTagService.insertUserTagDTO(userTagDTO));
    }

    @ApiOperation(value = "根据用户标签id删除对应记录《真删》", notes = "根据usertag-id删除对应记录", httpMethod = "DELETE")
    @DeleteMapping("/{usertagId}")
    public ReturnEntity deleteUserTag(@ApiParam(name = "usertagId", value = "用户标签id", required = true) @PathVariable Long usertagId) {
        return ReturnUtil.buildReturn(userTagService.delete(usertagId));
    }
}
