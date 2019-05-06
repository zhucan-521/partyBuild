package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeDeptQueryBean;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeQueryBean;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDTO;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDeptDTO;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeDeptVO;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeVO;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/28
 */
public interface MsgNoticeService {
    /**
     * 保存
     *
     * @param notice
     * @return
     */
    int addMsgNotice(MsgNoticeDTO notice);

    /**
     * 修改
     *
     * @param notice
     * @return
     */
    int editMsgNoticeById(MsgNoticeDTO notice);

    /**
     * 根据主键id查询数据
     *
     * @param id       主表主键id
     * @param noticeId 明细主键id
     * @return
     */
    MsgNoticeVO getMsgNotice(Long id, Long noticeId);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int deleteMsgNotice(Long id);

    /**
     * 查询list数据
     *
     * @param record
     * @param page
     * @return
     */
    List<MsgNoticeVO> selectSendMsgNoticeList(MsgNoticeQueryBean record, Page page);

    /**
     * 改变状态
     *
     * @param record
     * @return
     */
    int editMsgNoticeState(MsgNoticeDTO record);

    /**
     * 根据主键id签收
     *
     * @param noticeDept
     * @return
     */
    int signNotice(MsgNoticeDeptDTO noticeDept);

    /**
     * 接收通知list
     *
     * @param noticeDept
     * @param page
     * @return
     */
    List<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean noticeDept, Page page);
}
