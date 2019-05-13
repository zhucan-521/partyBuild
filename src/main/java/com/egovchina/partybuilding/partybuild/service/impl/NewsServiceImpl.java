package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.NewsDTO;
import com.egovchina.partybuilding.partybuild.entity.NewsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbNews;
import com.egovchina.partybuilding.partybuild.repository.NewsMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.NewsService;
import com.egovchina.partybuilding.partybuild.vo.NewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * desc: 新闻资讯-服务接口实现
 * Created by FanYanGen on 2019-05-11 17:38
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private ITabPbAttachmentService tabPbAttachmentService;

    @Override
    public int insertNews(NewsDTO newsDTO) {
        verifyAdditionsAndUpdates(newsDTO);
        int result = newsMapper.insertSelective(generateTargetCopyPropertiesAndPaddingBaseField(newsDTO, TabPbNews.class, false));
        if (result > 0) {
            result += tabPbAttachmentService.intelligentOperation(newsDTO.getAttachments(), newsDTO.getNewsId(), AttachmentType.NEWS);
        }
        return result;
    }

    @Override
    public int updateNews(NewsDTO newsDTO) {
        verifyAdditionsAndUpdates(newsDTO);
        int result = newsMapper.updateByPrimaryKeySelective(generateTargetCopyPropertiesAndPaddingBaseField(newsDTO, TabPbNews.class, true));
        if (result > 0) {
            result += tabPbAttachmentService.intelligentOperation(newsDTO.getAttachments(), newsDTO.getNewsId(), AttachmentType.NEWS);
        }
        return result;
    }

    @Override
    public int updateNewsViews(Long newsId) {
        return newsMapper.updateNewsViews(newsId);
    }

    @Override
    public int deleteNews(Long newsId) {
        verification(newsId);
        TabPbNews tabPbNews = new TabPbNews().setDelFlag(1).setNewsId(newsId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbNews);
        return newsMapper.updateByPrimaryKeySelective(tabPbNews);
    }

    @Override
    public NewsVO getNewsVODetails(Long newsId) {
        verification(newsId);
        return newsMapper.getNewsVODetails(newsId);
    }

    @Override
    public List<NewsVO> getNewsVOList(NewsQueryBean newsQueryBean) {
        return newsMapper.getNewsVOList(newsQueryBean);
    }

    /**
     * desc: 数据校验
     *
     * @param newsId 新闻资讯id
     * @auther FanYanGen
     * @date 2019-05-11 17:53
     */
    private void verification(Long newsId) {
        if (!newsMapper.checkIsExistByNewsId(newsId)) {
            throw new BusinessDataCheckFailException("该新闻数据不存在");
        }
    }

    private void verifyAdditionsAndUpdates(NewsDTO newsDTO) {
        Long newsId = newsDTO.getNewsId();
        if (newsId == null) {
            throw new BusinessDataCheckFailException("新闻资讯ID不能为空");
        } else {
            if (!newsMapper.checkIsExistByNewsId(newsId)) {
                throw new BusinessDataCheckFailException("该新闻数据不存在");
            }
        }
        if (!tabSysDeptMapper.checkIsExistByOrgId(newsDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
    }

}
