package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesParticipantDTO;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesParticipantVO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesParticipantQueryBean;

import java.util.List;

/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/22 10:16:01
 */
public interface PartyMassesParticipantService {

    /** 
     * Description: 新增
     *
     * @param partyMassesParticipantDTO 新增对象
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    int save(PartyMassesParticipantDTO partyMassesParticipantDTO);

    /** 
     * Description: 根据id修改
     *
     * @param partyMassesParticipantDTO 修改对象
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    int updateById(PartyMassesParticipantDTO partyMassesParticipantDTO);

    /** 
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    int deleteById(Long id);
    
    /** 
     * Description: 根据id查找
     *
     * @param id 主键ID
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    PartyMassesParticipantVO selectById(Long id);


    /** 
     * Description: 根据条件，查询列表
     *
     * @param partyMassesParticipantQueryBean
     * @param page
     * @return 查询结果集合
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    List<PartyMassesParticipantVO> selectList(PartyMassesParticipantQueryBean partyMassesParticipantQueryBean, Page page);
}

