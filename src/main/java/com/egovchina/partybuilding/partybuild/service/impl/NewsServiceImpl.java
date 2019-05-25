package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.NewsDTO;
import com.egovchina.partybuilding.partybuild.entity.NewsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbNews;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyNewsReceive;
import com.egovchina.partybuilding.partybuild.repository.NewsMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyNewsReceiveMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.NewsService;
import com.egovchina.partybuilding.partybuild.vo.NewsDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.NewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField;

/**
 * desc: 新闻资讯-服务接口实现
 * Created by FanYanGen on 2019-05-11 17:38
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private TabPbPartyNewsReceiveMapper tabPbPartyNewsReceiveMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private ITabPbAttachmentService tabPbAttachmentService;

    @Override
    public int insertNews(NewsDTO newsDTO) {
        verifyAddOrUpdate(newsDTO, true);
        TabPbNews tabPbNews = generateTargetCopyPropertiesAndPaddingBaseField(newsDTO, TabPbNews.class, false);
        int result = newsMapper.insertSelective(tabPbNews);
        Long newsId = tabPbNews.getNewsId();
        if (result > 0) {
            List<TabPbPartyNewsReceive> tabPbPartyNewsReceives = generateTargetListCopyPropertiesAndPaddingBaseField(newsDTO.getNewsReceives(), TabPbPartyNewsReceive.class, receive -> receive.setNewsId(newsId), false);
            result += tabPbPartyNewsReceiveMapper.batchInsert(tabPbPartyNewsReceives);
            result += updatingFiles(newsDTO, newsId);
        }
        return result;
    }

    @Override
    public int updateNews(NewsDTO newsDTO) {
        verifyAddOrUpdate(newsDTO, false);
        int result = newsMapper.updateByPrimaryKeySelective(generateTargetCopyPropertiesAndPaddingBaseField(newsDTO, TabPbNews.class, true));
        Long newsId = newsDTO.getNewsId();
        if (result > 0) {
            tabPbPartyNewsReceiveMapper.batchDelete(newsId);
            List<TabPbPartyNewsReceive> tabPbPartyNewsReceives = generateTargetListCopyPropertiesAndPaddingBaseField(newsDTO.getNewsReceives(), TabPbPartyNewsReceive.class, receive -> receive.setNewsId(newsId), false);
            result += tabPbPartyNewsReceiveMapper.batchInsert(tabPbPartyNewsReceives);
            result += updatingFiles(newsDTO, newsDTO.getNewsId());
        }
        return result;
    }

    @Override
    public int publishNews(Long newsId) {
        return newsMapper.publishNews(newsId);
    }

    @Override
    public int obtainedNews(Long newsId) {
        return newsMapper.obtainedNews(newsId);
    }

    @Override
    public int topNews(Long newsId) {
        return newsMapper.updateTopStatus(newsId, 1L);
    }

    @Override
    public int unTopNews(Long newsId) {
        return newsMapper.updateTopStatus(newsId, 0L);
    }

    @Override
    public int deleteNews(Long newsId) {
        verification(newsId);
        TabPbNews tabPbNews = new TabPbNews().setNewsId(newsId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbNews);
        return newsMapper.logicDeleteNewsCascadeReceiveOrg(tabPbNews);
    }

    @Override
    public NewsDetailsVO getNewsVODetails(Long newsId) {
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

    private void verifyAddOrUpdate(NewsDTO newsDTO, boolean isInsert) {
        Long newsId = newsDTO.getNewsId();
        if (!isInsert) {
            if (newsId == null) {
                throw new BusinessDataCheckFailException("新闻资讯ID不能为空");
            } else {
                if (!newsMapper.checkIsExistByNewsId(newsId)) {
                    throw new BusinessDataCheckFailException("该新闻数据不存在");
                }
            }
        }
        if (!tabSysDeptMapper.checkIsExistByOrgId(newsDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
    }

    /**
     * desc: 维护附件上传
     *
     * @param newsId 主键id
     * @auther FanYanGen
     * @date 2019-05-14 10:14
     */
    private int updatingFiles(NewsDTO newsDTO, Long newsId) {
        return tabPbAttachmentService.intelligentOperation(newsDTO.getAttachments(), newsId, AttachmentType.NEWS);
    }

}
