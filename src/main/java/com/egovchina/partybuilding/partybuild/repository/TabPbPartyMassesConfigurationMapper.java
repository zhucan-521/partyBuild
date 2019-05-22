package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesConfigurationQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesConfiguration;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesConfigurationVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyMassesConfigurationMapper {
    /**
     * Description: 新增
     *
     * @param tabPbPartyMassesConfiguration
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int insert(TabPbPartyMassesConfiguration tabPbPartyMassesConfiguration);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMassesConfiguration
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int updateById(TabPbPartyMassesConfiguration tabPbPartyMassesConfiguration);

    /**
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    PartyMassesConfigurationVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param tabPbPartyMassesConfigurationQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    List<PartyMassesConfigurationVO> list(PartyMassesConfigurationQueryBean tabPbPartyMassesConfigurationQueryBean);
}