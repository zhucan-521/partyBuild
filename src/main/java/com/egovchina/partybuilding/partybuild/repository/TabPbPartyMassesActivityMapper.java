package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesActivity;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyMassesActivityMapper {
    /**
     * Description: 新增
     *
     * @param tabPbPartyMassesActivity
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int insert(TabPbPartyMassesActivity tabPbPartyMassesActivity);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMassesActivity
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int updateById(TabPbPartyMassesActivity tabPbPartyMassesActivity);

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    PartyMassesActivityVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param partyMassesActivityQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    List<PartyMassesActivityVO> list(PartyMassesActivityQueryBean partyMassesActivityQueryBean);

    /**
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int deleteById(Long id);

}