package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgTagDTO;
import com.egovchina.partybuilding.partybuild.service.OrgTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 组织标记控制器
 * @author: WuYunJie
 * @create: 2019-05-13 21:57
 **/
@Api(tags = "党组织-组织标签-v1-吴云杰")
@RestController
@RequestMapping("/v1/orgtags")
public class OrgTagController {
    @Autowired
    private OrgTagService orgTagService;

    @ApiOperation(value = "添加组织标签", notes = "添加组织标签")
    @HasPermission("party_orgInfo_identification")
    @PostMapping
    public ReturnEntity batchInsertOrgTag(@ApiParam("多个组织标记") @RequestBody @Validated OrgTagDTO orgTagDTO) {
        return ReturnUtil.buildReturn(orgTagService.batchInsertOrgTagDTO(orgTagDTO));
    }
}
