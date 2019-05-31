package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesActivityJoinDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityJoinQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityJoinVO;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/31 11:49:59
 */
public interface PartyMassesActivityJoinService {

    /**
     * Description: 新增
     *
     * @param partyMassesActivityJoinDTO 党群活动关联DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    int save(PartyMassesActivityJoinDTO partyMassesActivityJoinDTO);

    /**
     * Description: 根据id修改
     *
     * @param partyMassesActivityJoinDTO 党群活动关联DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    int updateById(PartyMassesActivityJoinDTO partyMassesActivityJoinDTO);

    /**
     * Description: 根据id删除
     *
     * @param id 主键ID
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id 主键ID
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    PartyMassesActivityJoinVO selectById(Long id);


    /**
     * Description: 根据条件，查询列表
     *
     * @param partyMassesActivityJoinQueryBean 党群活动关联查询实体
     * @param page                             分页
     * @return 查询结果集合
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    List<PartyMassesActivityJoinVO> selectList(PartyMassesActivityJoinQueryBean partyMassesActivityJoinQueryBean, Page page);
}

