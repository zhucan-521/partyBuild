package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.NewsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbNews;
import com.egovchina.partybuilding.partybuild.vo.NewsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsMapper {

    int deleteByPrimaryKey(Long newsId);

    int insert(TabPbNews record);

    int insertSelective(TabPbNews record);

    TabPbNews selectByPrimaryKey(Long newsId);

    int updateByPrimaryKeySelective(TabPbNews record);

    int updateByPrimaryKeyWithBLOBs(TabPbNews record);

    int updateByPrimaryKey(TabPbNews record);

    /**
     * desc: 校验数据是否存在
     *
     * @param newsId 主键id
     * @return boolean
     * @auther FanYanGen
     * @date 2019-05-11 17:54
     */
    boolean checkIsExistByNewsId(Long newsId);

    /**
     * desc: 查询新闻资讯详情
     *
     * @param newsId 主键id
     * @return NewsVO
     * @auther FanYanGen
     * @date 2019-05-11 17:36
     */
    NewsVO getNewsVODetails(Long newsId);

    /**
     * desc: 根据查询条件查询新闻资讯列表
     *
     * @param newsQueryBean 查询条件
     * @return regions
     * @auther FanYanGen
     * @date 2019-05-11 18:32
     */
    List<NewsVO> getNewsVOList(NewsQueryBean newsQueryBean);

    /**
     * desc: 发布
     *
     * @param newsId       主键id
     * @param attachmentId 附件id
     * @param publishTime  发布时间
     * @return int
     * @auther FanYanGen
     * @date 2019-05-13 22:10
     */
    int publishNews(@Param("newsId") Long newsId, @Param("attachmentId") Long attachmentId, @Param("publishTime") String publishTime);

    /**
     * desc: 取消发布
     *
     * @param newsId 主键id
     * @return int
     * @auther FanYanGen
     * @date 2019-05-14 10:50
     */
    int obtainedNews(Long newsId);

    /**
     * desc: 修改置顶状态
     *
     * @param newsId    主键id
     * @param topStatus 置顶状态
     * @return int
     * @auther FanYanGen
     * @date 2019-05-14 11:29
     */
    int updateTopStatus(@Param("newsId") Long newsId, @Param("topStatus") Long topStatus);

}