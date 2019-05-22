package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesPlaceQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesPlace;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesPlaceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyMassesPlaceMapper {

    /**
     * Description: 新增
     *
     * @param tabPbPartyMassesPlace
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int insert(TabPbPartyMassesPlace tabPbPartyMassesPlace);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMassesPlace
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int updateById(TabPbPartyMassesPlace tabPbPartyMassesPlace);

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
    PartyMassesPlaceVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param tabPbPartyMassesPlaceQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    List<PartyMassesPlaceVO> list(PartyMassesPlaceQueryBean tabPbPartyMassesPlaceQueryBean);

}