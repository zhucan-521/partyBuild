package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduSubjectBatchDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubject;
import com.egovchina.partybuilding.partybuild.service.TabPbEduSubjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@Api(tags = "党员教育-考试管理-题库模块")
@RestController
@RequestMapping(value = "eduSubject")
public class TabPbEduSubjectController {

    @Autowired
    private TabPbEduSubjectService subjectService;

    @Deprecated
    @ApiOperation(value = "获取题目信息", notes = "返回指定ID信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public TabPbEduSubject getById(@ApiParam(value = "id", required = true) @PathVariable int id) {
        return subjectService.getById(id);
    }

    @Deprecated
    @ApiOperation(value = "新增题目", notes = "新增题目信息", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity add(@RequestBody TabPbEduSubject subject) {
        int add = subjectService.add(subject);
        return ReturnUtil.buildReturn(add);
    }

    @Deprecated
    @ApiOperation(value = "修改题目", notes = "修改题目信息", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@RequestBody TabPbEduSubject subject) {
        int update = subjectService.update(subject);
        return ReturnUtil.buildReturn(update);
    }

    @Deprecated
    @ApiOperation(value = "删除题目", notes = "删除题目", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@ApiParam(value = "id", required = true) @PathVariable int id) {
        TabPbEduSubject subject = new TabPbEduSubject();
        subject.setId(id);
        subject.setDelFlag("1");
        int update = subjectService.update(subject);
        return ReturnUtil.buildReturn(update);
    }

    @Deprecated
    @ApiOperation(value = "获取题目列表", notes = "获取题目列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", required = false, value = "题目类别：数据字典59116", paramType = "query"),
            @ApiImplicitParam(name = "title", required = false, value = "题目内容", paramType = "query"),
            @ApiImplicitParam(name = "type", required = false, value = "试题类型：单选1  多选2  简答3", paramType = "query"),
            @ApiImplicitParam(name = "difficulty", required = false, value = "试题难度：1简单 2一般  3困难", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbEduSubject> getSubjectList(@ApiIgnore TabPbEduSubject subject, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduSubject> list = subjectService.getSubjects(subject);
        return new PageInfo<>(list);
    }

    @Deprecated
    @ApiOperation(value = "批量新增题目", notes = "批量新增题目信息", httpMethod = "POST")
    @PostMapping("/batchInsert")
    public ReturnEntity batchInsert(@RequestBody TabPbEduSubjectBatchDto subjectBatch) {
        //批量新增内容的格式： "1、“三会一课”不包含哪一个？ A、使用其他车辆的形式证 B、饮酒驾驶机动车 C、不知道 D、没有  答案：D  解析：请知悉“三会一课”课件内容  <---------->  2、“三会一课”不包含哪一个？ A、使用其他车辆的形式证 B、饮酒驾驶机动车 C、不知道 D、没有  答案：D  解析：请知悉“三会一课”课件内容";
        if (StringUtils.isEmpty(subjectBatch.getSubjects())) {
            throw new BusinessDataCheckFailException("请传入正确参数");
        }
        String[] strs = subjectBatch.getSubjects().split("<---------->");
        List<TabPbEduSubject> subjectList = checkFormatSubjects(strs, subjectBatch.getCategory(), subjectBatch.getType(), subjectBatch.getDifficulty());
        for (TabPbEduSubject subject : subjectList) {
            //循环新增
            subjectService.add(subject);
        }
        return ReturnUtil.success();
    }


    //定义题目内容的字母顺序，导入多个题目时，判断使用
    private static String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G"};

    /**
     * 先检测题目格式是否正确
     *
     * @param strs       题目字符串数组
     * @param category   题目类别：语文、数学、计算机
     * @param type       题目类型：单选1  多选2  简答3（现在默认只支持单选）
     * @param difficulty 题目难度：1简单 2一般  3困难
     */
    private static List<TabPbEduSubject> checkFormatSubjects(String[] strs, int category, int type, int difficulty) {
        int index = 1;
        List<TabPbEduSubject> eduSubjects = new ArrayList<>();
        for (String str : strs) {
            //获取单个题目的所有内容，并进行分割
            String[] singleSubjectStrs = str.split("\\n");//前端每行之间的内容用回车符隔开的
            //把单个题目的每一项添加到list中
            List<String> subjectList = new ArrayList<>();
            for (String singleSubject : singleSubjectStrs) {
                if (StringUtils.isNotEmpty(singleSubject) && singleSubject.trim().length() > 0) {
                    subjectList.add(singleSubject);
                }
            }
            //单个题目必须包含：标题、4个选项、答案、解析，7个内容
            int subjectLen = subjectList.size();
            if (subjectLen >= 7) {
                //单个题目的格式： 1、“三会一课”不包含哪一个？ A、使用其他车辆的形式证 B、饮酒驾驶机动车 C、不知道 D、没有  答案：D  解析：请知悉“三会一课”课件内容
                String title = subjectList.get(0);
                String answer = subjectList.get(subjectLen - 2);
                String answerAnalysis = subjectList.get(subjectLen - 1);
                boolean flag1 = title.startsWith("、", 1);
                if (!flag1) {
                    throw new BusinessDataCheckFailException("第 " + index + " 题，题目标题格式有误，请修正");
                }
                boolean flag2 = answer.contains("答案：");
                if (!flag2) {
                    throw new BusinessDataCheckFailException("第 " + index + " 题，答案格式有误，请修正");
                }
                boolean flag3 = answerAnalysis.contains("解析：");
                if (!flag3) {
                    throw new BusinessDataCheckFailException("第 " + index + " 题，解析格式有误，请修正");
                }

                String selects = "";//题目内容，题目标题 与 题目答案之前的内容是题目内容
                for (int i = 1; i < subjectLen - 2; i++) {
                    String select = subjectList.get(i);
                    int j = i - 1;
                    String letter = letters[j];
                    Boolean flag4 = select.contains(letter + "、");
                    if (!flag4) {
                        throw new BusinessDataCheckFailException("第 " + index + " 题，题目内容格式有误，请修正");
                    } else {
                        if (i > 1) {
                            selects += "|";
                        }
                        selects += subjectList.get(i);
                    }
                }
                //组装题目对象
                TabPbEduSubject subject = new TabPbEduSubject();
                subject.setType(type);
                subject.setDifficulty(difficulty);
                subject.setCategory(category);
                subject.setTitle(title.substring(2));
                subject.setSelects(selects);
                subject.setAnswer(answer.substring(3));
                subject.setAnswerAnalysis(answerAnalysis.substring(3));
                eduSubjects.add(subject);
                subject = null;
            } else {
                throw new BusinessDataCheckFailException("第 " + index + " 题格式有误，请修正");
            }
            singleSubjectStrs = null;
            subjectList = null;
            index++;
        }
        return eduSubjects;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String subjects = "1、“三会一课”不包含哪一个？ A、使用其他车辆的形式证 B、饮酒驾驶机动车 C、不知道 D、没有  答案：D  解析：请知悉“三会一课”课件内容  <---------->  2、“三会一课”不包含哪一个？ A、使用其他车辆的形式证 B、饮酒驾驶机动车 C、不知道 D、没有  答案：D  解析：请知悉“三会一课”课件内容";
            String[] strs = subjects.split("<---------->");
            List<TabPbEduSubject> subjectList = checkFormatSubjects(strs, 59137, 1, 2);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
