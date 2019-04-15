package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubject;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTest;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduSubjectMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbEduSubjectService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabPbEduSubjectServiceImpl implements ITabPbEduSubjectService {

    @Autowired
    private TabPbEduSubjectMapper mapper;

    //新增题目
    @Override
    public int add(TabPbEduSubject subject) {
        subject.setDelFlag("0");
        return mapper.insertSelective(subject);
    }

    //修改题目
    @Override
    public int update(TabPbEduSubject subject) {
        return mapper.updateByIdSelective(subject);
    }

    //根据id获取题目
    @Override
    public TabPbEduSubject getById(int id) {
        return mapper.selectById(id);
    }

    //获取题目list
    @Override
    public List<TabPbEduSubject> getSubjects(TabPbEduSubject subject) {
        return mapper.getSubjects(subject);
    }

    //随机获取多个题目
    @Override
    public List<TabPbEduSubject> getRandomSubjects(TabPbEduTest test, int type, int count) {
        PageHelper.startPage(1, count, "RAND()");
        TabPbEduSubject subject = new TabPbEduSubject();
        subject.setType(type);                      //试题类型 单选1  多选2  简答3
        subject.setCategory(test.getCategory());    //类别 语文数学之类的
        subject.setDifficulty(test.getDifficulty());//困难程度 单选1  多选2  简答3
        List<TabPbEduSubject> list = getSubjects(subject);
        return list;
    }
}
