package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.*;
import com.yizheng.partybuilding.entity.TabPbEduSubject;
import com.yizheng.partybuilding.entity.TabPbEduTest;
import com.yizheng.partybuilding.entity.TabPbEduTestarrange;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.partybuilding.service.inf.ITabPbEduSubjectService;
import com.yizheng.partybuilding.service.inf.ITabPbEduTestService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对接步骤
 * 1.后台创建题库
 * 2.创建试卷，从题库中组装成试卷
 * 3.党员在前台参加考试，开始考试，记录考试时间，获取试卷题目
 * 4.考试结束后，提交试卷，对试卷进行阅卷打分
 * 5.考生获取考生成绩信息，与每道题得分情况
 */
@Api(tags = "党员教育-考试管理-试卷模块")
@RestController
@RequestMapping(value = "eduTest")
public class TabPbEduTestController {

    @Autowired
    private ITabPbEduTestService eduTestService;
    @Autowired
    private ITabPbEduSubjectService eduSubjectService;

    @ApiOperation(value = "获取试卷信息", notes = "返回指定ID信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public TabPbEduTest getById(@ApiParam(value = "id", required = true) @PathVariable int id) {
        return eduTestService.getById(id);
    }

    @ApiOperation(value = "根据试卷id，获取试卷与题目信息", notes = "返回指定ID信息", httpMethod = "GET")
    @GetMapping("/getTestAndSubjectById/{id}")
    public TabPbEduTestAndSubjectDto getTestAndSubject(@ApiParam(value = "id", required = true) @PathVariable int id) {
        if (id > 0) {
            TabPbEduTestAndSubjectDto testAndSubject = new TabPbEduTestAndSubjectDto();
            testAndSubject.setTest(eduTestService.getById(id));
            testAndSubject.setSubjects(eduTestService.getSubjectForTest(id));
            return testAndSubject;
        } else {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
    }

    //随机出卷，返回题目列表
    @ApiOperation(value = "创建试卷-随机出卷", notes = "创建试卷-随机出卷", httpMethod = "POST")
    @PostMapping("/insertRandom")
    public List<TabPbSubjectForTestDto> createRandomTest(@ApiParam("试卷实体，随机组卷不要传题目集合") @RequestBody TabPbEduTestAddDto testAddDto) {
        if (testAddDto == null) {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
        TabPbEduTest test = new TabPbEduTest();
        BeanUtils.copyProperties(testAddDto, test);

        //获取单选题
        List<TabPbEduSubject> danxuanList = eduSubjectService.getRandomSubjects(test, 1, testAddDto.getCount1());
        if (danxuanList.size() != testAddDto.getCount1()) {
            throw new BusinessDataIncompleteException("单选题数量不足，请添加试题后重试");
        }
        //添加试卷 - 得到试卷id
        eduTestService.add(test);
        //添加试卷中的题目
        eduTestService.createSubjectforTest(test.getId(), danxuanList, test.getTotalScore() * (float) testAddDto.getPercent1() / (100 * testAddDto.getCount1()));
        return eduTestService.getSubjectForTest(test.getId());
    }

    @ApiOperation(value = "创建试卷-选题出卷", notes = "创建试卷", httpMethod = "POST")
    @PostMapping("/insertTopic")
    public ReturnEntity createTopicTest(@ApiParam("试卷实体，选题组卷需传入题目集合，且单选题数量与题目集合数量一致") @RequestBody TabPbEduTestAddDto testAddDto) {
        if (testAddDto == null) {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
        TabPbEduTest test = new TabPbEduTest();
        BeanUtils.copyProperties(testAddDto, test);

        //获取单选题
        if (testAddDto.getDanxuanList() != null && testAddDto.getDanxuanList().size() != testAddDto.getCount1()) {
            throw new BusinessDataIncompleteException("单选题数量不足，请添加试题后重试");
        }
        //添加试卷 - 得到试卷id
        int add = eduTestService.add(test);
        if (add > 0) {
            //添加试卷中的题目
            eduTestService.createSubjectforTest(test.getId(), testAddDto.getDanxuanList(), test.getTotalScore() * (float) testAddDto.getPercent1() / (100 * testAddDto.getCount1()));
        }
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "修改试卷主体信息", notes = "修改试卷主体信息", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@RequestBody TabPbEduTest test) {
        if (test == null) {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
        int update = eduTestService.update(test);
        return ReturnUtil.buildReturn(update);
    }

    @ApiOperation(value = "试卷发布与取消发布", notes = "试卷发布与取消发布", httpMethod = "POST")
    @PostMapping("/publish")
    public ReturnEntity publish(@RequestBody TabPbEduTestPublishDto publishDto) {
        if (publishDto == null) {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
        TabPbEduTest test = new TabPbEduTest();
        test.setId(publishDto.getId());
        test.setPublishFlag(publishDto.getPublishFlag());
        int update = eduTestService.update(test);
        return ReturnUtil.buildReturn(update);
    }

    @ApiOperation(value = "删除试卷", notes = "删除试卷", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@ApiParam(value = "id", required = true) @PathVariable int id) {
        TabPbEduTest test = new TabPbEduTest();
        test.setId(id);
        test.setDelFlag("1");
        int update = eduTestService.update(test);
        return ReturnUtil.buildReturn(update);
    }

    @ApiOperation(value = "获取试卷列表", notes = "获取试卷列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", required = false, defaultValue = "0", value = "试卷类别 数据字典59116", paramType = "query"),
            @ApiImplicitParam(name = "title", required = false, value = "试卷名称", paramType = "query"),
            @ApiImplicitParam(name = "type", required = false, defaultValue = "0", value = "组卷方式，1选题组卷、2随机组卷", paramType = "query"),
            @ApiImplicitParam(name = "difficulty", required = false, defaultValue = "0", value = "试卷难度 (1简单 2一般  3困难)", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbEduTest> getTestList(Integer category, String title, Integer type, Integer difficulty, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduTest> list = eduTestService.getTests(category, title, type, difficulty);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "开始考试-记录考生考试时间", notes = "开始考试-记录考试考试时间", httpMethod = "POST")
    @PostMapping("/openTestArrange")
    public TabPbEduTestarrange openTestArrange(@RequestBody TabPbEduTestarrange arrange) {
        if (arrange == null) {
            throw new BusinessDataIncompleteException("请传入正确参数");
        }
        return eduTestService.openTestArrange(arrange);
    }

    @ApiOperation(value = "获取试卷题目", notes = "获取试卷题目", httpMethod = "POST")
    @PostMapping("/getSubjectForTest")
    public List<TabPbSubjectForTestDto> getSubjectForTest(int testId) {
        return eduTestService.getSubjectForTest(testId);
    }

    @ApiOperation(value = "提交试卷", notes = "提交试卷", httpMethod = "POST")
    @PostMapping("/submitAnswer")
    public ReturnEntity submitAnswer(@RequestBody TabPbEduSubmitAnswerDto submitAnswerDto) {
        if (submitAnswerDto == null) {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
        Integer userId = UserContextHolder.getUserId();
        if (!(userId != null && userId > 0)) {
            throw new BusinessDataCheckFailException("请登录后提交试卷");
        }
        if (eduTestService.hasAnswerByArrangeId(submitAnswerDto.getArrangeId(), userId)) {
            throw new BusinessDataCheckFailException("该考试已经提交答案，请勿重复提交");
        }
        int retVal = eduTestService.submitAnswer(submitAnswerDto, userId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "获取用户考试信息", notes = "获取用户考试信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", required = false, defaultValue = "0", value = "试卷类别 数据字典59116", paramType = "query"),
            @ApiImplicitParam(name = "title", required = false, value = "试卷名称", paramType = "query")
    })
    @PostMapping("/getUserTestInfo")
    public PageInfo<TabPbEduUserTestInfoDto> getUserTestInfo(int category, String title, Page page) {
        Integer userId = UserContextHolder.getUserId();
        if (!(userId != null && userId > 0)) {
            throw new BusinessDataCheckFailException("请登录后查询试卷信息");
        }
        PageHelper.startPage(page);
        List<TabPbEduUserTestInfoDto> list = eduTestService.getUserTestInfo(userId, category, title);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "获取用户最高-最低得分信息", notes = "获取用户最高-最低得分信息", httpMethod = "GET")
    @GetMapping("/getUserTestScore")
    public TabPbEduUserTestScoreDto getUserTestScore() {
        Integer userId = UserContextHolder.getUserId();
        if (!(userId != null && userId > 0)) {
            throw new BusinessDataCheckFailException("请登录后查询试卷信息");
        }
        return eduTestService.getUserTestScore(userId);
    }
}
