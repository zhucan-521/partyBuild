package com.egovchina.partybuilding.partybuild.service;


import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;

import java.util.List;

/**
 * @author YangYingXiang on 2019/03/01
 */
public interface SecretaryService {

    /**
     * 新增书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    int addSecretary(SecretaryMemberDTO secretaryMemberDTO);

    /**
     * 删除书记
     *
     * @param secretaryId
     * @return
     */
    int removeSecretary(Long secretaryId);

    /**
     * 修改书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    int updateSecretary(SecretaryMemberDTO secretaryMemberDTO);

    /**
     * 根据书记id获取书记详情
     *
     * @param secretaryId
     * @return
     */
    SecretaryMemberVO selectSecretaryBySecretaryId(Long secretaryId);

    /**
     * 列表查询书记
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    List<SecretaryMemberVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page);

    /**
     * 根据用户id和领导班子id查询书记信息
     *
     * @param userId     用户id
     * @param leadTeamId 领导班子id
     * @return
     */
    TabPbDeptSecretary selectOldSecretaryInfoByUserIdAndLeadTeamId(Long userId, Long leadTeamId);

    /**
     * 添加书记实体
     *
     * @param tabPbDeptSecretary 书记实体
     * @return
     */
    int insertTabPbDeptSecretary(TabPbDeptSecretary tabPbDeptSecretary);

    /**
     * 将书记信息逻辑删除
     *
     * @param secretary 书记信息实体
     */
    int logicDeleteTabPbSecretary(TabPbDeptSecretary secretary);

    /**
     * 更新书记实体信息
     *
     * @param tabPbDeptSecretary 书记信息实体
     */
    int updateTabPbDeptSecretarySelective(TabPbDeptSecretary tabPbDeptSecretary);
}
