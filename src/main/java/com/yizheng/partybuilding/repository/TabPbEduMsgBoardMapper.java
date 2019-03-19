package com.yizheng.partybuilding.repository;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduMsgBoardDto;
import com.yizheng.partybuilding.entity.TabPbEduMsgBoard;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TabPbEduMsgBoardMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(TabPbEduMsgBoard record);

    //添加评论
    int insertSelective(TabPbEduMsgBoard record);

    //查询单个评论详情
    TabPbEduMsgBoardDto selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(TabPbEduMsgBoard record);

    int updateByPrimaryKey(TabPbEduMsgBoard record);


//分页查询所有
    List<TabPbEduMsgBoard>selectWithConditions(Page page);
    //根据业务id和类型 查询评论的次数
    Long selectMsgBoardCount(TabPbEduMsgBoard record);

    List<TabPbEduMsgBoard> selectMsgBoardByUserId(Long userId);
}