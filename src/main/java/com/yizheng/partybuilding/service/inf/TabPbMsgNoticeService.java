package com.yizheng.partybuilding.service.inf;

import com.baomidou.mybatisplus.service.IService;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbMsgNotice;
import com.yizheng.partybuilding.entity.TabPbMsgNoticeDept;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/28
 */
public interface TabPbMsgNoticeService extends IService<TabPbMsgNotice> {
    /**
     * 保存
     * @param notice
     * @return
     */
    int add(TabPbMsgNotice notice);

    /**
     * 修改
     * @param notice
     * @return
     */
    int edit(TabPbMsgNotice notice);

    /**
     * 根据主键id查询数据
     * @param id 主表主键id
     * @param noticeId 明细主键id
     * @return
     */
    TabPbMsgNotice findById(Long id,Long noticeId);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int tombstone(Long id);

    /**
     * 查询list数据
     * @param record
     * @param page
     * @return
     */
    List<TabPbMsgNotice> selectList(TabPbMsgNotice record,Page page);

    /**
     * 改变状态
     * @param record
     * @return
     */
    int editState(TabPbMsgNotice record);

    /**
     * 根据主键id签收
     * @param noticeDept
     * @return
     */
    int updateSigning(TabPbMsgNoticeDept noticeDept);

    /**
     * 接收通知list
     * @param noticeDept
     * @param page
     * @return
     */
    List<TabPbMsgNoticeDept> selectNoticeDeptList(TabPbMsgNoticeDept noticeDept,Page page);
}
