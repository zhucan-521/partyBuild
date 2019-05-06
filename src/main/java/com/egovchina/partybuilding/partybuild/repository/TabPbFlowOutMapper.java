package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.CommunityDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.vo.CommunityVO;
import com.egovchina.partybuilding.partybuild.entity.FlowOutMemberQueryBean;
import com.egovchina.partybuilding.partybuild.vo.FlowOutMemberVO;
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
     * @param flowOutMemberQueryBean
     * @return
     */
    List<FlowOutMemberVO> selectActiveFlowOutVo(FlowOutMemberQueryBean flowOutMemberQueryBean);

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
    List<CommunityVO> selectCommuntiyDto(CommunityDTO dto);

    /**
     * 根据社区id查询社区名称
     * @param id
     * @return
     */
    String communityNameById(Long id);

    /**
     * 单个查询根据id
     * @param id
     * @return
     */
     FlowOutMemberVO findFlowOutVoById(Long id);

    /**
     * 查询待报道流动党员是否流动
     * 返回状态 返回实体 其余状态 null
     * @param userId
     * @return
     */
    Boolean checkTabPbFlowOutExistsByUserId(Long userId);

}