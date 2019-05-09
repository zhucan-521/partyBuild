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
     * @param msgNoticeDTO
     * @return
     */
    int addMsgNotice(MsgNoticeDTO msgNoticeDTO);

    /**
     * 修改
     *
     * @param msgNoticeDTO
     * @return
     */
    int editMsgNoticeById(MsgNoticeDTO msgNoticeDTO);

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
     * @param msgNoticeQueryBean
     * @param page
     * @return
     */
    List<MsgNoticeVO> selectSendMsgNoticeList(MsgNoticeQueryBean msgNoticeQueryBean, Page page);

    /**
     * 改变状态
     *
     * @param msgNoticeDTO
     * @return
     */
    int editMsgNoticeState(MsgNoticeDTO msgNoticeDTO);

    /**
     * 根据主键id签收
     *
     * @param msgNoticeDeptDTO
     * @return
     */
    int signNotice(MsgNoticeDeptDTO msgNoticeDeptDTO);

    /**
     * 接收通知list
     *
     * @param msgNoticeDeptQueryBean
     * @param page
     * @return
     */
    List<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean msgNoticeDeptQueryBean, Page page);

}
