package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.partybuilding.entity.TabPbSubjectTag;
import com.yizheng.partybuilding.service.inf.TabPbSubjectTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jiang An
 **/
@Api(tags = "党组织活动-主题专题标签 蒋安")
@RestController
@RequestMapping("/theme")
public class TabPbSubjectTagController {

    @Autowired
    private TabPbSubjectTagService tabPbSubjectTagService;


    @ApiOperation(value = "删除标签", notes = "删除标签", httpMethod = "GET")
    @GetMapping("/delete/{tagId}")
    public ReturnEntity delete(@ApiParam(value = "标签Id", required = true) @PathVariable Long tagId) {
        int retVal = tabPbSubjectTagService.deleteByTagId(tagId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "添加标签", notes = "添加标签", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@RequestBody TabPbSubjectTag subjectTag) {
        int retVal = tabPbSubjectTagService.add(subjectTag);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询标签详情", notes = "根据TagId查询标签详情", httpMethod = "POST")
    @PostMapping("/findByTagId")
    public TabPbSubjectTag findByUserId(@RequestParam @ApiParam(value = "TagId", required = true) Long tagId) {
        return tabPbSubjectTagService.selectByTagId(tagId);
    }

    @ApiOperation(value = "修改标签", notes = "修改标签", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@RequestBody TabPbSubjectTag subjectTag) {
        int retVal = tabPbSubjectTagService.update(subjectTag);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "根据活动类型查询标签范围", notes = "根据活动类型查询标签范围", httpMethod = "POST")
    @PostMapping("/findByRange")
    public List<TabPbSubjectTag> findByRange(@RequestParam @ApiParam(value = "activitiesTypeId", required = true) String activitiesTypeId) {
        List<Integer> list = new ArrayList<>();
        String[] split = activitiesTypeId.split(",");
        for (String s : split) {
            int i = Integer.parseInt(s);
            list.add(i);
        }
        List<TabPbSubjectTag> TabPbSubjectTag = tabPbSubjectTagService.findByRange(list);
        return TabPbSubjectTag;
    }

    @ApiOperation(value = "分页查询标签列表", notes = "分页查询标签列表", httpMethod = "POST")
    @PostMapping("/list")
    public PageInfo<TabPbSubjectTag> findByList() {
        return null;
    }
}
