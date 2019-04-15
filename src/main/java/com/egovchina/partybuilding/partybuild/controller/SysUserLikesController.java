package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.service.SysUserLikesSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "党员教育-党员兴趣爱好添加 朱灿")
@RestController
@RequestMapping("/sysUserLikes/")
public class SysUserLikesController {

    @Autowired
    SysUserLikesSerivce sysUserLikesSerivce;

    @GetMapping("/insert")
    @ApiOperation(value = "党员兴趣爱好添加", notes = "传爱好码值过来用，链接。比如（122，11）", httpMethod = "GET")
    public ReturnEntity insert(String likes) {
        Long userId = UserContextHolder.getUserId().longValue();
        int retVal = sysUserLikesSerivce.insertSysUserLikes(likes, userId);
        return ReturnUtil.buildReturn(retVal);
    }

    @GetMapping("/checkedSysUserLikes")
    @ApiOperation(value = "判断党员是否已经选择兴趣", notes = "选择了返回他的兴趣，未选择则为空", httpMethod = "GET")
    public String checkedSysUserLikes() {
        return sysUserLikesSerivce.checkedSysUserLikes();
    }
}
