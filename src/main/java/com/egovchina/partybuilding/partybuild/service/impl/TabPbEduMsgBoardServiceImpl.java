package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduMsgBoardDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduMsgBoard;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduMsgBoardMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbEduMsgBoardService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Jiang An
 **/
@Service
@Transactional
public class TabPbEduMsgBoardServiceImpl implements TabPbEduMsgBoardService {

    @Autowired
    private TabPbEduMsgBoardMapper msgBoardMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    //添加评论
    @Override
    public int insert(TabPbEduMsgBoardDto record, Long courseId) {
        record.setBusId(courseId);
        record.setCreateTime(new Date());
        record.setUserId(UserContextHolder.currentUser().getUserId().longValue());
        int retVal = msgBoardMapper.insert(record);

        if (retVal > 0) {
            //存入附件
            iTabPbAttachmentService.intelligentOperation(record.getAnnexs(), record.getMsgId(), AttachmentType.MsgBoard);
        }
        return retVal;
    }

    @Override
    public int insertSelective(TabPbEduMsgBoard record, Long courseId) {
        record.setBusId(courseId);
        record.setCreateTime(new Date());
        record.setUserId(UserContextHolder.currentUser().getUserId().longValue());
        return msgBoardMapper.insertSelective(record);
    }

    @Override
    public List<TabPbEduMsgBoard> selectWithConditions(Page page) {
        PageHelper.startPage(page);
        List<TabPbEduMsgBoard> list = msgBoardMapper.selectWithConditions(page);
        return list;
    }

    @Override
    public TabPbEduMsgBoard selectByPrimaryKey(Long msgId) {
        TabPbEduMsgBoard tabPbEduMsgBoard = msgBoardMapper.selectByPrimaryKey(msgId);
        return tabPbEduMsgBoard;
    }

    @Override
    public int deleteByPrimaryKey(Long msgId) {
        return msgBoardMapper.deleteByPrimaryKey(msgId);
    }

    //根据业务id和类型 查询评论的次数
    @Override
    public Long selectMsgBoardCount(Long busId, String msgType) {
        TabPbEduMsgBoard tabPbEduMsgBoard = new TabPbEduMsgBoard();
        tabPbEduMsgBoard.setBusId(busId);
        tabPbEduMsgBoard.setMsgType(msgType);
        return msgBoardMapper.selectMsgBoardCount(tabPbEduMsgBoard);
    }

    @Override
    public List<TabPbEduMsgBoard> getMsgBoardByUserId(Long userId, Page page) {
        PageHelper.startPage(page);
        return msgBoardMapper.selectMsgBoardByUserId(userId);
    }


}
