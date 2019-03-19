package com.yizheng.partybuilding.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbMsgNotice;
import com.yizheng.partybuilding.entity.TabPbMsgNoticeDept;

import com.yizheng.partybuilding.repository.TabPbMsgNoticeDeptMapper;
import com.yizheng.partybuilding.repository.TabPbMsgNoticeMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabPbMsgNoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYingXiang on 2018/12/28
 */
@Service
@Transactional
public class TabPbMsgNoticeServiceImpl extends ServiceImpl<TabPbMsgNoticeMapper,TabPbMsgNotice> implements TabPbMsgNoticeService {

    @Autowired
    private TabPbMsgNoticeMapper noticeMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private TabPbMsgNoticeDeptMapper deptMapper;

    @Override
    @PaddingBaseField(recursive = true)
    public int add(TabPbMsgNotice notice) {
        if(CollectionUtils.isEmpty(notice.getNoticeDeptList())){
            throw new BusinessDataIncompleteException("请选择接收通知组织");
        }
        int count = noticeMapper.insertSelective(notice);
        /**
         * 保存附件
         */
        if(count>0){
            if(notice.getAttachmentList()!=null){
                iTabPbAttachmentService.intelligentOperation(notice.getAttachmentList(),notice.getId(),AttachmentType.NOTICE);
            }
            /**
             * 保存明细
             */
            addNoticeDeptList(notice.getNoticeDeptList(),notice.getId());
        }
        return count;
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int edit(TabPbMsgNotice notice) {
        if(CollectionUtils.isEmpty(notice.getNoticeDeptList())){
            throw new BusinessDataIncompleteException("请选择接收通知组织");
        }
        notice.setUpdateTime(new Date());
        notice.setUpdateUserid(UserContextHolder.getUserIdLong());
        notice.setUpdateUsername(UserContextHolder.getUserName());
        int count = noticeMapper.updateByPrimaryKeySelective(notice);
        if (count > 0) {
            //查询出该发出通知需要通知的所有组织信息
            List<TabPbMsgNoticeDept> noticeDeptList = deptMapper.selectDeptList(notice.getId());
            //筛选出数据库查询出的数据的组织id
            List<Long> dbDeptIdList = noticeDeptList.stream()
                    .map(TabPbMsgNoticeDept::getDeptId).collect(Collectors.toList());
            //筛选出前端查询的数据的组织id
            List<Long> interactionDeptList = notice.getNoticeDeptList().stream()
                    .map(TabPbMsgNoticeDept::getDeptId).collect(Collectors.toList());
            if(!CollectionUtil.isEqualCollection(dbDeptIdList,interactionDeptList)){
                //数据库和前端不相等的需要删除
                List<Long> noticeDeptRemoveList = noticeDeptList.stream()
                        .filter(noticeDept -> !notice.getNoticeDeptList()
                                .contains(noticeDept.getDeptId()))
                        .map(TabPbMsgNoticeDept::getId)
                        .collect(Collectors.toList());
                //前端和数据库不相等的需要新增
                List<TabPbMsgNoticeDept> addMsgNoticeDeptList = notice.getNoticeDeptList().stream()
                        .filter(noticeDept -> !dbDeptIdList
                                .contains(noticeDept.getDeptId()))
                        .collect(Collectors.toList());
                /**
                 * 删除明细
                 */
                deptMapper.deleteByPrimaryKey(noticeDeptRemoveList);
                /**
                 * 保存
                 */
                addNoticeDeptList(addMsgNoticeDeptList,notice.getId());
            }
            /**
             * 保存附件
             */
            if(notice.getAttachmentList()!=null){
                iTabPbAttachmentService.intelligentOperation(notice.getAttachmentList(),notice.getId(),AttachmentType.NOTICE);
            }
        }
        return count;
    }

    @Override
    public TabPbMsgNotice findById(Long id,Long noticeId) {
        /**
         * 查询明细数据
         */
        if(noticeId!=null){
            TabPbMsgNoticeDept noticeDept = deptMapper.findByDept(noticeId);
            TabPbMsgNotice notice = noticeMapper.selectByPrimaryKey(noticeDept.getNoticeId());
            List<TabPbMsgNoticeDept> list = new ArrayList();
            list.add(noticeDept);
            notice.setNoticeDeptList(list);
            /**
             * 查询附件list
             */
            notice.setAttachmentList(iTabPbAttachmentService.listByHostId(notice.getId(), AttachmentType.NOTICE));
            notice.setDocAttachmentList(notice.getAttachmentList().stream().filter(tabPbAttachment -> AttachmentType.DOC.equals(tabPbAttachment.getAttachmentFileType())).collect(Collectors.toList()));
            notice.setPhotoAttachmentList(notice.getAttachmentList().stream().filter(tabPbAttachment -> AttachmentType.PHOTO.equals(tabPbAttachment.getAttachmentFileType())).collect(Collectors.toList()));
            notice.setAttachmentList(null);
            return notice;
        }else{
            TabPbMsgNotice notice = noticeMapper.selectByPrimaryKey(id);
            notice.setNoticeDeptList(deptMapper.selectList(new TabPbMsgNoticeDept().setNoticeId(id)));
            /**
             * 查询附件list
             */
            notice.setAttachmentList(iTabPbAttachmentService.listByHostId(notice.getId(),AttachmentType.NOTICE));
            notice.setDocAttachmentList(notice.getAttachmentList().stream().filter(tabPbAttachment -> AttachmentType.DOC.equals(tabPbAttachment.getAttachmentFileType())).collect(Collectors.toList()));
            notice.setPhotoAttachmentList(notice.getAttachmentList().stream().filter(tabPbAttachment -> AttachmentType.PHOTO.equals(tabPbAttachment.getAttachmentFileType())).collect(Collectors.toList()));
            notice.setAttachmentList(null);
            return notice;
        }
    }

    @Override
    public int tombstone(Long id) {
        TabPbMsgNotice notice = new TabPbMsgNotice();
        notice.setId(id);
        notice.setUpdateTime(new Date());
        notice.setUpdateUserid(UserContextHolder.getUserIdLong());
        notice.setUpdateUsername(UserContextHolder.getUserName());
        return noticeMapper.tombstone(notice);
    }

    @Override
    public List<TabPbMsgNotice> selectList(TabPbMsgNotice record,Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return noticeMapper.selectNoticeList(record);
    }

    @Override
    public int editState(TabPbMsgNotice record) {
        /**
         * （是|取消）发布
         */
        record.setPublishTime(new Date());
        return noticeMapper.editState(record);
    }

    @Override
    public int updateSigning(TabPbMsgNoticeDept noticeDept) {
        noticeDept.setRecevieUsername(UserContextHolder.getUserName());
        noticeDept.setRecevieUserId(UserContextHolder.getUserId().longValue());
        noticeDept.setRecevieTime(new Date());
        return deptMapper.updateSigning(noticeDept);
    }

    @Override
    public List<TabPbMsgNoticeDept> selectNoticeDeptList(TabPbMsgNoticeDept noticeDept,Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return deptMapper.selectList(noticeDept);
    }


    /**
     * 保存组织
     * @param list
     */
    private int addNoticeDeptList(List<TabPbMsgNoticeDept> list, Long id){
        int retVal = 0;
        for(TabPbMsgNoticeDept noticeDept : list){
            noticeDept.setNoticeId(id);
            retVal += deptMapper.insertSelective(noticeDept);
        }
        return retVal;
    }

}
