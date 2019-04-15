package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduSpecialDto;

import java.util.List;
import java.util.Map;

public interface TabPbEduSpecialService {

    int add(TabPbEduSpecialDto tabPbEduSpecialDto);

    List<TabPbEduSpecialDto> pagingQueryCourseSystem(Map<String, Object> conditions, Page page);

    int deleteCourseSystem(Long id);

    int modifyTheCurriculumSystem(TabPbEduSpecialDto tabPbEduSpecialDto);

    TabPbEduSpecialDto queryCourseSystemById(Long id);
}
