package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabSpecialWorkerResultDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbSpcialWorker;
import com.egovchina.partybuilding.partybuild.system.entity.SysUserRole;

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
     * 删除专干人员根据用户id
     * @param UserId
     * @return
     */
    int deleteTabSpecialWorkerByUserId(Long UserId);


    /**
     * 判断专干是否已经在XX组织里面
     * 存在这返回这个实体不存在返回null
     * @param tabSpecialWorkerResultDto
     * @return
     */
    List<TabSpecialWorkerResultDto> checkSpecialWorkerOndeptId(TabSpecialWorkerResultDto tabSpecialWorkerResultDto);

    /**
     * 根据mangerId和userId寻找专干主键
     * @param tabSpecialWorkerResultDto
     * @return
     */
    Long selectSpecialWorkerIdByIdcard(TabSpecialWorkerResultDto tabSpecialWorkerResultDto);


    /**
     * 详情查询
     * @param specialWorkerId
     * @return
     */
    TabSpecialWorkerResultDto selectOneById(Long specialWorkerId);


    /**
     * 查找专干是否离职 没有离职返回null 离职返回离职对象集合
     * @param userId
     * @return
     */
    List<TabSpecialWorkerResultDto> checkSpecialWhetherTOLeave(Long userId);
}