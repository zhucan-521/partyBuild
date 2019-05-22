package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesMatterDTO;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesMatterVO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesMatterQueryBean;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
public interface PartyMassesMatterService {

    /**
     * Description: 新增
     *
     * @param partyMassesMatterDTO 党群服务事项DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int save(PartyMassesMatterDTO partyMassesMatterDTO);

    /**
     * Description: 根据id修改
     *
     * @param partyMassesMatterDTO 党群服务事项DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int updateById(PartyMassesMatterDTO partyMassesMatterDTO);

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群服务事项id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id 党群服务事项id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    PartyMassesMatterVO selectById(Long id);


    /**
     * Description: 查询列表
     *
     * @param partyMassesMatterQueryBean 党群服务事项查询实体
     * @param page                       分页
     * @return PartyMassesMatterVO
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    List<PartyMassesMatterVO> selectList(PartyMassesMatterQueryBean partyMassesMatterQueryBean, Page page);
}

