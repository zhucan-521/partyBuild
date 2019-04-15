package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.CommunityDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbFlowOutDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface  TabPbFlowOutMapper {
    int deleteByPrimaryKey(Long flowOutId);

    int insert(TabPbFlowOut record);

    int insertSelective(TabPbFlowOut record);

    TabPbFlowOut selectByPrimaryKey(Long flowOutId);

    int updateByPrimaryKeySelective(TabPbFlowOut record);

    int updateByPrimaryKey(TabPbFlowOut record);

    /**
     * 条件查询流出党员列表
     * @param dto
     * @return
     */
    List<TabPbFlowOutDto> selectActiveTabPbFlowOutDto(TabPbFlowOutDto dto);

    /**
     * 根据deptId获取部门名称
     * @param deptId
     * @return
     */
    String selectDeptNameByDeptId(Long deptId);

    /**
     * 根据userId获取最新流出对象
     * @param userId
     * @return
     */
    TabPbFlowOut selectFlowOutByUserId(Long userId);


    /**
     * 获取档案所在单位名称
     * @param filesManageUnitId
     * @return
     */
    String selectUntilName(Long filesManageUnitId);

    /**
     * 根据社区名字模糊查找社区
     * @param dto
     * @return
     */
    List<CommunityDto> selectCommuntiyDto(CommunityDto dto);

    /**
     * 根据社区id查询社区名称
     * @param id
     * @return
     */
    String CommunityNameById(Long id);

    /**
     * 单个查询根据id
     * @param id
     * @return
     */
    TabPbFlowOutDto findTabPbFlowOutDtoById(Long id);


    /**
     * 查询待报道流动党员是否流动
     * 返回状态 返回实体 其余状态 null
     * @param userId
     * @return
     */
    TabPbFlowOut checkTabPbFlowOutExixtByUserId(Long userId);

}