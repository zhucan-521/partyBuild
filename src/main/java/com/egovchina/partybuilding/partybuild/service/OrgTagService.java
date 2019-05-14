package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.OrgTagDTO;

/**
 * @description: 组织标签
 * @author: WuYunJie
 * @create: 2019-05-13 22:06
 **/
public interface OrgTagService {
    /**
     * 批量插入标记
     *
     * @param orgTagDTO 组织标签dto
     * @return int
     */
    int batchInsertOrgTagDTO(OrgTagDTO orgTagDTO);

    /**
     * 根据组织id逻辑删除组织标签
     *
     * @param orgId 组织id
     * @return int
     * @auther WuYunJie
     * @date 2019/5/13 22:51
     */
    int batchUpdateOrgTagByOrgId(Long orgId);

    /**
     * 添加组织标签
     *
     * @param orgTagDTO 组织标签DTO
     * @return int
     * @auther WuYunJie
     * @date 2019/5/13 23:05
     */
    int insertOrgTagDTO(OrgTagDTO orgTagDTO);
}
