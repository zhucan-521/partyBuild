package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbSubjectTag;
import com.egovchina.partybuilding.partybuild.service.TabPbSubjectTagService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "标签名", paramType = "query"),
            @ApiImplicitParam(name = "scope", value = "活动类型", paramType = "query")
    })
    public PageInfo<TabPbSubjectTag> findByList(@ApiIgnore TabPbSubjectTag tabPbSubjectTag, Page page) {
        List<Integer> array = new ArrayList<>();
        String[] split =tabPbSubjectTag.getScope().split(",");
        if (split.length>0){
            for (String s : split) {
                int i = Integer.parseInt(s);
                array.add(i);
            }
        }
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name",tabPbSubjectTag.getName());
        conditions.put("scope",array);
        List<TabPbSubjectTag>list=tabPbSubjectTagService.findByList(conditions,page);
        PageInfo<TabPbSubjectTag> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
