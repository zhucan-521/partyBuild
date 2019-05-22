package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesTree;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesVO;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
public interface PartyMassesService {

    /**
     * Description: 新增
     *
     * @param partyMassesDTO 党群DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int save(PartyMassesDTO partyMassesDTO);

    /**
     * Description: 根据id修改
     *
     * @param partyMassesDTO 党群DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int updateById(PartyMassesDTO partyMassesDTO);

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id 党群id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    PartyMassesVO selectById(Long id);


    /**
     * Description: 查询列表
     *
     * @param partyMassesQueryBean 党群查询实体
     * @param page                 分页
     * @return PartyMassesVO
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    List<PartyMassesVO> selectList(PartyMassesQueryBean partyMassesQueryBean, Page page);

    /**
     * 根据父组织id获取党群列表
     *
     * @param parentId 父组织id
     * @return PartyMassesTree
     */
    List<PartyMassesTree> getPartyMassesListByOrgParentId(Long parentId);

}

