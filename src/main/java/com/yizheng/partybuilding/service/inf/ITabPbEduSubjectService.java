package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.entity.TabPbEduSubject;
import com.yizheng.partybuilding.entity.TabPbEduTest;

import java.util.List;

public interface ITabPbEduSubjectService {

    /**
     * 新增题目
     *
     * @param subject
     * @return
     */
    int add(TabPbEduSubject subject);

    /**
     * 修改题目
     * @param subject
     * @return
     */
    int update(TabPbEduSubject subject);

    /**
     * 根据id获取题目
     *
     * @param id
     * @return
     */
    TabPbEduSubject getById(int id);

    /**
     * 获取题目list
     *
     * @param category
     * @param title
     * @param type
     * @return
     */
    List<TabPbEduSubject> getSubjects(TabPbEduSubject subject);

    /**
     * 随机获取多个题目
     * @param test 试卷对象
     * @param type 试题类型 单选1  多选2  简答3
     * @param count 获取题目数量
     * @return
     */
    List<TabPbEduSubject> getRandomSubjects(TabPbEduTest test, int type, int count);
}
