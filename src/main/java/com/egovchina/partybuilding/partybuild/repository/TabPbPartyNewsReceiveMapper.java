package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyNewsReceive;
import com.egovchina.partybuilding.partybuild.vo.NewsReceiveVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyNewsReceiveMapper {

    int deleteByPrimaryKey(Long receiveOrgId);

    int insert(TabPbPartyNewsReceive record);

    int insertSelective(TabPbPartyNewsReceive record);

    TabPbPartyNewsReceive selectByPrimaryKey(Long receiveOrgId);

    int updateByPrimaryKeySelective(TabPbPartyNewsReceive record);

    int updateByPrimaryKey(TabPbPartyNewsReceive record);

    /**
     * desc: 批量新增
     *
     * @param newsReceiveList list
     * @return int
     * @auther FanYanGen
     * @date 2019-05-24 11:36
     */
    int batchInsert(List<TabPbPartyNewsReceive> newsReceiveList);

    /**
     * desc: 批量删除
     *
     * @param newsId 党务公开主键ID
     * @return int
     * @auther FanYanGen
     * @date 2019-05-24 13:44
     */
    int batchDelete(Long newsId);

    /**
     * desc: 获得党务公开接收方信息
     *
     * @param newsId 党务公开主键
     * @return List<NewsReceiveVO>
     * @auther FanYanGen
     * @date 2019-05-24 14:28
     */
    List<NewsReceiveVO> getReceiveInfo(Long newsId);

}