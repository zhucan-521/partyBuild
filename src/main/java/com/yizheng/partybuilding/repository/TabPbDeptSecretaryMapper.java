package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.PunishmentRewardsDto;
import com.yizheng.partybuilding.entity.TabPbDeptSecretary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbDeptSecretaryMapper {
    int insertSelective(TabPbDeptSecretary record);

    TabPbDeptSecretary selectByPrimaryKey(Long secretaryId);

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

}