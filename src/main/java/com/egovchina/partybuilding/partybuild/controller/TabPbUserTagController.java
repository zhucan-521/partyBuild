package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.service.TabPbUserTagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: huang
 * Date: 2019/1/14
 */
@Api(tags = "党员-党员标记-官颖鑫")
@RestController
@RequestMapping("/userTag")
public class TabPbUserTagController {

    @Autowired
    private TabPbUserTagService tabPbUserTagService;

    @ApiOperation(value = "根据用户ID和对应的用户标签字典ID", notes = "根据用户ID和对应的用户标签字典ID", httpMethod = "PUT")
    @PutMapping("/addUserTag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId",dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "tagType", value = "培训名称", paramType = "query")
    })
    public ReturnEntity addUserTag(Long userId, Long tagType){
        int retVal = tabPbUserTagService.addUserTag(userId, tagType);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "根据用户标签id删除对应记录《真删》", notes = "根据用户标签id删除对应记录", httpMethod = "DELETE")
    @DeleteMapping("/{usertagId}")
    public ReturnEntity delete(@ApiParam(value = "usertagId", required = true ) @PathVariable Long usertagId) {
        int delete = tabPbUserTagService.delete(usertagId);
        return ReturnUtil.buildReturn(delete);
    }

}
