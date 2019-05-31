package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesActivityJoinDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityJoinQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesActivityJoin;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesActivityJoinMapper;
import com.egovchina.partybuilding.partybuild.service.PartyMassesActivityJoinService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityJoinVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;


/**
 * Description:服务实现类
 *
 * @author WuYunJie
 * @date 2019/05/31 11:49:59
 */
@Service
public class PartyMassesActivityJoinServiceImpl implements PartyMassesActivityJoinService {

    @Autowired
    private TabPbPartyMassesActivityJoinMapper tabPbPartyMassesActivityJoinMapper;

    /**
     * Description: 新增
     *
     * @param partyMassesActivityJoinDTO 党群活动关联DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    @Override
    public int save(PartyMassesActivityJoinDTO partyMassesActivityJoinDTO) {
        TabPbPartyMassesActivityJoin tabPbPartyMassesActivityJoin =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesActivityJoinDTO, TabPbPartyMassesActivityJoin.class, false);
        return tabPbPartyMassesActivityJoinMapper.insert(tabPbPartyMassesActivityJoin);
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesActivityJoinDTO 党群活动关联DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    @Override
    public int updateById(PartyMassesActivityJoinDTO partyMassesActivityJoinDTO) {
        TabPbPartyMassesActivityJoin tabPbPartyMassesActivityJoin =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesActivityJoinDTO, TabPbPartyMassesActivityJoin.class, true);
        return tabPbPartyMassesActivityJoinMapper.updateById(tabPbPartyMassesActivityJoin);
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 主键id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    @Override
    public int deleteById(Long id) {
        TabPbPartyMassesActivityJoin tabPbPartyMassesActivityJoin = new TabPbPartyMassesActivityJoin();
        tabPbPartyMassesActivityJoin.setId(id);
        tabPbPartyMassesActivityJoin.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesActivityJoin);
        return tabPbPartyMassesActivityJoinMapper.updateById(tabPbPartyMassesActivityJoin);
    }

    /**
     * Description: 根据id查找
     *
     * @param id 主键id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    @Override
    public PartyMassesActivityJoinVO selectById(Long id) {
        return tabPbPartyMassesActivityJoinMapper.selectById(id);
    }

    /**
     * Description: 根据条件，查询列表
     *
     * @param partyMassesActivityJoinQueryBean 党群活动关联查询实体
     * @param page                             分页
     * @return 查询结果集合
     * @author WuYunJie
     * @date 2019/05/31 11:49:59
     */
    @Override
    public List<PartyMassesActivityJoinVO> selectList(PartyMassesActivityJoinQueryBean partyMassesActivityJoinQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesActivityJoinMapper.list(partyMassesActivityJoinQueryBean);
    }
}

