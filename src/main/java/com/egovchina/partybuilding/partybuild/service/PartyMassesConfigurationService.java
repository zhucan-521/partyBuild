package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesConfigurationDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesConfigurationQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesConfigurationVO;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
public interface PartyMassesConfigurationService {

    /**
     * Description: 新增
     *
     * @param partyMassesConfigurationDTO 党群配置DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int save(PartyMassesConfigurationDTO partyMassesConfigurationDTO);

    /**
     * Description: 根据id修改
     *
     * @param partyMassesConfigurationDTO 党群配置DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int updateById(PartyMassesConfigurationDTO partyMassesConfigurationDTO);

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群配置id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id 党群配置id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    PartyMassesConfigurationVO selectById(Long id);


    /**
     * Description: 查询列表
     *
     * @param partyMassesConfigurationQueryBean 党群配置查询实体
     * @param page                              分页
     * @return PartyMassesConfigurationVO
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    List<PartyMassesConfigurationVO> selectList(PartyMassesConfigurationQueryBean partyMassesConfigurationQueryBean, Page page);
}

