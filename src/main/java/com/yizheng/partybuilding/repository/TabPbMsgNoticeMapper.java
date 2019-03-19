package com.yizheng.partybuilding.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yizheng.partybuilding.entity.TabPbMsgNotice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMsgNoticeMapper extends BaseMapper<TabPbMsgNotice> {
    /**
     * 保存
     * @param record
     * @return
     */
    int insertSelective(TabPbMsgNotice record);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    TabPbMsgNotice selectByPrimaryKey(Long id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TabPbMsgNotice record);

    /**
     * 逻辑删除
     * @param noticeId
     * @return
     */
    int tombstone(TabPbMsgNotice noticeId);

    /**
     * 查询list数据
     * @param record
     * @return
     */
    List<TabPbMsgNotice> selectNoticeList(TabPbMsgNotice record);

    /**
     * 改变状态
     * @param record
     * @return
     */
    int editState(TabPbMsgNotice record);
}