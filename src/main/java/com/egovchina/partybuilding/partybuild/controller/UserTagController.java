package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation(value = "根据用户id和对应的用户标签id插入", notes = "根据用户id和对应的用户标签id插入(可同时为用户插入多个标记)", httpMethod = "POST")
    @PostMapping
    public ReturnEntity batchInsertUserTag(@ApiParam("多个党员标记") @RequestBody @Validated UserTagDTO userTagDTO) {
        return ReturnUtil.buildReturn(userTagService.batchInsertUserTagDTO(userTagDTO));
    }
}
