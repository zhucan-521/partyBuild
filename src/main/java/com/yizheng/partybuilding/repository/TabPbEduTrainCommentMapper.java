package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduTrainComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTrainCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduTrainComment record);

    int insertSelective(TabPbEduTrainComment record);

    TabPbEduTrainComment selectByPrimaryKey(Long id);

    List<TabPbEduTrainComment> selectList( TabPbEduTrainComment comment);

    int updateByPrimaryKeySelective(TabPbEduTrainComment record);

    int updateByPrimaryKey(TabPbEduTrainComment record);
}