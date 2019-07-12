package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbDeptSecretaryMapper {

    int insertSelective(TabPbDeptSecretary record);

    TabPbDeptSecretary selectByPrimaryKey(TabPbDeptSecretary secretary);

    int updateByPrimaryKeySelective(TabPbDeptSecretary secretary);

    int tombstone(TabPbDeptSecretary record);

    /**
     * 根据书记id获取书记信息
     *
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
    Long getSecretaryIdByUserId(@Param("userId") Long userId, @Param("deptId") Long deptId);

    /**
     * 根据用户Id判断书记是否存在
     *
     * @param userId
     * @return
     */
    Boolean checkSecretaryIsexistByUserId(@Param("userId") Long userId, @Param("deptId") Long deptId);

    /**
     * 书记列表查询
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    List<SecretaryMemberVO> selectSecretaryVOList(SecretaryMemberQueryBean secretaryMemberQueryBean);

    /**
     * 根据用户主键和班子主键查询班子成员主键
     *
     * @param userId
     * @param leadTeamId
     * @return
     */
    Long findMemberIdByLeadTeamIdAndUserId(@Param(value = "userId") Long userId, @Param(value = "leadTeamId") Long leadTeamId);

    /**
     * 根据用户id和领导班子id查询书记信息
     *
     * @param userId     用户id
     * @param leadTeamId 领导班子id
     * @return
     */
    TabPbDeptSecretary selectSecretaryByUserIdAndLeadTeamId(@Param("userId") Long userId, @Param("leadTeamId") Long leadTeamId);

    /**
     * 逻辑删除书记信息
     *
     * @param secretary 书记实体
     * @return
     */
    int logicDeleteTabPbSecretary(TabPbDeptSecretary secretary);

    /**
     * 根据书记id获取导出数据
     * @param secretaryId 书记id
     * @return
     */
    Map<String, Object> selectExportDataBySecretaryId(Long secretaryId);
}