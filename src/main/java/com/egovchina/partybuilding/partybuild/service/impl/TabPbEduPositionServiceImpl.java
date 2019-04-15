package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduIntelligentStudyDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduPositionDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPosition;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionCourse;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionProject;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduPositionCourseMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduPositionMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduPositionProjectMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbEduPositionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 教育基地service实现类
 *
 * @Author Zhang Fan
 **/
@Service("tabPbEduPositionService")
public class TabPbEduPositionServiceImpl implements TabPbEduPositionService {

    @Autowired
    private TabPbEduPositionMapper tabPbEduPositionMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;
    @Autowired
    private TabPbEduPositionCourseMapper tabPbEduPositionCourseMapper;
    @Autowired
    private TabPbEduPositionProjectMapper tabPbEduPositionProjectMapper;


    @Override
    public List<TabPbEduPosition> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduPosition> list = tabPbEduPositionMapper.selectWithConditions(conditions);
        return list;
    }

    @Override
    public int logicDeleteById(Long pId) {
        return tabPbEduPositionMapper.logicDeleteById(pId);
    }

    @Transactional
    @Override
    public int insertWithAbout(TabPbEduPositionDto tabPbEduPosition) {
        //新增基地信息
        int retVal = tabPbEduPositionMapper.insertSelective(tabPbEduPosition);
        if (retVal > 0) {
            Long id = tabPbEduPosition.getId();
            //特色项目
            List<TabPbEduPositionProject> eduPositionProjectList = tabPbEduPosition.getEduPositionProjects();
            if (CollectionUtil.isNotEmpty(eduPositionProjectList)) {
                eduPositionProjectList.forEach(project -> project.setPositionId(id));
                retVal += tabPbEduPositionProjectMapper.batchInsert(eduPositionProjectList);
            }
            //特色课程
            List<TabPbEduPositionCourse> eduPositionCourseList = tabPbEduPosition.getEduPositionCourses();
            if (CollectionUtil.isNotEmpty(eduPositionCourseList)) {
                eduPositionCourseList.forEach(course -> course.setPositionId(id));
                retVal += tabPbEduPositionCourseMapper.batchInsert(eduPositionCourseList);
            }
            //基地照片
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbEduPosition.getTabPbAttachments(), id, AttachmentType.EDU_POSITION);
        }
        return retVal;
    }

    @Transactional
    @Override
    public int updateWithAbout(TabPbEduPositionDto tabPbEduPosition) {
        //修改基地信息
        int retVal = tabPbEduPositionMapper.updateByPrimaryKeySelective(tabPbEduPosition);
        if (retVal > 0) {
            //维护特色项目
            retVal += modifyEduPositionProjects(tabPbEduPosition);
            //维护特色课程
            retVal += modifyEduPositionCourses(tabPbEduPosition);
            //维护基地照片
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbEduPosition.getTabPbAttachments(),
                    tabPbEduPosition.getId(), AttachmentType.EDU_POSITION);
        }
        return retVal;
    }

    /**
     * 维护教育基地特色课程
     *
     * @param tabPbEduPosition
     * @return
     */
    private int modifyEduPositionCourses(TabPbEduPositionDto tabPbEduPosition) {
        int retVal = 0;
        List<TabPbEduPositionCourse> positionCourseList = tabPbEduPosition.getEduPositionCourses();
        List<TabPbEduPositionCourse> dbPositionCourseList = tabPbEduPositionCourseMapper.selectAllByPositionId(tabPbEduPosition.getId());

        if (CollectionUtil.isNotEmpty(positionCourseList)) {
            List<Long> pendingIdList = positionCourseList.stream().map(TabPbEduPositionCourse::getId).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(dbPositionCourseList)) { //数据库有旧数据
                List<Long> pendingRemoveIdList = dbPositionCourseList.stream()
                        .filter(course -> !pendingIdList.contains(course.getId()))
                        .map(TabPbEduPositionCourse::getId)
                        .collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(pendingRemoveIdList)) {
                    retVal += tabPbEduPositionCourseMapper.batchLogicDeleteById(pendingRemoveIdList);
                }
            }

            positionCourseList.forEach(positionCourse -> {
                PaddingBaseFieldUtil.paddingBaseFiled(positionCourse);
                if (positionCourse.getId() != null && positionCourse.getId() > 0) { //修改
                    tabPbEduPositionCourseMapper.updateByPrimaryKeySelective(positionCourse);
                } else { //新增
                    positionCourse.setPositionId(tabPbEduPosition.getId());
                    tabPbEduPositionCourseMapper.insertSelective(positionCourse);
                }
            });
        } else if (CollectionUtil.isNotEmpty(dbPositionCourseList)) {
            //逻辑删除
            retVal += tabPbEduPositionCourseMapper.batchLogicDeleteByPositionId(tabPbEduPosition.getId());
        }
        return retVal;
    }

    /**
     * 维护教育基地特色项目
     *
     * @param tabPbEduPosition
     * @return
     */
    private int modifyEduPositionProjects(TabPbEduPositionDto tabPbEduPosition) {
        int retVal = 0;
        List<TabPbEduPositionProject> eduPositionProjectList = tabPbEduPosition.getEduPositionProjects();
        List<TabPbEduPositionProject> dbEduPositionProjectList = tabPbEduPositionProjectMapper.selectAllByPositionId(tabPbEduPosition.getId());

        if (CollectionUtil.isNotEmpty(eduPositionProjectList)) {
            List<Long> pendingProjectIdList = eduPositionProjectList.stream().map(TabPbEduPositionProject::getProjectId).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(dbEduPositionProjectList)) { //数据库有旧数据
                List<Long> pendingRemoveProjectId = dbEduPositionProjectList.stream()
                        .filter(project -> !pendingProjectIdList.contains(project.getProjectId()))
                        .map(TabPbEduPositionProject::getProjectId)
                        .collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(pendingRemoveProjectId)) {
                    retVal += tabPbEduPositionProjectMapper.batchLogicDeleteById(pendingRemoveProjectId);
                }
            }

            eduPositionProjectList.forEach(project -> {
                PaddingBaseFieldUtil.paddingBaseFiled(project);
                if (project.getProjectId() != null && project.getProjectId() > 0) { //修改
                    tabPbEduPositionProjectMapper.updateByPrimaryKeySelective(project);
                } else { //新增
                    project.setPositionId(tabPbEduPosition.getId());
                    tabPbEduPositionProjectMapper.insertSelective(project);
                }
            });
        } else if (CollectionUtil.isNotEmpty(dbEduPositionProjectList)) {
            //移除数据库里的所有
            retVal += tabPbEduPositionProjectMapper.batchLogicDeleteByPositionId(tabPbEduPosition.getId());
        }
        return retVal;
    }

    @Override
    public TabPbEduPositionDto selectWithAboutInfoById(Long id) {
        return tabPbEduPositionMapper.selectWithAboutInfoById(id);
    }

    @Override
    public List<TabPbEduIntelligentStudyDto> selectZLXXWithConditions(HashMap<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduIntelligentStudyDto> list = tabPbEduPositionMapper.selectZLXXWithConditions(conditions);
        return list;
    }

    @Override
    public TabPbEduIntelligentStudyDto selectZLLXWithAboutInfoById(Long id) {
        return tabPbEduPositionMapper.selectZLXXWithAboutInfoById(id);
    }
}
