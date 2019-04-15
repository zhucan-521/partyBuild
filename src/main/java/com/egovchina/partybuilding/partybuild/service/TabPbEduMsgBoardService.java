package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduMsgBoardDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduMsgBoard;

import java.util.List;

/**
 * @Author Jiang An
 **/
public interface TabPbEduMsgBoardService {


     int insert(TabPbEduMsgBoardDto record, Long courseId);
    //添加评论
    int insertSelective(TabPbEduMsgBoard record, Long courseId);
    //查询所有评论
    List<TabPbEduMsgBoard>selectWithConditions(Page page);

   //查询当个评论详情
    TabPbEduMsgBoard selectByPrimaryKey(Long msgId);
 //删除单个评论
    int deleteByPrimaryKey(Long msgId);
    //根据业务id和类型 查询评论的次数
    Long selectMsgBoardCount(Long busId,String msgType);

    /**
     * 查询登陆用户的留言信息
     * @param userId
     * @param page
     * @return
     */
    List<TabPbEduMsgBoard> getMsgBoardByUserId(Long userId, Page page);
}
