package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduStudyCenter;

import java.util.List;
import java.util.Map;

/**
 * 学习中心service接口
 *
 * @Author Zhang Fan
 **/
public interface TabPbEduStudyCenterService {
    /**
     * 根据条件查询数据
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbEduStudyCenter> selectWithConditions(Map<String, Object> conditions, Page page);

    /**
     * 新增
     * @param tabPbEduStudyCenter
     * @return
     */
    int insertWithAbout(TabPbEduStudyCenter tabPbEduStudyCenter);

    /**
     * 修改
     * @param tabPbEduStudyCenter
     * @return
     */
    int updateWithAbout(TabPbEduStudyCenter tabPbEduStudyCenter);

    /**
     * 根据id查询信息
     * @param studyId
     * @return
     */
    TabPbEduStudyCenter selectWithAboutInfoById(Long studyId);

    /**
     * 根据id逻辑删除
     * @param studyId
     * @return
     */
    int logicDeleteById(Long studyId);

    /**
     * 点赞
     * @param studyId
     */
    int like(Long studyId);

    /**
     * 查看
     * @param studyId
     * @return
     */
    int watch(Long studyId);

    TabPbEduStudyCenter details(Long studyId, Long lookFrom);

}
