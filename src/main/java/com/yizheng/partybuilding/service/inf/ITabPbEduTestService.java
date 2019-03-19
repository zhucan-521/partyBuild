package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.dto.TabPbEduSubmitAnswerDto;
import com.yizheng.partybuilding.dto.TabPbEduUserTestInfoDto;
import com.yizheng.partybuilding.dto.TabPbEduUserTestScoreDto;
import com.yizheng.partybuilding.dto.TabPbSubjectForTestDto;
import com.yizheng.partybuilding.entity.TabPbEduSubject;
import com.yizheng.partybuilding.entity.TabPbEduTest;
import com.yizheng.partybuilding.entity.TabPbEduTestarrange;

import java.util.List;

public interface ITabPbEduTestService {

    /**
     * 新增试卷
     *
     * @param test
     * @return
     */
    int add(TabPbEduTest test);

    /**
     * 创建试卷-题目信息
     *
     * @param id    试卷id
     * @param list  题目集合
     * @param score 每个题目的分数
     */
    void createSubjectforTest(Integer id, List<TabPbEduSubject> list, float score);

    /**
     * 修改试卷
     *
     * @param test
     * @return
     */
    int update(TabPbEduTest test);

    /**
     * 根据id获取试卷
     *
     * @param id
     * @return
     */
    TabPbEduTest getById(int id);

    /**
     * 获取试卷list
     *
     * @param category   试卷类型
     * @param title      试卷名称
     * @param type       试卷类别（考试1 练习0）
     * @param difficulty 试卷难度 (1简单 2一般  3困难)
     * @return
     */
    List<TabPbEduTest> getTests(int category, String title, int type, int difficulty);

    /**
     * 开始考试-记录考生考试时间
     *
     * @param arrange
     * @return
     */
    TabPbEduTestarrange openTestArrange(TabPbEduTestarrange arrange);

    /**
     * 获取试卷题目信息
     *
     * @param testId
     * @return
     */
    List<TabPbSubjectForTestDto> getSubjectForTest(int testId);

    /**
     * 查询党员在改考试时间内是否提交了答卷
     *
     * @param arrangeId
     * @param userId
     * @return
     */
    boolean hasAnswerByArrangeId(int arrangeId, Integer userId);

    /**
     * 根据考试时间id获取试卷id
     *
     * @param arrangeId
     * @return
     */
    int getTestIdByArrangeId(int arrangeId);

    /**
     * 提交试卷答案
     *  @param submitAnswerDto
     * @param userId
     */
    int submitAnswer(TabPbEduSubmitAnswerDto submitAnswerDto, int userId);

    /**
     * 获取用户考试信息
     *
     * @param userId
     * @param category
     * @param title
     * @return
     */
    List<TabPbEduUserTestInfoDto> getUserTestInfo(int userId, int category, String title);


    /**
     * 获取用户最高-最低得分信息
     *
     * @param userId
     * @return
     */
    TabPbEduUserTestScoreDto getUserTestScore(int userId);
}
