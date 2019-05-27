package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDTO;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeDeptQueryBean;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNotice;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import com.egovchina.partybuilding.partybuild.repository.TabPbMsgNoticeDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbMsgNoticeMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.MsgNoticeService;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeDeptVO;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author zhucan on 2018/12/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgNoticeServiceImpl implements MsgNoticeService {

    @Autowired
    private TabPbMsgNoticeMapper tabPbMsgNoticeMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private TabPbMsgNoticeDeptMapper tabPbMsgNoticeDeptMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 发布文件
     *
     * @param msgNoticeDTO
     * @return
     */
    @Override
    public int addMsgNotice(MsgNoticeDTO msgNoticeDTO) {
        TabPbMsgNotice tabPbMsgNotice = generateTargetCopyPropertiesAndPaddingBaseField(msgNoticeDTO, TabPbMsgNotice.class, false);
        int count = tabPbMsgNoticeMapper.insertSelective(tabPbMsgNotice);
        if (count > 0) {
            iTabPbAttachmentService.intelligentOperation(msgNoticeDTO.getAttachments(), tabPbMsgNotice.getId(), AttachmentType.NOTICE);
            //保存接受党组织
            List<TabPbMsgNoticeDept> MsgNoticeDeptlist = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(msgNoticeDTO.getNoticeDeptList(), TabPbMsgNoticeDept.class, msgNoticeDept -> msgNoticeDept.setNoticeId(tabPbMsgNotice.getId()), false);
            eventRelease(MsgNoticeDeptlist);
        }
        return count;
    }

    /**
     * 修改文件
     *
     * @param msgNoticeDTO
     * @return
     */
    @Override
    public int editMsgNoticeById(MsgNoticeDTO msgNoticeDTO) {
        if (msgNoticeDTO.getId() == null) {
            throw new BusinessDataIncompleteException("文件主键id不能为空");
        }
        TabPbMsgNotice tabPbMsgNotice = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(msgNoticeDTO, TabPbMsgNotice.class, false);
        int count = tabPbMsgNoticeMapper.updateByPrimaryKeySelective(tabPbMsgNotice);
        if (count > 0) {
            //接受党组织
            List<TabPbMsgNoticeDept> noticeDeptList = tabPbMsgNoticeDeptMapper.getMsgNoticeDeptListByNoticeId(msgNoticeDTO.getId());
            //筛选出数据库查询出的数据的组织id
            List<Long> dbDeptIdList = noticeDeptList.stream()
                    .map(TabPbMsgNoticeDept::getId).collect(Collectors.toList());
            tabPbMsgNoticeDeptMapper.batchDeleteMsgNoticeDept(dbDeptIdList);
            //筛选出前端查询的数据的组织id
            List<TabPbMsgNoticeDept> interactionDeptList = msgNoticeDTO.getNoticeDeptList();
            interactionDeptList.stream().forEach(item -> {
                item.setNoticeId(msgNoticeDTO.getId());
                tabPbMsgNoticeDeptMapper.insertSelective(item);
            });
            iTabPbAttachmentService.intelligentOperation(msgNoticeDTO.getAttachments(), msgNoticeDTO.getId(), AttachmentType.NOTICE);
        }
        return count;
    }

    /**
     * 查询发出文件通知只需要传id，查询收到文件通知需要传id和noticeId
     *
     * @param id       主键id
     * @param noticeId 下达文件通知id
     * @return
     */
    @Override
    public MsgNoticeVO getMsgNotice(Long id, Long noticeId) {
        if (noticeId != null) {
            Long temporarily = noticeId;
            noticeId = id;
            id = temporarily;
            TabPbMsgNoticeDept tabPbMsgNoticeDept = tabPbMsgNoticeDeptMapper.getTabPbMsgNoticeDeptById(noticeId);
            TabPbMsgNotice tabPbMsgNotice = tabPbMsgNoticeMapper.selectByPrimaryKey(tabPbMsgNoticeDept.getNoticeId());
            List<TabPbMsgNoticeDept> list = new ArrayList();
            list.add(tabPbMsgNoticeDept);
            tabPbMsgNotice.setNoticeDeptList(list);
            //查询附件list
            tabPbMsgNotice.setAttachments(iTabPbAttachmentService.listByHostId(tabPbMsgNotice.getId(), AttachmentType.NOTICE));
            MsgNoticeVO msgNoticeVO = new MsgNoticeVO();
            BeanUtil.copyPropertiesIgnoreNull(tabPbMsgNotice, msgNoticeVO);
            return msgNoticeVO;
        } else {
            TabPbMsgNotice tabPbMsgNotice = tabPbMsgNoticeMapper.selectByPrimaryKey(id);
            tabPbMsgNotice.setNoticeDeptList(tabPbMsgNoticeDeptMapper.selectMsgNoticeDeptList(new TabPbMsgNoticeDept().setNoticeId(id)));
            //查询附件list
            tabPbMsgNotice.setAttachments(iTabPbAttachmentService.listByHostId(tabPbMsgNotice.getId(), AttachmentType.NOTICE));
            MsgNoticeVO msgNoticeVO = new MsgNoticeVO();
            BeanUtil.copyPropertiesIgnoreNull(tabPbMsgNotice, msgNoticeVO);
            return msgNoticeVO;
        }
    }

    /**
     * 根据主键逻辑删除文件
     *
     * @param id
     * @return
     */
    @Override
    public int deleteMsgNotice(Long id) {
        TabPbMsgNotice tabPbMsgNotice = new TabPbMsgNotice();
        tabPbMsgNotice.setId(id);
        tabPbMsgNotice.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgNotice);
        return tabPbMsgNoticeMapper.updateByPrimaryKeySelective(tabPbMsgNotice);
    }

    /**
     * 发布文件通知列表
     *
     * @param msgNoticeQueryBean
     * @param page
     * @return
     */
    @Override
    public List<MsgNoticeVO> selectSendMsgNoticeList(MsgNoticeQueryBean msgNoticeQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbMsgNoticeMapper.selectSendMsgNoticeList(msgNoticeQueryBean);
    }

    /**
     * 改变文件发布状态
     *
     * @param id    文件主键
     * @param state 状态值 0.未发布、1.已发布
     * @return
     */
    @Override
    public int editMsgNoticeState(Long id, String state) {
        TabPbMsgNotice tabPbMsgNotice = new TabPbMsgNotice();
        tabPbMsgNotice.setPublishTime(new Date());
        tabPbMsgNotice.setId(id);
        tabPbMsgNotice.setState(state);
        if ("1".equals(state)) {
            tabPbMsgNotice.setPublisherName(UserContextHolder.getUserName());
            tabPbMsgNotice.setPublisherId(UserContextHolder.getUserId());
        } else {
            tabPbMsgNotice.setPublisherName("");
        }
        return tabPbMsgNoticeMapper.editState(tabPbMsgNotice);
    }

    /**
     * 根据主键签收
     *
     * @param id
     * @return
     */
    @Override
    public int signNotice(Long id) {
        TabPbMsgNoticeDept tabPbMsgNoticeDept = tabPbMsgNoticeDeptMapper.getTabPbMsgNoticeDeptById(id);
        tabPbMsgNoticeDept.setRecevieUsername(UserContextHolder.getUserName());
        tabPbMsgNoticeDept.setRecevieUserId(UserContextHolder.getUserId());
        tabPbMsgNoticeDept.setRecevieTime(new Date());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbMsgNoticeDept);
        return tabPbMsgNoticeDeptMapper.updateSigning(tabPbMsgNoticeDept);
    }

    /**
     * 收到文件通知列表
     *
     * @param msgNoticeDeptQueryBean
     * @param page
     * @return
     */
    @Override
    public List<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean msgNoticeDeptQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbMsgNoticeDeptMapper.selectReceiveMsgNotice(msgNoticeDeptQueryBean);
    }

    /**
     * 保存接受党组织
     *
     * @param tabPbMsgNoticeDeptList
     */
    @EventListener
    public void addNoticeDeptList(List<TabPbMsgNoticeDept> tabPbMsgNoticeDeptList) {
        tabPbMsgNoticeDeptMapper.batchInsertTabPbMsgNoticeDept(tabPbMsgNoticeDeptList);
    }

    /**
     * 事件发布
     *
     * @param tabPbMsgNoticeDeptList
     */
    @Async
    public void eventRelease(List<TabPbMsgNoticeDept> tabPbMsgNoticeDeptList) {
        publisher.publishEvent(tabPbMsgNoticeDeptList);
        this.addNoticeDeptList(tabPbMsgNoticeDeptList);
    }

}
