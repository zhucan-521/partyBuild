package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbDeptSecretaryMapper {

    int insertSelective(TabPbDeptSecretary record);

    TabPbDeptSecretary selectByPrimaryKey(TabPbDeptSecretary secretary);

    int updateByPrimaryKeySelective(TabPbDeptSecretary record);

    int tombstone(TabPbDeptSecretary record);

    /**
     * 根据书记id获取书记信息
     * @param SecretaryId
     * @return
     */
    SecretaryMemberVO selectSecretaryVOBySecretaryId(Long SecretaryId);

    /**
     * 根据用户Id获取书记主键
     *
     * @param userId
     * @return
     */
    Long getSecretaryIdByUserId(@Param("userId") Long userId,@Param("deptId")Long deptId);

    /**
     * 根据用户Id判断书记是否存在
     *
     * @param userId
     * @return
     */
    Boolean checkSecretaryIsexistByUserId(@Param("userId") Long userId, @Param("deptId")Long deptId);

    /**
     * 书记列表查询
     * @param secretaryMemberQueryBean
     * @return
     */
    List<SecretarysVO> selectSecretaryVOList(SecretaryMemberQueryBean secretaryMemberQueryBean);

}