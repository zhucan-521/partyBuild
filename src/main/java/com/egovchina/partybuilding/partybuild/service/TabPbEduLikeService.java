package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduLike;
import com.github.pagehelper.PageInfo;

public interface TabPbEduLikeService {


    /**
     * 点赞次数新增（请给实体附带业务id和业务类型）
     * @param tabPbEduLike
     * @return
     */
    int addTabPbEduLikeService(TabPbEduLike tabPbEduLike);


    /**
     * 条件查询列表
     * @param tabPbEduLike
     * @param page
     * @return
     */
    PageInfo<TabPbEduLike> selectTabPbEduLikeSelective(TabPbEduLike tabPbEduLike,Page page);

    /**
     * 修改根据id
     * @param tabPbEduLike
     * @return
     */
    int updataTabPbEduLikeSelective(TabPbEduLike tabPbEduLike);

    /**
     * 根据删除
     * @param likeId
     * @return
     */
    int deleteTabPbEduLikeById(Long likeId);

    /**
     * 根据业务类型和业务id获取点赞次数
     * @param busId
     * @param likeType
     * @return
     */
    Long selectLikeCount(Long busId,String likeType);

}