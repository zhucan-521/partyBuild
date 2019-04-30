package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbDeptSecretaryMapper {
    int insertSelective(TabPbDeptSecretary record);

    TabPbDeptSecretary selectByPrimaryKey(TabPbDeptSecretary secretary);

    int updateByPrimaryKeySelective(TabPbDeptSecretary record);

    int tombstone(TabPbDeptSecretary record);

    /**
     * 根据用户id获取书记信息
     * @param userId
     * @return
     */
    SecretaryMemberVO selectSecretaryVOByUserId(Long userId);


    /**
     * 根据书记id获取书记信息
     * @param SecretaryId
     * @return
     */
    SecretaryMemberVO selectSecretaryVOBySecretaryId(Long SecretaryId);

    /**
     * 书记列表查询
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    List<SecretaryMemberVO> selectSecretaryVOList(SecretaryMemberQueryBean secretaryMemberQueryBean);

}