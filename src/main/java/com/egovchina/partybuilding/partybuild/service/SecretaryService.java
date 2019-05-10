package com.egovchina.partybuilding.partybuild.service;


import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryInfoVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author YangYingXiang on 2019/03/01
 */
public interface SecretaryService {

    /**
     * 根据userId获取书记基本信息
     *
     * @param userId
     * @return
     */
    SecretaryInfoVO getSecretaryInfoVOByUserId(Long userId);

    /**
     * 添加书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    int insertSecretary(SecretaryMemberDTO secretaryMemberDTO);

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
    List<SecretarysVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page);

    /**
     * 删除书记家庭成员和职务以及书记本人
     *
     * @return
     */
    int deleteSecretary(Long secretaryId);

}
