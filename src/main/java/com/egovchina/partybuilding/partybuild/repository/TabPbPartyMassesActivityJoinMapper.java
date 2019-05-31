package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityJoinQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesActivityJoin;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityJoinVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:Mapper类
 *
 * @author WuYunJie
 * @date 2019/05/31 11:50:14
 */
@Repository
public interface TabPbPartyMassesActivityJoinMapper {

    /**
     * Description: 新增
     *
     * @param tabPbPartyMassesActivityJoin
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:50:14
     */
    int insert(TabPbPartyMassesActivityJoin tabPbPartyMassesActivityJoin);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMassesActivityJoin
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:50:14
     */
    int updateById(TabPbPartyMassesActivityJoin tabPbPartyMassesActivityJoin);

    /**
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:50:14
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/31 11:50:14
     */
    PartyMassesActivityJoinVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param partyMassesActivityJoinQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/31 11:50:14
     */
    List<PartyMassesActivityJoinVO> list(PartyMassesActivityJoinQueryBean partyMassesActivityJoinQueryBean);
}
