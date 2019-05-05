package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNotice;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import com.egovchina.partybuilding.partybuild.repository.TabPbMsgNoticeDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbMsgNoticeMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeDeptQueryBean;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeQueryBean;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDTO;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDeptDTO;
import com.egovchina.partybuilding.partybuild.service.MsgNoticeService;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeDeptVO;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeVO;
import com.github.pagehelper.PageHelper;
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
public class MsgNoticeServiceImpl implements MsgNoticeService {

    @Autowired
    private TabPbMsgNoticeMapper noticeMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private TabPbMsgNoticeDeptMapper deptMapper;

    @Transactional
    @Override
    public int addMsgNotice(MsgNoticeDTO msgNoticeDTO) {
        if(CollectionUtils.isEmpty(msgNoticeDTO.getNoticeDeptList())){
            throw new BusinessDataIncompleteException("请选择接收通知组织");
        }
        TabPbMsgNotice tabPbMsgNotice=new TabPbMsgNotice();
        BeanUtil.copyPropertiesIgnoreNull(msgNoticeDTO,tabPbMsgNotice);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgNotice);
        int count = noticeMapper.insertSelective(tabPbMsgNotice);
        /**
         * 保存附件
         */
        if(count>0){
            iTabPbAttachmentService.intelligentOperation(msgNoticeDTO.getAttachments(), tabPbMsgNotice.getId(), AttachmentType.NOTICE);
            /**
             * 保存明细
             */
            addNoticeDeptList(msgNoticeDTO.getNoticeDeptList(),tabPbMsgNotice.getId());
        }
        return count;
    }

    @Override
    @Transactional
    public int editMsgNoticeById(MsgNoticeDTO notice) {
        if(CollectionUtils.isEmpty(notice.getNoticeDeptList())){
            throw new BusinessDataIncompleteException("请选择接收通知组织");
        }
        TabPbMsgNotice tabPbMsgNoticeUpdate=new TabPbMsgNotice();
        BeanUtil.copyPropertiesIgnoreNull(notice,tabPbMsgNoticeUpdate);
        tabPbMsgNoticeUpdate.setUpdateTime(new Date());
        tabPbMsgNoticeUpdate.setUpdateUserid(UserContextHolder.getUserIdLong());
        tabPbMsgNoticeUpdate.setUpdateUsername(UserContextHolder.getUserName());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbMsgNoticeUpdate);
        int count = noticeMapper.updateByPrimaryKeySelective(tabPbMsgNoticeUpdate);
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
            iTabPbAttachmentService.intelligentOperation(notice.getAttachments(), notice.getId(), AttachmentType.NOTICE);
        }
        return count;
    }

    @Override
    public MsgNoticeVO getMsgNotice(Long id, Long noticeId) {
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
            notice.setAttachments(iTabPbAttachmentService.listByHostId(notice.getId(), AttachmentType.NOTICE));
            MsgNoticeVO msgNoticeVO=new MsgNoticeVO();
            BeanUtil.copyPropertiesIgnoreNull(notice,msgNoticeVO);
            return msgNoticeVO;
        }else{
            TabPbMsgNotice notice = noticeMapper.selectByPrimaryKey(id);
            notice.setNoticeDeptList(deptMapper.selectList(new TabPbMsgNoticeDept().setNoticeId(id)));
            /**
             * 查询附件list
             */
            notice.setAttachments(iTabPbAttachmentService.listByHostId(notice.getId(), AttachmentType.NOTICE));
            MsgNoticeVO msgNoticeVO=new MsgNoticeVO();
            BeanUtil.copyPropertiesIgnoreNull(notice,msgNoticeVO);
            return msgNoticeVO;
        }
    }

    @Override
    public int deleteMsgNotice(Long id) {
        TabPbMsgNotice tabPbMsgNotice = new TabPbMsgNotice();
        tabPbMsgNotice.setId(id);
        tabPbMsgNotice.setDelFlag("1");
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgNotice);
        return noticeMapper.updateByPrimaryKeySelective(tabPbMsgNotice);
    }

    @Override
    public List<MsgNoticeVO> selectSendMsgNoticeList(MsgNoticeQueryBean record, Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return noticeMapper.selectNoticeVoList(record);
    }

    @Override
    public int editMsgNoticeState(MsgNoticeDTO record) {
        /**
         * （是|取消）发布
         */
        record.setPublishTime(new Date());
        TabPbMsgNotice msgNoticeVO=new TabPbMsgNotice();
        BeanUtil.copyPropertiesIgnoreNull(record,msgNoticeVO);
        return noticeMapper.editState(msgNoticeVO);
    }

    @Override
    public int signNotice(MsgNoticeDeptDTO noticeDept) {
        noticeDept.setRecevieUsername(UserContextHolder.getUserName());
        noticeDept.setRecevieUserId(UserContextHolder.getUserId().longValue());
        noticeDept.setRecevieTime(new Date());
        TabPbMsgNoticeDept tabPbMsgNoticeDept=new TabPbMsgNoticeDept();
        BeanUtil.copyPropertiesIgnoreNull(noticeDept,tabPbMsgNoticeDept);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgNoticeDept);
        return deptMapper.updateSigning(tabPbMsgNoticeDept);
    }

    @Override
    public List<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean noticeDept, Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return deptMapper.selectVoDeptList(noticeDept);
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
