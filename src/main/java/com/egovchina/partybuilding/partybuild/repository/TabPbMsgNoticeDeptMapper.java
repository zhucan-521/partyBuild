package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.MsgNoticeDeptQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeDeptVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TabPbMsgNoticeDeptMapper {


    /**
     * 根据主键删除接受党组织
     *
     * @param
     * @return
     */
    int batchDeleteMsgNoticeDept(List<Long> msgNoticeDeptList);

    /**
     * 保存
     *
     * @param record
     * @return
     */
    int insertSelective(TabPbMsgNoticeDept record);

    /**
     * 批量插入接受党组织
     *
     * @param tabPbMsgNoticeDept
     * @return
     */
    int batchInsertTabPbMsgNoticeDept(List<TabPbMsgNoticeDept> tabPbMsgNoticeDept);

    /**
     * 查询接受党组织集合
     *
     * @param record
     * @return
     */
    List<TabPbMsgNoticeDept> selectMsgNoticeDeptList(TabPbMsgNoticeDept record);

    /**
     * 收到文件通知列表
     *
     * @param record
     * @return
     */
    List<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean record);

    /**
     * 签收
     *
     * @param record
     * @return
     */
    int updateSigning(TabPbMsgNoticeDept record);

    /**
     * 根据主键id查询数据
     *
     * @param id
     * @return
     */
    TabPbMsgNoticeDept getTabPbMsgNoticeDeptById(Long id);

    /**
     * 根据noticeId获取接受党组织集合
     *
     * @param noticeId
     * @return
     */
    List<TabPbMsgNoticeDept> getMsgNoticeDeptListByNoticeId(Long noticeId);

    /**
     * 检查文件发布是否有接受党组织签收
     *
     * @param id 文件Id
     * @return
     */
    boolean checkWhetherPublish(Long id);

    /**
     * 根据接受党组织的noticeId获取发布党组织名称 ,和标题
     *
     * @param noticeId
     * @return
     */
    HashMap<String, Object> getPublishNameAndTitleByNoticId(Long noticeId);

}