package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMasses;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesTree;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbPartyMassesMapper {

    /**
     * Description: 新增
     *
     * @param tabPbPartyMasses
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int insert(TabPbPartyMasses tabPbPartyMasses);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMasses
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    int updateById(TabPbPartyMasses tabPbPartyMasses);

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
    PartyMassesVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param tabPbPartyMassesQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/20 21:34:12
     */
    List<PartyMassesVO> list(PartyMassesQueryBean tabPbPartyMassesQueryBean);

    /**
     * 校验党群名字是否存在
     *
     * @param partyMassesName 名字
     * @param partyMassesId   id
     * @return boolean
     */
    Boolean checkIsExistPartyMassesName(
            @Param("partyMassesName") String partyMassesName, @Param("partyMassesId") Long partyMassesId);

    /**
     * 根据父组织id查询党群列表
     *
     * @param conditions 条件
     * @return PartyMassesTree
     */
    List<PartyMassesTree> selectListByCondition(Map<String, Object> conditions);
}