package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbEduUserTestInfoDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduUserTestScoreDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbEduAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TabPbEduAnswer record);

    int insertSelective(TabPbEduAnswer record);

    TabPbEduAnswer selectById(Integer id);

    int updateByIdSelective(TabPbEduAnswer record);

    int updateByPrimaryKey(TabPbEduAnswer record);

    //查询党员在改考试时间内是否提交了答卷
    int selectNumsByArranageId(@Param("arrangeId") int arrangeId, @Param("userId") Integer userId);

    //获取用户考试信息
    List<TabPbEduUserTestInfoDto> getUserTestInfo(@Param("userId") int userId, @Param("category") int category, @Param("title") String title);

    //获取用户最高-最低得分信息
    TabPbEduUserTestScoreDto getUserTestScore(@Param("userId") int userId);
}