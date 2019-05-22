package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesMatterQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesMatter;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesMatterVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyMassesMatterMapper {

    /**
     * Description: 新增
     *
     * @param tabPbPartyMassesMatter
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int insert(TabPbPartyMassesMatter tabPbPartyMassesMatter);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMassesMatter
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int updateById(TabPbPartyMassesMatter tabPbPartyMassesMatter);

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
    PartyMassesMatterVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param tabPbPartyMassesMatterQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    List<PartyMassesMatterVO> list(PartyMassesMatterQueryBean tabPbPartyMassesMatterQueryBean);

}