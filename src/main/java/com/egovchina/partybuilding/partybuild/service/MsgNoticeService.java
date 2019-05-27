package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDTO;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeDeptQueryBean;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeQueryBean;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeDeptVO;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeVO;

import java.util.List;

/**
 * @author zhucan on 2018/12/28
 */
public interface MsgNoticeService {

    /**
     * 发布文件
     *
     * @param msgNoticeDTO
     * @return
     */
    int addMsgNotice(MsgNoticeDTO msgNoticeDTO);

    /**
     * 修改文件
     *
     * @param msgNoticeDTO
     * @return
     */
    int editMsgNoticeById(MsgNoticeDTO msgNoticeDTO);

    /**
     * 查询发出文件通知只需要传id，查询收到文件通知需要传id和noticeId
     *
     * @param id       主键id
     * @param noticeId 下达文件通知id
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
     * 发布文件通知列表
     *
     * @param msgNoticeQueryBean
     * @param page
     * @return
     */
    List<MsgNoticeVO> selectSendMsgNoticeList(MsgNoticeQueryBean msgNoticeQueryBean, Page page);

    /**
     * 改变文件发布状态
     *
     * @param id 文件主键
     * @param state 状态值 0.未发布、1.已发布
     * @return
     */
    int editMsgNoticeState(Long id, String state);

    /**
     * 根据主键id签收
     *
     * @param id
     * @return
     */
    int signNotice(Long id);

    /**
     * 接收文件通知列表
     *
     * @param msgNoticeDeptQueryBean
     * @param page
     * @return
     */
    List<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean msgNoticeDeptQueryBean, Page page);

}
