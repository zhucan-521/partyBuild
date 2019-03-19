package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabSpecialWorkerResultDto;
import com.yizheng.partybuilding.entity.TabPbSpcialWorker;
import com.yizheng.partybuilding.system.entity.SysUserRole;

import java.util.List;

public interface TabPbSpcialWorkerMapper {
    int deleteByPrimaryKey(Long specialWorkerId);

    int insert(TabPbSpcialWorker record);

    int insertSelective(TabPbSpcialWorker record);

    TabPbSpcialWorker selectByPrimaryKey(Long specialWorkerId);

    int updateByPrimaryKeySelective(TabPbSpcialWorker record);

    int updateByPrimaryKeyWithBLOBs(TabPbSpcialWorker record);

    int updateByPrimaryKey(TabPbSpcialWorker record);


    /**
     * 查询人员权限表中有没有专干人员
     * @param userId
     * @return
     */
    SysUserRole selectSysUserRoleByUserId(Long userId);

    /**
     * 条件查询查询专干党员信息
     * @param dto
     * @return
     */
    List<TabSpecialWorkerResultDto> selectAllTabSpecialWorkerDto(TabSpecialWorkerResultDto dto);

    /**
     * 修改专干党员信息
     * @param dto
     * @return
     */
    int updateTabSpecialWorkerResultDto(TabSpecialWorkerResultDto dto);

    /**
     * 删除专干人员根据用户id
     * @param UserId
     * @return
     */
    int deleteTabSpecialWorkerByUserId(Long UserId);
}