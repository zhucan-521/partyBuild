package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduStudyCenter;

import com.yizheng.partybuilding.service.inf.TabPbEduStudyCenterService;
import com.yizheng.partybuilding.service.inf.TabPbEduStudyMarkService;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习中心控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党员教育-学习中心模块-张帆")
@RestController
@RequestMapping(value = "eduStudy")
public class TabPbEduStudyController {

    @Autowired
    private TabPbEduStudyCenterService tabPbEduStudyCenterService;
    @Autowired
    private TabPbEduStudyMarkService tabPbEduStudyMarkService;

    @ApiOperation(value = "学习文章列表", notes = "学习文章列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileType", value = "文件类型 dict WJLX", paramType = "query"),
            @ApiImplicitParam(name = "studyType", value = "学习类别 dict XXLB", paramType = "query"),
            @ApiImplicitParam(name = "duration", value = "时长 dict SC", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "文件名", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbEduStudyCenter> list(String fileType, String studyType, String duration,
                                              String title, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("fileType", fileType);
        conditions.put("studyType", studyType);
        conditions.put("duration", duration);
        conditions.put("title", title);
        conditions.put("delFlag", "0");
        List<TabPbEduStudyCenter> list = tabPbEduStudyCenterService.selectWithConditions(conditions, page);
        PageInfo<TabPbEduStudyCenter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "新增学习文章", notes = "新增学习文章", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "学习文章") @RequestBody @Validated TabPbEduStudyCenter tabPbEduStudyCenter) {
        dataVerification(tabPbEduStudyCenter);
        int retVal = tabPbEduStudyCenterService.insertWithAbout(tabPbEduStudyCenter);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改学习文章", notes = "修改学习文章", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "学习文章信息") @RequestBody @Validated TabPbEduStudyCenter tabPbEduStudyCenter) {
        dataVerification(tabPbEduStudyCenter);
        int retVal = tabPbEduStudyCenterService.updateWithAbout(tabPbEduStudyCenter);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查看学习文章", notes = "查看学习文章", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbEduStudyCenter details(@RequestParam("studyId") @ApiParam(value = "数据ID", required = true) Long studyId,
                                       @RequestParam(required = false) @ApiParam(value = "查看来源 1网站页面") Long lookFrom) {

        return tabPbEduStudyCenterService.details(studyId, lookFrom);
    }

    @ApiOperation(value = "删除学习文章", notes = "删除学习文章", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("studyId") @ApiParam(value = "数据ID", required = true) Long studyId) {
        //逻辑删除
        int retVal = tabPbEduStudyCenterService.logicDeleteById(studyId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "点赞", notes = "点赞", httpMethod = "GET")
    @GetMapping("/like")
    public ReturnEntity like(@RequestParam("studyId") @ApiParam(value = "学习文章ID", required = true) Long studyId) {
        int retVal = tabPbEduStudyCenterService.like(studyId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "学习中心-猜你喜欢", notes = "学习中心-猜你喜欢", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileType", value = "文件类型 dict STUDY_WJLX", paramType = "query"),
            @ApiImplicitParam(name = "studyType", value = "学习类别 dict XXLB", paramType = "query"),
            @ApiImplicitParam(name = "duration", value = "时长 dict SC", paramType = "query")
    })
    @GetMapping("/youMayAlsoLike")
    public List<TabPbEduStudyCenter> youMayAlsoLike(String fileType, String studyType, String duration) {
        Map<String, Object> conditions = new HashMap<>();
        if (StringUtils.isAllEmpty(fileType, studyType, duration)) {
            conditions.put("youMayAlsoLike", "youMayAlsoLike");
            conditions.put("userId", UserContextHolder.getUserId());
        } else {
            conditions.put("fileType", fileType);
            conditions.put("studyType", studyType);
            conditions.put("duration", duration);
        }
        conditions.put("delFlag", "0");
        Page page = new Page();
        page.setPageSize(3L);
        List<TabPbEduStudyCenter> list = tabPbEduStudyCenterService.selectWithConditions(conditions, page);
        return list;
    }

    @ApiOperation(value = "学习中心-重点推荐", notes = "学习中心-重点推荐", httpMethod = "GET")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "fileType", value = "文件类型 dict STUDY_WJLX", paramType = "query"),
//            @ApiImplicitParam(name = "studyType", value = "学习类别 dict XXLB", paramType = "query"),
//            @ApiImplicitParam(name = "duration", value = "时长 dict SC", paramType = "query")
//    })
    @GetMapping("/highlyRecommend")
    public PageInfo<TabPbEduStudyCenter> highlyRecommend(Page page) {
        Map<String, Object> conditions = new HashMap<>();
//        if (StringUtils.isAllEmpty(fileType, studyType, duration)) {
//            conditions.put("highlyRecommend", "highlyRecommend");
//            conditions.put("userId", UserContextHolder.getUserId());
//        } else {
//            conditions.put("fileType", fileType);
//            conditions.put("studyType", studyType);
//            conditions.put("duration", duration);
//        }
        conditions.put("isRecommend", "1");
        conditions.put("delFlag", "0");
        List<TabPbEduStudyCenter> list = tabPbEduStudyCenterService.selectWithConditions(conditions, page);
        PageInfo<TabPbEduStudyCenter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "学习中心-学习痕迹", notes = "学习中心-学习痕迹", httpMethod = "GET")
    @GetMapping("/studyMark")
    public PageInfo<TabPbEduStudyCenter> studyMark(@RequestParam @ApiParam(value = "指定日期 yyyy-MM-dd") String date,
                                                   @RequestParam @ApiParam(value = "文件类型 dict STUDY_WJLX") String type,
                                                   Page page) {
        Map<String, Object> conditions = new HashMap<>();
        if (StringUtils.isEmpty(date)) { //默认今天
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        conditions.put("date", date);
        conditions.put("userId", UserContextHolder.getUserId());
        conditions.put("type", type);
        conditions.put("delFlag", "0");
        List<TabPbEduStudyCenter> list = tabPbEduStudyMarkService.selectWithConditions(conditions, page);
        PageInfo<TabPbEduStudyCenter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 数据校验
     *
     * @param tabPbEduStudyCenter 文章信息
     */
    private void dataVerification(TabPbEduStudyCenter tabPbEduStudyCenter) {
        if (AttachmentType.DOC.equals(tabPbEduStudyCenter.getFileType())
                && StringUtils.isEmpty(tabPbEduStudyCenter.getContent())) {
            throw new BusinessDataIncompleteException("文章内容不能为空");
        }
    }

}
