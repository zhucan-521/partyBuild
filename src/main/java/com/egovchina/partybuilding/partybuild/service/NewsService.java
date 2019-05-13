package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.NewsDTO;
import com.egovchina.partybuilding.partybuild.entity.NewsQueryBean;
import com.egovchina.partybuilding.partybuild.vo.NewsVO;

import java.util.List;

/**
 * desc: 党建资讯-服务接口
 * Created by FanYanGen on 2019-05-11 17:33
 */
public interface NewsService {

    /**
     * desc: 新增党建资讯
     *
     * @param newsDTO 党建资讯dto
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 17:35
     */
    int insertNews(NewsDTO newsDTO);

    /**
     * desc: 更新党建资讯
     *
     * @param newsDTO 党建资讯dto
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 17:35
     */
    int updateNews(NewsDTO newsDTO);

    /**
     * desc: 发布党建资讯
     *
     * @param newsId 主键id
     * @param attachmentId 附件id
     * @return int
     * @auther FanYanGen
     * @date 2019-05-13 22:10
     */
    int publishNews(Long newsId, Long attachmentId);

    /**
     * desc: 取消发布党建资讯
     *
     * @param newsId 主键id
     * @return int
     * @auther FanYanGen
     * @date 2019-05-13 22:11
     */
    int obtainedNews(Long newsId);

    /**
     * desc: 删除党建资讯
     *
     * @param newsId 主键id
     * @return int
     * @auther FanYanGen
     * @date 2019-05-11 17:35
     */
    int deleteNews(Long newsId);

    /**
     * desc: 党建资讯详情
     *
     * @param newsId 主键id
     * @return NewsVO
     * @auther FanYanGen
     * @date 2019-05-11 17:36
     */
    NewsVO getNewsVODetails(Long newsId);

    /**
     * desc: 党建资讯列表
     *
     * @param newsQueryBean 查询条件
     * @return list
     * @auther FanYanGen
     * @date 2019-05-11 17:37
     */
    List<NewsVO> getNewsVOList(NewsQueryBean newsQueryBean);

}
