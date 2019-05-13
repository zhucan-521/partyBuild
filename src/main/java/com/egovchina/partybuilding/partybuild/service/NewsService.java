package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.NewsDTO;
import com.egovchina.partybuilding.partybuild.entity.NewsQueryBean;
import com.egovchina.partybuilding.partybuild.vo.NewsVO;

import java.util.List;

/**
 * desc: 新闻资讯-服务接口
 * Created by FanYanGen on 2019-05-11 17:33
 */
public interface NewsService {

    /**
     * desc: 新增新闻资讯
     *
     * @param newsDTO 新闻资讯dto
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 17:35
     */
    int insertNews(NewsDTO newsDTO);

    /**
     * desc: 更新新闻资讯
     *
     * @param newsDTO 新闻资讯dto
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 17:35
     */
    int updateNews(NewsDTO newsDTO);

    /**
     * desc: 更新新闻资讯浏览次数
     *
     * @param newsId 主键id
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 19:12
     */
    int updateNewsViews(Long newsId);

    /**
     * desc: 更新新闻资讯
     *
     * @param newsId 主键id
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 17:35
     */
    int deleteNews(Long newsId);

    /**
     * desc: 新闻资讯详情
     *
     * @param newsId 主键id
     * @return NewsVO
     * @auther FanYanGen
     * @date 2019-05-11 17:36
     */
    NewsVO getNewsVODetails(Long newsId);

    /**
     * desc: 询新闻资讯列表
     *
     * @param newsQueryBean 查询条件
     * @return list
     * @auther FanYanGen
     * @date 2019-05-11 17:37
     */
    List<NewsVO> getNewsVOList(NewsQueryBean newsQueryBean);

}
