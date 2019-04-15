package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.IdWorker;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduLiveDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduLive;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduLiveMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbEduLiveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:
 * 直播实现类
 *
 * @outhor wuyunjie
 * @create 2018-12-10 11:38
 */
@Service("tabPbEduLiveService")
@Transactional
public class TabPbEduLiveServiceImpl implements TabPbEduLiveService {
    @Autowired
    private TabPbEduLiveMapper tabPbEduLiveMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    /**
     * 创建直播
     * @param tabPbEduLive
     * @return
     */
    @PaddingBaseField
    @Override
    public int insertSelective(TabPbEduLive tabPbEduLive) {
        if (StringUtil.isEmpty(tabPbEduLive.getName())){
            throw new BusinessDataIncompleteException("直播名称不能为空");
        }
        if(tabPbEduLive.getEndTime()!=null && tabPbEduLive.getStartTime()!=null){
            if(tabPbEduLive.getEndTime().getTime()-tabPbEduLive.getStartTime().getTime()<=0){
                throw new BusinessDataInvalidException("直播开始时间不能大于等于直播结束之间");
            }
        }else {
            throw new BusinessDataIncompleteException("直播开始时间和结束时间不能为空");
        }

        //生成录播id
        tabPbEduLive.setRecordId(IdWorker.getLongId());
        int insert = tabPbEduLiveMapper.insertSelective(tabPbEduLive);

        if (insert > 0 && tabPbEduLive.getListCourseware()!=null){
            List<TabPbAttachment> ListCoursware = tabPbEduLive.getListCourseware();
            Long liveId = tabPbEduLive.getLiveId();
            insert += iTabPbAttachmentService.intelligentOperation(ListCoursware
                    ,liveId,AttachmentType.LIVE);
        }
        return insert;
    }

    /**
     * 删除直播
     * @param liveId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long liveId) {
        TabPbEduLive tabPbEduLive = new TabPbEduLive();
        tabPbEduLive.setLiveId(liveId);
        tabPbEduLive.setDelFlag("1");
        return tabPbEduLiveMapper.updateByPrimaryKeySelective(tabPbEduLive);
    }

    /**
     * 编辑
     * @param tabPbEduLiveDto
     * @return
     */
    @PaddingBaseField
    @Override
    public int updateLive(TabPbEduLiveDto tabPbEduLiveDto) {
        if(tabPbEduLiveMapper.selectByPrimaryKey(tabPbEduLiveDto.getLiveId())==null){
            throw new BusinessDataNotFoundException("该直播不存在");
        }
        //新增录播资源
        if(tabPbEduLiveDto.getRecordList()!=null){
            List<TabPbAttachment> recordList = tabPbEduLiveDto.getRecordList();
            Long recordId = tabPbEduLiveDto.getRecordId();
            iTabPbAttachmentService.intelligentOperation(recordList
                    ,recordId,AttachmentType.LIVE);
        }
        //修改课件资源
        if(tabPbEduLiveDto.getListCourseware()!=null){
            List<TabPbAttachment> listCoursware = tabPbEduLiveDto.getListCourseware();
            Long liveId = tabPbEduLiveDto.getLiveId();
            iTabPbAttachmentService.intelligentOperation(listCoursware
                    ,liveId,AttachmentType.LIVE);
        }
        return tabPbEduLiveMapper.updateByPrimaryKeySelective(tabPbEduLiveDto);
    }

    @Override
    public List<TabPbEduLiveDto> selectLive(TabPbEduLive tabPbEduLive, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduLiveDto> dtoList = tabPbEduLiveMapper.selectLiveByTabPbEduLive(tabPbEduLive);
        return dtoList;
    }

    @Override
    public TabPbEduLiveDto getLiveByLiveId(Long liveId) {
        TabPbEduLive tabPbEduLive = new TabPbEduLive();
        tabPbEduLive.setLiveId(liveId);
        List<TabPbEduLiveDto> dtoList = tabPbEduLiveMapper.selectLiveByTabPbEduLive(tabPbEduLive);
        if(dtoList!=null){
            TabPbEduLiveDto tabPbEduLiveDto = dtoList.get(0);
            return tabPbEduLiveDto;
        }else {
            throw new BusinessDataNotFoundException("直播不存在");
        }

    }
}
