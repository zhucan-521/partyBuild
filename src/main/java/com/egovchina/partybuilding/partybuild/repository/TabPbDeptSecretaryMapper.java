package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.PunishmentRewardsDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
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
     * 返回奖惩信息
     * @return
     */
    List<PunishmentRewardsDto> punishmentRewards(@Param(value = "userId") Long userId);

    /**
     * 返回list
     * @param record
     * @return
     */
    List<TabPbDeptSecretary> selectList(TabPbDeptSecretary record);

    /**
     * 寻找组织里面最大的排序码
     * @param deptId
     * @return
     */
    Long maxOrderNum(@Param(value = "deptId") Long deptId);

    /**
     *
     * @param orderNum
     * @param userId
     * @return
     */
    int updateOrderNum(@Param(value = "orderNum")Long orderNum,@Param(value = "userId")Long userId);

    /**
     * 查询该组织下是否已存在信息
     * @param idCardNo
     * @param deptId
     * @return
     */
    Long selectByIdCardNo(@Param(value = "idCardNo")String idCardNo,@Param(value = "deptId")String deptId);

}