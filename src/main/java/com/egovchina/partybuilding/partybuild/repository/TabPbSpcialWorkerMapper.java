package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbSpcialWorker;
import com.egovchina.partybuilding.partybuild.system.entity.SysUserRole;
import com.egovchina.partybuilding.partybuild.entity.SpecialWorkerQueryBean;
import com.egovchina.partybuilding.partybuild.vo.SpecialWorkerVO;

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
     * 删除专干人员根据用户id
     * @param UserId
     * @return
     */
    int deleteTabSpecialWorkerByUserId(Long UserId);

    /**
     * 详情查询
     * @param specialWorkerId
     * @return
     */
    SpecialWorkerVO selectSpecialWorkerVOById(Long specialWorkerId);

    /**
     * 查找专干是否离职 没有离职返回null 离职返回离职对象集合
     * @param userId
     * @return
     */
    boolean checkSpecialWhetherTOLeave(Long userId);

    List<SpecialWorkerVO> selectSpecialWorkerVOList(SpecialWorkerQueryBean specialWorkerQueryBean);
}