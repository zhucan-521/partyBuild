package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduLike;

import java.util.List;

public interface TabPbEduLikeMapper {
    int deleteByPrimaryKey(Long likeId);

    int insert(TabPbEduLike record);

    int insertSelective(TabPbEduLike record);

    TabPbEduLike selectByPrimaryKey(Long likeId);

    int updateByPrimaryKeySelective(TabPbEduLike record);

    int updateByPrimaryKey(TabPbEduLike record);

    /**
     * 根据业务id和业务类型查询
     * @param record
     * @return
     */
    List<TabPbEduLike> selectTabPbEduLikeSelective(TabPbEduLike record);

    /**
     * 业务id获取点赞次数获取精品课程的点赞次数
     * @param busId
     * @return
     */
    Long selectLikeCountByBusId(Long busId);


    /**
     * 根据业务id和业务类型获取点赞次数
     * @param record
     * @return
     */
    Long selectLikeCountByBusIdAndLikeType(TabPbEduLike record);
}