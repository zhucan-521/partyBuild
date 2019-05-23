package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesPlaceDTO;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesPlaceVO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesPlaceQueryBean;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
public interface PartyMassesPlaceService {

    /** 
     * Description: 新增
     *
     * @param partyMassesPlaceDTO 新增对象
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int save(PartyMassesPlaceDTO partyMassesPlaceDTO);

    /** 
     * Description: 根据id修改
     *
     * @param partyMassesPlaceDTO 修改对象
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int updateById(PartyMassesPlaceDTO partyMassesPlaceDTO);

    /** 
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    int deleteById(Long id);
    
    /** 
     * Description: 根据id查找
     *
     * @param id 主键ID
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    PartyMassesPlaceVO selectById(Long id);


    /** 
     * Description: 根据条件，查询列表
     *
     * @param partyMassesPlaceQueryBean
     * @param page
     * @return 查询结果集合
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    List<PartyMassesPlaceVO> selectList(PartyMassesPlaceQueryBean partyMassesPlaceQueryBean, Page page);
}

