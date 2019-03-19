package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduSpecialDto;

import java.util.List;
import java.util.Map;

public interface TabPbEduSpecialService {

    int add(TabPbEduSpecialDto tabPbEduSpecialDto);

    List<TabPbEduSpecialDto> pagingQueryCourseSystem(Map<String, Object> conditions, Page page);

    int deleteCourseSystem(Long id);

    int modifyTheCurriculumSystem(TabPbEduSpecialDto tabPbEduSpecialDto);

    TabPbEduSpecialDto queryCourseSystemById(Long id);
}
