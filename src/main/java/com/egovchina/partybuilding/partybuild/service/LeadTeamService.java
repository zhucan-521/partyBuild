package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamQueryBean;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamVO;

import java.util.List;

/**
 * @Author Jiang An
 **/
public interface LeadTeamService {

    /**
     * 根据条件查询班子列表
     *
     * @param queryBean 查询条件
     * @param page      分页对象
     * @return
     */
    List<LeadTeamVO> selectLeadTeamVOByCondition(LeadTeamQueryBean queryBean, Page page);

    /**
     * 根据id逻辑删除领导班子
     *
     * @param leadTeamId id
     * @return
     */
    int logicDeleteLeadTeamById(Long leadTeamId);

    /**
     * 新增领导班子
     *
     * @param leadTeamDTO 领导班子
     * @return
     */
    int insertLeadTeam(LeadTeamDTO leadTeamDTO);

    /**
     * 修改领导班子
     *
     * @param leadTeamDTO 领导班子
     * @return
     */
    int updateLeadTeam(LeadTeamDTO leadTeamDTO);

    /**
     * 根据id查询班子详情
     *
     * @param leadTeamId id
     * @return
     */
    LeadTeamVO selectLeadTeamVOById(Long leadTeamId);
}
