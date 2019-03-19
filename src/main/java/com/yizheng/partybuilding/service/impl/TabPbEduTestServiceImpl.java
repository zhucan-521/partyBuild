package com.yizheng.partybuilding.service.impl;

import com.yizheng.partybuilding.dto.*;
import com.yizheng.partybuilding.entity.*;
import com.yizheng.partybuilding.repository.*;
import com.yizheng.partybuilding.service.inf.ITabPbEduTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TabPbEduTestServiceImpl implements ITabPbEduTestService {

    @Autowired
    private TabPbEduTestMapper mapper;
    @Autowired
    private TabPbEduSubjectfortestMapper subjectfortestMapper;
    @Autowired
    private TabPbEduTestarrangeMapper testarrangeMapper;
    @Autowired
    private TabPbEduAnswerMapper answerMapper;
    @Autowired
    private TabPbEduAnswerforsubjectMapper answerforsubjectMapper;

    /**
     * 新增试卷
     *
     * @param test
     * @return id
     */
    @Override
    public int add(TabPbEduTest test) {
        test.setDelFlag("0");
        test.setPublishFlag(false);//创建试卷时，默认不发布
        return mapper.insertSelective(test);
    }

    /**
     * 创建试卷-题目信息
     *
     * @param id    试卷id
     * @param list  题目集合
     * @param score 每个题目的分数
     */
    @Override
    public void createSubjectforTest(Integer id, List<TabPbEduSubject> list, float score) {
        TabPbEduSubjectfortest subjectfortest = null;
        for (TabPbEduSubject subject : list) {
            subjectfortest = new TabPbEduSubjectfortest();
            subjectfortest.setSubjectId(subject.getId());
            subjectfortest.setTestId(id);
            subjectfortest.setScore(score);
            subjectfortestMapper.insertSelective(subjectfortest);
        }
    }

    //修改试卷
    @Override
    public int update(TabPbEduTest test) {
        return mapper.updateByIdSelective(test);
    }

    //根据id获取试卷
    @Override
    public TabPbEduTest getById(int id) {
        return mapper.selectById(id);
    }

    //获取试卷list
    @Override
    public List<TabPbEduTest> getTests(int category, String title, int type, int difficulty) {
        return mapper.getTests(category, title, type, difficulty);
    }

    //开始考试-记录考生考试时间
    @Override
    public TabPbEduTestarrange openTestArrange(TabPbEduTestarrange arrange) {
        testarrangeMapper.insertSelective(arrange);
        return arrange;
    }

    //获取试卷题目信息
    @Override
    public List<TabPbSubjectForTestDto> getSubjectForTest(int testId) {
        return subjectfortestMapper.getSubjectForTest(testId);
    }

    //查询党员在改考试时间内是否提交了答卷
    @Override
    public boolean hasAnswerByArrangeId(int arrangeId, Integer userId) {
        return answerMapper.selectNumsByArranageId(arrangeId, userId) > 0;
    }

    //根据党员id与考试时间id获取试卷id
    @Override
    public int getTestIdByArrangeId(int arrangeId) {
        return testarrangeMapper.selectById(arrangeId).getTestId();
    }

    //获取用户考试信息
    @Override
    public List<TabPbEduUserTestInfoDto> getUserTestInfo(int userId, int category, String title) {
        return answerMapper.getUserTestInfo(userId, category, title);
    }

    //获取用户最高-最低得分信息
    @Override
    public TabPbEduUserTestScoreDto getUserTestScore(int userId) {
        return answerMapper.getUserTestScore(userId);
    }

    /**
     * 提交试卷答案
     * 1.保存试卷答案主体信息
     * 2.匹配用户题交的答案与题目的正确答案和得分
     * 3.保存每到题的得分
     * 4.修改试卷总分
     *  @param submitAnswerDto
     * @param userId
     */
    @Transactional
    @Override
    public int submitAnswer(TabPbEduSubmitAnswerDto submitAnswerDto, int userId) {
        //查询试卷id
        int testId = getTestIdByArrangeId(submitAnswerDto.getArrangeId());
        TabPbEduAnswer answer = new TabPbEduAnswer();
        answer.setTestId(testId);
        answer.setArrangeId(Long.valueOf(submitAnswerDto.getArrangeId()));
        answer.setUserId(Long.valueOf(userId));
        answer.setScore(0f);//默认0分，阅卷后再修改分
        answer.setState(0);//状态(是否出成绩 0为没有 1为已出)
        answer.setCreateTime(new Date());
        int retVal = answerMapper.insertSelective(answer);
        if (retVal > 0) {
            int answerId = answer.getId();//返回提交试卷id

            Map<Integer, String> answerMap = new HashMap<Integer, String>(); //答案map
            Map<Integer, TabPbEduSubjectAnswerDto> subjectAnswerMap = new HashMap<Integer, TabPbEduSubjectAnswerDto>();  //题目分数map

            //试卷题目答案，题目id与答案集合，如：1&A,2&B
            String[] contents = submitAnswerDto.getAnswerContent().split(",");
            for (String items : contents) {
                String[] item = items.split("&");
                int subjectId = Integer.parseInt(item[0]);
                String answerContent = item[1];
                answerMap.put(subjectId, answerContent);
            }

            //查询试卷题目的正确答案与题目分数，最后比较用户提交的答案与题目的正确答案，得到用户考试分数
            List<TabPbEduSubjectAnswerDto> list = subjectfortestMapper.getSubjectAnswerByTestId(testId);
            for (TabPbEduSubjectAnswerDto subject : list) {
                subjectAnswerMap.put(subject.getSubjectId(), subject);
            }

            float totalScore = 0; //试卷总分
            //循环用户提交的答案，对比题目正确答案，保存用户得分
            for (int subjectId : answerMap.keySet()) {
                TabPbEduSubjectAnswerDto subjectAnswer = subjectAnswerMap.get(subjectId);
                String answerContent = answerMap.get(subjectId);//用户提交的题目答案
                float score = 0;//每题得分
                //匹配正确答案
                if (answerContent.equals(subjectAnswer.getAnswer())) {
                    score = subjectAnswer.getScore();
                }
                totalScore += score;
                TabPbEduAnswerforsubject answerforsubject = new TabPbEduAnswerforsubject();
                answerforsubject.setAnswerId(answerId);
                answerforsubject.setSubjectId(subjectId);
                answerforsubject.setAnswerContent(answerContent);
                answerforsubject.setAnswerScore(score);
                answerforsubjectMapper.insertSelective(answerforsubject);
            }
            //最后修改试卷总得分
            TabPbEduAnswer eduAnswer = answerMapper.selectById(answerId);
            eduAnswer.setState(1);//状态(是否出成绩 0为没有 1为已出)
            eduAnswer.setScore(totalScore);
            answerMapper.updateByIdSelective(eduAnswer);
        }
        return retVal;
    }
}
